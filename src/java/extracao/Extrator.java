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
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import mvc.bean.Artigo;
import mvc.bean.Edicao;
import util.Const;

/**
 *
 * @author Flávio Almeida
 */
public abstract class Extrator{
    protected Edicao edicao;
    protected List<Artigo> artigos;
    protected String caminhoRepositorio = Const.DIRETORIO_REPOSITORIO;
    protected static String mensagem = "";
    
    //No campo Artigo.caminho será gravado apenas a estrutura de pastas do repositório,
    //evitando problemas no índice caso a localização do repositório seja mudada
    protected String caminhoRelEdicao;
    protected String caminhoGravacao;
    
    /**
     * Substitui o construtor para evitar repetição de código.
     * @param edicao 
     */
    protected Extrator(Edicao edicao){
        this.edicao = edicao;
        artigos = new ArrayList<>();
        caminhoRelEdicao = "\\" + edicao.getRevista() 
                + "\\vol." + edicao.getVolume() + "\\N-" + edicao.getNumero();
        
    }
    
    /**
     * Cria uma instância de uma subclasse de acordo com o tipo de arquivo.
     * @param arquivo Contendo uma edição ou um artigo
     * @param edicao Os dados da Edição à qual os artigos pertecem
     * @return
     * @throws IOException 
     */
    public static Extrator getExtrator(InputStream arquivo, String formato, Edicao edicao) throws IOException{
        if(formato.toLowerCase().contains("pdf")){
            return new ExtratorPDF(arquivo, edicao);
        }else if(formato.toLowerCase().contains("doc")){
            //código que irá retornar um objeto para tratar arquivos do Microsoft Office Word
        }
        
        throw new IllegalArgumentException("O arquivo não está em um formato reconhecido pelo sistema.");
    }
    
    //Adiciona cada artigo encontrado à lista de artigos que será retornada
    protected final void adicionarArtigo(String titulo, String autores, String conteudo, String caminho) throws IOException{
        Artigo artigo = new Artigo();
        artigo.setTitulo(titulo);
        artigo.setAutores(autores);
        artigo.setConteudo(conteudo);
        artigo.setEdicao(edicao);
        artigo.setCaminho(caminho);
        
        artigos.add(artigo);
    }
    
    //A paritr da edição informada, cria a estrutura de diretórios onde os artigos serão armazenados
    //Retorna o caminho do artigo
    protected final String getNomeArtigo(int pagInicial, int pagFinal){
        return "\\artigo_" + edicao.getRevista() + "_vol-" + edicao.getVolume() 
                + "_N-" + edicao.getNumero() + "_pags" + pagInicial + "-" + pagFinal + ".pdf";
    }
    
    /**
     * Cria o caminho absoluto para gravação
     */
    protected final void criaCaminhoGravacao(){
        File destino = new File(caminhoRepositorio + caminhoRelEdicao);
            
        if (!destino.exists()){
            System.out.println("Criando diretório: " + destino.getAbsolutePath());
            destino.mkdirs();
        }
    }
    
    //Para fins de teste
    public void setCaminhoRepositorio(String caminhoRepositorio){
        this.caminhoRepositorio = caminhoRepositorio;
    }
    
    public static String getMensagem(){
        return mensagem;
    }
    
    /** 
     * Percorre o arquivo página por página, enviando cada uma para um analisador 
     * que sabe identificar o início e o fim de um artigo.
     * @return retorna uma lista de artigos extraídos ou null, se nenhum for encontrado.
     * @throws java.io.IOException
     */
    public abstract List<Artigo> processarEdicao() throws IOException;
    
    /**
     * Grava, no repositório, uma cópia do arquivo da edição junto aos artigos extraídos.
     * @param caminho O local no repositório onde a cópia será salva.
     * @throws IOException 
     */
    protected abstract void gravarEdicaoRepositorio() throws IOException;
}
