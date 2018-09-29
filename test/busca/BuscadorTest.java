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

import extracao.Extrator;
import indexacao.Indexador;
import indexacao.IndexadorTest;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import modelo.Artigo;
import modelo.Edicao;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.FSDirectory;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import util.Const;
import util.DocumentCenario;
import util.FieldUtil;
import util.ModeloCenario;

/**
 *
 * @author Flávio Almeida
 */
public class BuscadorTest {
    private Edicao edicao;
    
    public BuscadorTest() {
    }
    
    @Before
    public void before() throws IOException{
        edicao = ModeloCenario.getEdicao();
        criarIndice();
    }

    @Test
    public void DeveRetornarArtigoEspecificoEmBuscaSemFiltrosTeste() throws IOException {
        Artigo artigoEsperado = ModeloCenario.getUmArtigo();
        
        String termosPesquisa = "pescado";
        List<Artigo> artigosResultado = new Buscador().buscarEmTextoCompleto(termosPesquisa);
        
        assertTrue(artigosResultado.size() == 1 & artigoEsperado.equals(artigosResultado.get(0)));
    }
    
    private void criarIndice() throws IOException {
        String arquivoEdicao = Const.DIRETORIO_TESTES + "\\igapo_vol10_n1_2016_com3artigos.pdf";
        Extrator extrator = Extrator.getExtrator(arquivoEdicao, edicao);
        List<Artigo> artigosRes = extrator.processarEdicao();
        
        new Indexador(artigosRes).indexa();
    }
    
    @After
    public void after() throws IOException{
        //O índice precisa ser excluído ao final do teste, senão o próximo teste pode falhar
        Files.walkFileTree(Paths.get(Const.DIRETORIO_INDICE_TESTES), new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) throws IOException{
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
