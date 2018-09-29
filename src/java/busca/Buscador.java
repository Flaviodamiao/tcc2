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
import java.util.List;
import modelo.Artigo;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.br.BrazilianAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.FSDirectory;
import util.Const;

/**
 *
 * @author Flávio Almeida
 */
public class Buscador {
    private int hitsPorPagina = 20;
    
    public Buscador(int hitsPorPagina){
        this.hitsPorPagina = hitsPorPagina;
    }
    
    public List<Artigo> buscarEmTextoCompleto(String termosPesquisa) throws IOException{
        Analyzer analyzer = new BrazilianAnalyzer();
                
        IndexReader indexReader = DirectoryReader.open(FSDirectory.open(Paths.get(Const.DIRETORIO_INDICE_TESTES)));
        return null;
    }
}
