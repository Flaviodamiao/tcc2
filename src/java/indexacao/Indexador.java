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

package indexacao;

import extracao.Extrator;
import util.Const;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import modelo.Artigo;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.br.BrazilianAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.SimpleFSDirectory;

/**
 * Esta classe indexa artigos recebidos pelo construtor através de uma lista de artigos
 * @author Flávio Almeida
 */
public class Indexador {
    private List<Artigo> artigos;
    private String dirIndice = Const.DIRETORIO_INDICE;
    
    //Cria objetos padrão para cada tipo de Fieldtype utilizado pelo sistema
    public static final FieldType TYPE_CONTEUDO;
    public static final FieldType TYPE_STORED_ONLY;
    public static final FieldType TYPE_STORED_INDEXED_DOCS_TOKENIZED;
    public static final FieldType TYPE_STORED_INDEXED_DOCS_NOT_TOKENIZED;
    
    static{
        TYPE_CONTEUDO = new FieldType();
        TYPE_CONTEUDO.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS);
        TYPE_CONTEUDO.setTokenized(true);
        TYPE_CONTEUDO.setStored(false);
        TYPE_CONTEUDO.freeze();
        
        TYPE_STORED_ONLY = new FieldType();
        TYPE_STORED_ONLY.setIndexOptions(IndexOptions.NONE);
        TYPE_STORED_ONLY.setTokenized(false);
        TYPE_STORED_ONLY.setStored(true);
        TYPE_STORED_ONLY.freeze();
        
        TYPE_STORED_INDEXED_DOCS_TOKENIZED = new FieldType();
        TYPE_STORED_INDEXED_DOCS_TOKENIZED.setIndexOptions(IndexOptions.DOCS);
        TYPE_STORED_INDEXED_DOCS_TOKENIZED.setTokenized(true);
        TYPE_STORED_INDEXED_DOCS_TOKENIZED.setStored(true);
        TYPE_STORED_INDEXED_DOCS_TOKENIZED.freeze();
        
        TYPE_STORED_INDEXED_DOCS_NOT_TOKENIZED = new FieldType();
        TYPE_STORED_INDEXED_DOCS_NOT_TOKENIZED.setIndexOptions(IndexOptions.DOCS);
        TYPE_STORED_INDEXED_DOCS_NOT_TOKENIZED.setTokenized(false);
        TYPE_STORED_INDEXED_DOCS_NOT_TOKENIZED.setStored(true);
        TYPE_STORED_INDEXED_DOCS_NOT_TOKENIZED.freeze();
    }
    
    /**
     * Único Construtor. Recebe a {@code List<Artigo>} que será tratada
     * @param artigos 
     */
    public Indexador(List<Artigo> artigos){
        this.artigos = artigos;
    }
    
    /**
     * Realiza o processo de indexação.
     * Para o processo de análise, utiliza a classe {@link BrazilianAnalyzer}, 
     * que possui uma lista de stop-words e uma classe para radicalização.
     * @return true se o processo ocerrer normalmente.
     * @throws IOException 
     */
    public boolean indexa() throws IOException{
        if(!artigos.isEmpty()){
            Analyzer analyzer = new BrazilianAnalyzer();
            IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
            iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
            IndexWriter indexWriter = new IndexWriter(new SimpleFSDirectory(Paths.get(dirIndice)), iwc);

            for(Artigo artigo: artigos){
                Document doc = getDocument(artigo);
                System.out.println("Indexando: " + artigo.getTitulo());
                indexWriter.addDocument(doc);
            }

            indexWriter.close();
            
            return true;
        } 
        
        return false;
    }
    
    private Document getDocument(Artigo artigo){
        Document document = new Document();
        FieldType fieldType = new FieldType();
        fieldType.setIndexOptions(IndexOptions.NONE);
        fieldType.setStored(true);
        
        String autores = "";
        
        for(String s: artigo.getAutores()){
            autores += s;
        }
        
        document.add(new Field("titulo", artigo.getTitulo(), TYPE_STORED_INDEXED_DOCS_TOKENIZED));
        document.add(new Field("autores", autores, TYPE_STORED_INDEXED_DOCS_NOT_TOKENIZED));
        document.add(new Field("conteudo", artigo.getConteudo(), TYPE_CONTEUDO));
        document.add(new Field("caminho", artigo.getCaminho(), TYPE_STORED_ONLY));
        document.add(new Field("numeroEdicao", Integer.toString(artigo.getEdicao().getNumero()), TYPE_STORED_ONLY));
        document.add(new Field("volumeEdicao", Integer.toString(artigo.getEdicao().getVolume()), TYPE_STORED_ONLY));
        document.add(new Field("anoEdicao", Integer.toString(artigo.getEdicao().getAno()), TYPE_STORED_ONLY));
        document.add(new Field("revista", artigo.getEdicao().getRevista().toString(), TYPE_STORED_ONLY));
        
        return document;
    }
}

