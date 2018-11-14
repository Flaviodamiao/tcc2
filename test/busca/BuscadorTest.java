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
import java.io.InputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mvc.bean.Artigo;
import mvc.bean.Edicao;
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
    private Buscador buscador;
    
    public BuscadorTest() {
        buscador = new Buscador();
        buscador.setCaminhoIndice(Const.DIRETORIO_INDICE_TESTES);
        buscador.setCaminhoRepositorio(Const.DIRETORIO_REPOSITORIO_TESTES);
    }
    
    @Before
    public void before() throws IOException{
    }

    /**
     * Deve recuperar apenas um artigo, pois somente ele possui a palavra pesquisada
     * @throws IOException
     * @throws ParseException 
     */
    @Test
    public void DeveRetornarArtigoEspecificoEmBuscaSemFiltrosTeste() throws IOException, ParseException {
        Logger.getLogger(Buscador.class.getName()).log(Level.INFO, "Teste: DeveRetornarArtigoEspecificoEmBuscaSemFiltrosTeste()");
        criarIndice3ArtigosIGAPOv10n1();
        Artigo artigoEsperado = ModeloCenario.criarArtigoIGAPOV10_N1_Prim();
        
        //O conteúdo do artigo é INDEXED, mas não STORED, então, o artigo que será recuperado terá conteúdo vazio
        artigoEsperado.setConteudo("");
        
        Artigo artigoBusca = new Artigo();
        artigoBusca.setConteudo("pescado");
        
        List<Artigo> artigosResultado = buscador.buscar(artigoBusca);
        
        assertTrue(artigosResultado.size() == 1 & artigoEsperado.equals(artigosResultado.get(0)));
    }
    
    /**
     * Verifica se cálculo da similaridade está próximo do esperado
     * A palavra pesquisada aparece em quantidades diferentes em todos os artigos-teste
     * @throws java.io.IOException
     * @throws org.apache.lucene.queryparser.classic.ParseException
     */
    @Test
    public void DeveRetornarArtigoNaPosicaoEsperadaTeste() throws IOException, ParseException{
        Logger.getLogger(Buscador.class.getName()).log(Level.INFO, "Teste: DeveRetornarArtigoNaPosicaoEsperadaTeste");
        criarIndice3ArtigosIGAPOv10n1();
        Artigo artigoEsperado = ModeloCenario.criarArtigoIGAPOV10_N1_Seg();
        
        //O conteúdo do artigo é INDEXED, mas não STORED, então, o artigo que será recuperado terá conteúdo vazio
        artigoEsperado.setConteudo("");
        
        Artigo artigoBusca = new Artigo();
        artigoBusca.setConteudo("estudo");
        
        List<Artigo> artigosResultado = buscador.buscar(artigoBusca);
        
        assertTrue(artigosResultado.size() == 3 & artigoEsperado.equals(artigosResultado.get(0)));
    }
    
    @Test
    public void deveFiltrarBuscaPorTituloEConteudoTeste() throws ParseException, IOException{
        Logger.getLogger(Buscador.class.getName()).log(Level.INFO, "Teste: deveFiltrarBuscaPorTituloEConteudoTeste");
        criarIndiceIGAPO_v9n2();
        criarIndiceIGAPO_v10n1();
        String tituloEsperado01 = "CARACTERIZAÇÃO DA PISCICULTURA NO MUNICÍPIO DE TABATINGA-AM";
        String tituloEsperado02 = "CARACTERÍSTICAS DA PISCICULTURA EM PRESIDENTE FIGUEIREDO, AMAZONAS";
        
        Artigo artigoBusca = new Artigo();
        artigoBusca.setTitulo("piscicultura");
        artigoBusca.setConteudo("peixe");
        
        List<Artigo> artigosResultado = buscador.buscar(artigoBusca);
        
        System.out.println("\n\n----------------------------------Resultado da busca\n");
        for(Artigo a: artigosResultado){
            System.out.println(a.getTitulo());
        }
        System.out.println("\n----------------------------------\n\n");
        
        String tituloRes01 = artigosResultado.get(0).getTitulo();
        String tituloRes02 = artigosResultado.get(1).getTitulo();
        
        assertTrue(artigosResultado.size() == 4 
                & tituloEsperado01.trim().equalsIgnoreCase(tituloRes01.trim()) 
                & tituloEsperado02.trim().equalsIgnoreCase(tituloRes02.trim()));
    }
    
    private void criarIndice3ArtigosIGAPOv10n1() throws IOException {
        String caminhoEdicao = Const.DIRETORIO_TESTES + "\\igapo_vol10_n1_2016_com3artigos.pdf";
        InputStream streamArquivo = Files.newInputStream(Paths.get(caminhoEdicao));
        Extrator extrator = Extrator.getExtrator(streamArquivo, "pdf", ModeloCenario.getEdicaoIGAPO_v10_n1());
        extrator.setCaminhoRepositorio(Const.DIRETORIO_REPOSITORIO_TESTES);
        List<Artigo> artigosRes = extrator.processarEdicao();
        
        Indexador indexador = new Indexador(artigosRes);
        indexador.setDirIndice(Const.DIRETORIO_INDICE_TESTES);
        indexador.indexa();
    }
    
    private void criarIndiceIGAPO_v9n2() throws IOException {
        String caminhoEdicao = Const.DIRETORIO_TESTES + "\\igapo_vol9_n2_2015_completa.pdf";
        InputStream streamArquivo = Files.newInputStream(Paths.get(caminhoEdicao));
        Extrator extrator = Extrator.getExtrator(streamArquivo, "pdf", ModeloCenario.getEdicaoIGAPO_v9_n2());
        extrator.setCaminhoRepositorio(Const.DIRETORIO_REPOSITORIO_TESTES);
        List<Artigo> artigosRes = extrator.processarEdicao();
        
        Indexador indexador = new Indexador(artigosRes);
        indexador.setDirIndice(Const.DIRETORIO_INDICE_TESTES);
        indexador.indexa();
    }
    
    private void criarIndiceIGAPO_v10n1() throws IOException {
        String caminhoEdicao = Const.DIRETORIO_TESTES + "\\igapo_vol10_n1_2016_completa.pdf";
        InputStream streamArquivo = Files.newInputStream(Paths.get(caminhoEdicao));
        Extrator extrator = Extrator.getExtrator(streamArquivo, "pdf", ModeloCenario.getEdicaoIGAPO_v10_n1());
        extrator.setCaminhoRepositorio(Const.DIRETORIO_REPOSITORIO_TESTES);
        List<Artigo> artigosRes = extrator.processarEdicao();
        
        Indexador indexador = new Indexador(artigosRes);
        indexador.setDirIndice(Const.DIRETORIO_INDICE_TESTES);
        indexador.indexa();
    }
    
    @After
    public void after() throws IOException {
        limparIndice();
        limparRepositorio();
    }
    
    //O índice precisa ser excluído ao final de cada teste, senão o próximo teste pode falhar
    private void limparIndice() throws IOException{
        Files.walkFileTree(Paths.get(Const.DIRETORIO_INDICE_TESTES), new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) throws IOException{
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }
        });
    }
    
    //Garante que nenhum teste dependa desta classe de testes
    private void limparRepositorio() throws IOException{
        Files.walkFileTree(Paths.get(Const.DIRETORIO_REPOSITORIO_TESTES), new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) throws IOException{
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }
            
            @Override
            public FileVisitResult postVisitDirectory(Path directory, IOException iOException) throws IOException{
                Files.delete(directory);
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
