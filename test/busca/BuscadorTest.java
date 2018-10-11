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
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Artigo;
import modelo.Edicao;
import org.apache.lucene.queryparser.classic.ParseException;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import util.Const;
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

    /**
     * Deve recuperar apenas um artigo, pois somente ele possui a palavra pesquisada
     * @throws IOException
     * @throws ParseException 
     */
    @Test
    public void DeveRetornarArtigoEspecificoEmBuscaSemFiltrosTeste() throws IOException, ParseException {
        Logger.getLogger(Buscador.class.getName()).log(Level.INFO, "Teste: DeveRetornarArtigoEspecificoEmBuscaSemFiltrosTeste()");
        Artigo artigoEsperado = ModeloCenario.getUmArtigo();
        
        //O conteúdo do artigo é INDEXED, mas não STORED, então, o artigo que será recuperado terá conteúdo vazio
        artigoEsperado.setConteudo("");
        
        String termosPesquisa = "pescado";
        List<Artigo> artigosResultado = new Buscador().buscarEmTextoCompleto(termosPesquisa);
        
        //System.out.println("\n\n---------------------------\n\n artigosResultado.size(): " + (artigosResultado.size() == 1 & artigoEsperado.equals(artigosResultado.get(0))));
        //artigoEsperado.toString();
        //artigosResultado.toString();
        
        assertTrue(artigosResultado.size() == 1 & artigoEsperado.equals(artigosResultado.get(0)));
    }
    
    /**
     * Verifica se cálculo da similaridade está próximo do esperado
     * A palavra pesquisada aparece em quantidades diferentes todos os artigos-teste
     * @throws java.io.IOException
     * @throws org.apache.lucene.queryparser.classic.ParseException
     */
    @Test
    public void DeveRetornarArtigoNaPosicaoEsperadaTeste() throws IOException, ParseException{
        Logger.getLogger(Buscador.class.getName()).log(Level.INFO, "Teste: DeveRetornarArtigoNaPosicaoEsperadaTeste");
        Artigo artigoEsperado = ModeloCenario.getUmArtigo();
        
        //O conteúdo do artigo é INDEXED, mas não STORED, então, o artigo que será recuperado terá conteúdo vazio
        artigoEsperado.setConteudo("");
        
        String termosPesquisa = "estudo";
        List<Artigo> artigosResultado = new Buscador().buscarEmTextoCompleto(termosPesquisa);
        
        
        artigosResultado.get(0).toString();
        assertTrue(artigosResultado.size() == 3 & artigoEsperado.equals(artigosResultado.get(2)));
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
