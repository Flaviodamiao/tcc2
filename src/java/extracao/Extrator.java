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
import java.util.List;
import modelo.Artigo;
import modelo.Edicao;
import util.Const;

/**
 *
 * @author Flávio Almeida
 */
public abstract class Extrator {
    protected Edicao edicao;
    protected List<Artigo> artigos;
    protected static String mensagemErro = "";
    
    /**
     * Cria uma instância de uma subclasse de acordo com o tipo de arquivo.
     * @param arquivo Contendo uma edição ou um artigo
     * @param edicao Os dados da Edição à qual os artigos pertecem
     * @return
     * @throws IOException 
     */
    public static Extrator getExtrator(String arquivo, Edicao edicao) throws IOException{
        if(arquivo.toLowerCase().endsWith(".pdf")){
            return new ExtratorPDF(arquivo, edicao);
        }else if(arquivo.toLowerCase().endsWith(".doc") || arquivo.toLowerCase().endsWith(".docx")){
            //código que irá retornar um objeto para tratar arquivos do Microsoft Office Word
        }
        
        mensagemErro = "O arquivo não está em um formato reconhecido pelo sistema.";
        return null;
    }
    
    //Adiciona cada artigo encontrado à lista de artigos que será retornada
    protected void adicionarArtigo(String titulo, List<String> autores, String conteudo, String caminho) throws IOException{
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
    protected String getCaminhoArtigo(int pagInicial, int pagFinal){
        String caminhoDestino = Const.DIRETORIO_REPOSITORIO + "\\" + edicao.getRevista() + "\\vol." + edicao.getVolume() + "\\N-" + edicao.getNumero();
        String arquivoDestino = "\\artigo_" + edicao.getRevista() + "_vol-" + edicao.getVolume() + "_N-" + edicao.getNumero() + "_pags" + pagInicial + "-" + pagFinal + ".pdf";
        File destino = new File(caminhoDestino);
            
        if (!destino.exists()){
            System.out.println("Criando diretório: " + destino.getAbsolutePath());
            destino.mkdirs();
        }
        return destino.getAbsolutePath() + arquivoDestino ;
    }
    
    /**
     * Recupera a mensagem de erro, caso ocorra algum.
     * @return A mensagem informando o erro.
     */
    public static String getMensagemErro(){
        return mensagemErro;
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
    protected abstract void gravarEdicaoRepositorio(String caminho) throws IOException;
}
