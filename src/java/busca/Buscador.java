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

package busca;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Artigo;
import modelo.Edicao;
import modelo.Revista;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.br.BrazilianAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import util.Const;

/**
 *
 * @author Flávio Almeida
 */
public class Buscador {
    private long numTotalHits;
    private long tempoBusca;
    
    public Buscador(){
    }
    
    public List<Artigo> buscarEmTextoCompleto(String termosPesquisa) throws IOException, ParseException{
        IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(Const.DIRETORIO_INDICE_TESTES)));
        IndexSearcher searcher = new IndexSearcher(reader);
        Analyzer analyzer = new BrazilianAnalyzer();
        QueryParser parser = new QueryParser(Const.CAMPO_CONTEUDO, analyzer);
        Query query;
        
        try {
            query = parser.parse(termosPesquisa);
        } catch (ParseException ex) {
            Logger.getLogger(Buscador.class.getName()).log(Level.SEVERE, null, ex);
            throw new ParseException("Erro ao converter termos em uma Query.");
        }
        
        Date inicioBusca = new Date();
        TopDocs topDocs = searcher.search(query, 50);
        Date fimBusca = new Date();
        tempoBusca = fimBusca.getTime() - inicioBusca.getTime();
        
        numTotalHits = topDocs.totalHits;
        ScoreDoc[] hits = topDocs.scoreDocs;
        
        List<Artigo> artigos = getArtigos(searcher, hits);
        reader.close();
        
        return artigos;
    }
    
    private List<Artigo> getArtigos(IndexSearcher searcher, ScoreDoc[] hits) throws IOException{
        List<Artigo> artigos = new ArrayList();
        
        try {
            for (ScoreDoc hit : hits) {
                Document doc = searcher.doc(hit.doc);
                Edicao edicao = new Edicao();
                Artigo artigo = new Artigo();
                
                //teste
                System.out.println("\n\nDocumento " + hit.doc + " ,score: " + hit.score);
                System.out.println("Título: " + doc.get(Const.CAMPO_TITULO));
                
                edicao.setRevista(Revista.valueOf(doc.get(Const.CAMPO_REVISTA)));
                edicao.setVolume(Integer.parseInt(doc.get(Const.CAMPO_VOLUME_EDICAO)));
                edicao.setAno(Integer.parseInt(doc.get(Const.CAMPO_ANO_EDICAO)));
                edicao.setNumero(Integer.parseInt(doc.get(Const.CAMPO_NUMERO_EDICAO)));
                
                artigo.setTitulo(doc.get(Const.CAMPO_TITULO));
                artigo.setAutores(Arrays.asList(doc.get(Const.CAMPO_AUTORES).split(" - ")));
                artigo.setCaminho(doc.get(Const.CAMPO_CAMINHO));
                artigo.setEdicao(edicao);
                
                artigos.add(artigo);
            }
        } catch (IOException ex) {
            Logger.getLogger(Buscador.class.getName()).log(Level.SEVERE, null, ex);
            throw new IOException("Erro ao ler o índice.");
        }
        return artigos;
    }
    
    public long getTempoDeBusca(){
        return tempoBusca;
    }

    public long getNumTotalHits() {
        return numTotalHits;
    }
}
