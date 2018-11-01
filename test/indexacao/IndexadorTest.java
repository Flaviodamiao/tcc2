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

import util.Const;
import extracao.Extrator;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import mvc.bean.Artigo;
import util.ModeloCenario;
import mvc.bean.Edicao;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.FSDirectory;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import util.DocumentCenario;
import util.FieldUtil;

/**
 *
 * @author Flávio Almeida
 */
public class IndexadorTest {
    private Edicao edicao;
    
    public IndexadorTest() {
    }
    
    @Before
    public void before(){
        edicao = ModeloCenario.getEdicao();
    }

    @Test
    public void PrimeiroDocumentoDoIndiceDeveTerValoresEsperadosTeste() throws IOException {
        Document documentEsp = DocumentCenario.getUmDocument();
        
        String artigoEmUmArquivo = Const.DIRETORIO_TESTES + "\\artigo_p16-24.pdf";
        InputStream arquivo = Files.newInputStream(Paths.get(artigoEmUmArquivo));
        Extrator extrator = Extrator.getExtrator(arquivo, "pdf", edicao);
        extrator.setCaminhoRepositorio(Const.DIRETORIO_REPOSITORIO_TESTES);
        List<Artigo> artigosRes = extrator.processarEdicao();
        
        Indexador indexador = new Indexador(artigosRes);
        indexador.setDirIndice(Const.DIRETORIO_INDICE_TESTES);
        indexador.indexa();
        
        IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(Const.DIRETORIO_INDICE_TESTES)));
        Document documentRes = reader.document(0);
        reader.close();
        
        assertTrue(FieldUtil.documentsSaoIguais(documentEsp, documentRes));
    }
    
    @After
    public void after() throws IOException{
        //O índice precisa ser excluído ao final do teste, senão o próximo teste falhará
        Files.walkFileTree(Paths.get(Const.DIRETORIO_INDICE_TESTES), new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) throws IOException{
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
