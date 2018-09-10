/*
 * Copyright (C) 2018 Flávio Almeida
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package extracao;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Artigo;
import org.apache.pdfbox.multipdf.PageExtractor;
import org.apache.pdfbox.pdmodel.PDDocument;
import modelo.Edicao;
import org.apache.pdfbox.text.PDFTextStripper;

/**
 * Analisa arquivos PDF em busca de artigos.
 * @author Flávio Almeida
 */
class ExtratorPDF extends Extrator{
    private PDDocument document;
    private int paginaAtual = 0;
    private int pagInicioArtigo = -1;
    private int pagFimArtigo;
    private boolean iniciouArtigo = false;
    private boolean encontrouInicioArtigo = false;
    private boolean finalizaAnalise = false;
    
    /**
     * Construtor solo. Recebe o caminho do arquivo, contendo um mais artigos, 
     * e as informações sobre a edição à qual o artigo pertence. 
     * @param arquivo O caminho do arquivo.
     * @param edicao A edição à qual o(s) artigo(s) pertemce(m).
     * @throws IOException 
     */
    protected ExtratorPDF(String arquivo, Edicao edicao) throws IOException{
        this.edicao = edicao;
        this.artigos = new ArrayList<>();
        carregarRevista(arquivo);
    }
    
    /**
     * Carrega o arquivo da Revista.
     * @param arquivo O arquivo da edição completa ou de um único artigo.
     */
    private void carregarRevista(String arquivo) throws IOException{
            try {
                File file = new File(arquivo);
                document = PDDocument.load(file);
            } catch (IOException ex) {
                Logger.getLogger(ExtratorPDF.class.getName()).log(Level.SEVERE, null, ex);
                mensagemErro = "Erro ao carregar revista. Verifique se o arquivo está corrompido ou se "
                        + "o conteúdo do arquivo está no formato especificado.";
            }
    }
    
    @Override
    public List<Artigo> processarEdicao() throws IOException{
        String titulo = "";
        List<String> autores = null;
        int ultimaPagina = document.getNumberOfPages();
        
        while(paginaAtual < ultimaPagina  && !finalizaAnalise){
            //Adiciona-se 1 a paginaAtual, pois o método extract() conta as páginas a partir de 1
            PageExtractor pageExtractor = new PageExtractor(document, paginaAtual + 1, paginaAtual + 1);
            ExtratorPDFEngine extratorEngine = null;
            
            try {
                PDDocument pagDoc = pageExtractor.extract();
                extratorEngine = new ExtratorPDFEngine(pagDoc, edicao.getRevista());
                extratorEngine.getText(pagDoc);
                pagDoc.close();
                encontrouInicioArtigo = extratorEngine.getEncontrouInicioArtigo();
                finalizaAnalise = extratorEngine.getfinalizaAnalise();
                
                //Procedimentos a serem realizados se o início de um artigo for encontrado
                //na página atual.
                if (encontrouInicioArtigo & !finalizaAnalise){
                    if( iniciouArtigo){
                        pagFimArtigo = paginaAtual;
                        PDDocument artigoExtraido = extrairArtigo(pagInicioArtigo, pagFimArtigo);
                        String caminhoArtigo = getCaminhoArtigo(pagInicioArtigo, pagFimArtigo);
                        adicionarArtigo(titulo, autores, new PDFTextStripper().getText(artigoExtraido), caminhoArtigo);
                        gravarArtigo(artigoExtraido, caminhoArtigo);
                        pagInicioArtigo = paginaAtual + 1;
                        titulo = extratorEngine.getTitulo();
                        autores = extratorEngine.getAutores();
                    }else{
                        pagInicioArtigo = paginaAtual + 1;
                        iniciouArtigo = true;
                        titulo = extratorEngine.getTitulo();
                        autores = extratorEngine.getAutores();
                    }
                    encontrouInicioArtigo = false;
                }
                paginaAtual++;
            
            } catch (IOException ex) {
                Logger.getLogger(ExtratorPDF.class.getName()).log(Level.SEVERE, null, ex);
                mensagemErro = "Erro ao tentar processar as páginas da edição.";
            }
        }
        
        //Verifica se, ao final do arquivo, algum artigo foi encontrado
        if(pagInicioArtigo != -1){
            pagFimArtigo = finalizaAnalise ? (paginaAtual) : ultimaPagina;
            PDDocument artigoExtraido = extrairArtigo(pagInicioArtigo, pagFimArtigo);
            String caminhoArtigo = getCaminhoArtigo(pagInicioArtigo, pagFimArtigo);
            adicionarArtigo(titulo, autores, new PDFTextStripper().getText(artigoExtraido), caminhoArtigo);
            gravarArtigo(artigoExtraido, caminhoArtigo);
            gravarEdicaoRepositorio(caminhoArtigo);
            document.close();
            
            return this.artigos;
        } else{
            return null;
        }
        
    }
    
    //Salva um artigo, extraído da edição, no caminho especificado
    private void gravarArtigo(PDDocument artigoExtraido, String caminho){
        try{
            System.out.println("Salvando artigo: " + caminho);
            artigoExtraido.save(caminho);
            artigoExtraido.close();
        } catch (IOException ex) {
            Logger.getLogger(ExtratorPDF.class.getName()).log(Level.SEVERE, null, ex);
            mensagemErro = "Erro ao gravar artigo em disco.";
        }
    }
    
    
    //Extrai do arquivo de uma edição as páginas correspondentes a um artigo.
    //A classe PageExtractor conta as páginas a partir de 1.
    private PDDocument extrairArtigo(int pagInicial, int pagFinal){
        try {
            return new PageExtractor(document, pagInicial, pagFinal).extract();
        } catch (IOException ex) {
            Logger.getLogger(ExtratorPDF.class.getName()).log(Level.SEVERE, null, ex);
            mensagemErro = "Erro ao extrair artigo do documento.";
        }
        return null;
    }
    
    
    @Override
    protected void gravarEdicaoRepositorio(String caminho) throws IOException{
        System.out.println("Enviando edição para o repositório, na pasta: " + caminho);
        document.save(new File(caminho).getParent()
                + "\\" + edicao.getRevista() + "_vol-" + edicao.getVolume() + "_N-" + edicao.getNumero() + ".pdf");
    }
}

