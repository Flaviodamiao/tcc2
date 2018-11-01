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

import util.Const;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import mvc.bean.Artigo;
import util.ModeloCenario;
import mvc.bean.Edicao;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 *
 * @author Flávio Almeida
 */
public class ExtratorPDFTest {
    private String edicaoEmUmArquivo;
    private String artigoEmUmArquivo;
    
    public ExtratorPDFTest() {
    }
    
    /**
     * Test of processarEdicao method, of class ExtratorPDF.
     * @throws java.io.IOException
     */
    @Test
    public void DeveExtrairUmArtigoTeste() throws IOException{
        System.out.println("DeveExtrairUmArtigoTeste");
        
        artigoEmUmArquivo = Const.DIRETORIO_TESTES + "\\artigo_p16-24.pdf";
        Artigo artigo = ModeloCenario.getUmArtigo();
        Edicao edicao = artigo.getEdicao();
        InputStream arquivo = Files.newInputStream(Paths.get(artigoEmUmArquivo));
        Extrator extrator = Extrator.getExtrator(arquivo, "pdf", edicao);
        List<Artigo> artigos = extrator.processarEdicao();
        
        assertTrue(artigos.get(0).equals(artigo) & artigos.size() == 1);
    }

    /**
     * Test of processarEdicao method, of class ExtratorPDF.
     * @throws java.io.IOException
     */
    @Test
    public void DeveExtrairVariosArtigosTeste() throws IOException {
        System.out.println("DeveExtrairVariosArtigosTeste");
        
        edicaoEmUmArquivo = Const.DIRETORIO_TESTES + "\\igapo_vol10_n1_2016_com3artigos.pdf";
        
        List<Artigo> artigosEsp = ModeloCenario.getTresArtigos();
        InputStream arquivo = Files.newInputStream(Paths.get(edicaoEmUmArquivo));
        Extrator extrator = Extrator.getExtrator(arquivo, "pdf", artigosEsp.get(0).getEdicao());
        List<Artigo> artigosRes = extrator.processarEdicao();
        
        assertTrue(artigosRes.get(0).equals(artigosEsp.get(0)) 
                & artigosRes.get(1).equals(artigosEsp.get(1))
                & artigosRes.get(2).equals(artigosEsp.get(2)));
    }
}
