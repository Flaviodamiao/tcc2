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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import modelo.Revista;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

/**
 *
 * @author Flávio Almeida
 */
class ExtratorPDFEngine extends PDFTextStripper{
    private String padraoInicioArtigo;
    private String padraoFimAnalise;
    private String padraoCabecalho;
    private boolean encontrouInicioArtigo = false;
    private boolean finalizaAnalise = false;
    private String linhaAnterior = "";
    private String titulo = "";
    private boolean construindoTitulo = false;
    private boolean coletandoAutores = false;
    private List<String> autores = new ArrayList<>();
    private String linhaAutores = "";
    
    //Quando for extrair artigos das demais revistas
    //verificar se as variáveis abaixo são necessárias
    private String fonteTitulo;
    private int tamFonteTitulo;
    private int tamFonteNotaRodape;
    private String padraoRodape;
    
    protected ExtratorPDFEngine(PDDocument document, Revista revista) throws IOException{
        this.document = document;
        configurarProcessadorPDF();
        configurarExtracao(revista);
    }
    
    protected boolean getEncontrouInicioArtigo(){
        return encontrouInicioArtigo;
    }
    protected boolean getfinalizaAnalise(){
        return finalizaAnalise;
    }
    
    protected String getTitulo(){
        return titulo;
    }
    
    protected List<String> getAutores(){
        return autores;
    }
    
    /**
     * Define configurações internas da classe @PDFTextStripper
     */
    private void configurarProcessadorPDF(){
        setSortByPosition(true);
        setStartPage(0);
        setEndPage(document.getNumberOfPages());
    }
    
     /**
     * Define as configurações para extrair artigos
     */
    private void configurarExtracao(Revista revista){
        padraoCabecalho = revista.getPadraoCabecalho();
        padraoInicioArtigo = revista.getPadraoInicioArtigo();
        padraoFimAnalise = revista.getPadraoFimAnalise();
    }
    
    /**
     * Sobrescrever este método possibilita o acesso a cada linha do documento.
     * @param linha
     * @param textPositions
     * @throws IOException 
     */
    @Override
    protected void writeString(String linha, List<TextPosition> textPositions) throws IOException{
        extrairTitulo(linha, textPositions);
        
        coletarAutores(linha, textPositions);
        
        if(linha.trim().equalsIgnoreCase(padraoInicioArtigo) && !finalizaAnalise){
            encontrouInicioArtigo = true;
        }
        
        if(linha.trim().equalsIgnoreCase(padraoFimAnalise)){
            finalizaAnalise = true;
        }
        
        linhaAnterior = linha;
    }
    
    /**
     * Verifica se a linha que está sendo processada apresenta o padrão de 
     * título da revista em questão e, caso positivo, adiciona esta linha 
     * ao título do artigo
     */
    private void extrairTitulo(String linha, List<TextPosition> textPositions){
        if (linhaAnterior.equals(padraoCabecalho) || construindoTitulo){
            if(!linhaEstaMaiuscula(textPositions) || !linhaEstaNegrito(textPositions)){
                construindoTitulo = false;
            } else{
                construindoTitulo = true;
                titulo += linha;
            }
        }
    }
    
    /**
     * Verifica se a linha que está sendo processada apresenta o padrão de 
     * nomes de autores da revista em questão e, caso positivo,
     * constrói lista de autores
     */
    private void coletarAutores(String linha, List<TextPosition> textPositions){
        //Inicia e continua a construção da lista de autores a cada nova chamada
        if((!construindoTitulo & linhaEstaNegrito(textPositions)) & (titulo.contains(linhaAnterior) || coletandoAutores) & !titulo.equals("")){
            linhaAutores += linha;
            coletandoAutores = true;
        }else if(coletandoAutores){ //Caso esteja coletando lista de autores e encontre o final da lista 
            int contaAutores = 1;
            int indexOfContAutores = linhaAutores.indexOf(Integer.toString(contaAutores));
            
            //Caso o artigo possua apenas um autor
            if(indexOfContAutores == -1){
                autores.add(linhaAutores.substring(0));
            }
            
            while( indexOfContAutores > 0){
                autores.add(linhaAutores.substring(0, indexOfContAutores));
                
                if(indexOfContAutores + 2 >= linhaAutores.length()){
                    break;
                }else{
                    linhaAutores = linhaAutores.substring(indexOfContAutores+2);

                    while (linhaAutores.substring(0, 1).toLowerCase().equals(linhaAutores.substring(0, 1))){
                        linhaAutores = linhaAutores.substring(1);
                    }
                    contaAutores++;
                    indexOfContAutores = linhaAutores.indexOf(Integer.toString(contaAutores));
                }
            }
            coletandoAutores = false;
        }
    }
    
    private boolean linhaEstaNegrito(List<TextPosition> textPositions){
        for (TextPosition tp: textPositions){
            if (!tp.getFont().getName().toLowerCase().contains("bold")) {
                return false;
            }
        }
        return true;
    }
    
    private boolean linhaEstaMaiuscula(List<TextPosition> textPositions){
        for (TextPosition tp: textPositions){
            String letra = tp.getUnicode();
            if (!letra.equals(letra.toUpperCase())) {
                return false;
            }
        }
        return true;
    }
}
