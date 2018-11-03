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

package util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import mvc.bean.Artigo;
import mvc.bean.Edicao;
import mvc.bean.Revista;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

/**
 *
 * @author Flávio Almeida
 * Classe utilizada para criar artigos para os cenários de testes
 */
public class ModeloCenario {
    private static Edicao edicao = criarEdicaoIGAPO_v10_n1();
    private static String repositorio = "\\" + 
            edicao.getRevista() + "\\vol." + edicao.getVolume() + "\\N-" + edicao.getNumero();;
    
    private ModeloCenario(){
        
    }
    
    public static Edicao getEdicaoIGAPO_v10_n1(){
        return edicao;
    }
    
    public static Edicao getEdicaoIGAPO_v9_n2(){
        return criarEdicaoIGAPO_v9_n2();
    }
    
    public static Artigo getUmArtigo() throws IOException{
        Artigo artigo = criarArtigoIGAPOV10_N1_Prim();
        
        //Este valor deve ser atribuído pois, como o arquivo possui um único artigo,
        //ao ser extraído, o sistema encontra seu início na primeira página
        artigo.setCaminho(repositorio + "\\artigo_IGAPO_vol-10_N-1_pags1-9.pdf");
        
        return artigo;
    }
    
    public static List<Artigo> getTresArtigosIGAPO_V10_N1() throws IOException{
        List<Artigo> artigos = new ArrayList<>();
        artigos.add(criarArtigoIGAPOV10_N1_Prim());
        artigos.add(criarArtigoIGAPOV10_N1_Seg());
        artigos.add(criarArtigoIGAPOV10_N1_Terc());
        
        return artigos;
    }
    
    private static Edicao criarEdicaoIGAPO_v10_n1(){
        Edicao edicao = new Edicao();
        edicao.setRevista(Revista.IGAPO);
        edicao.setAno(2016);
        edicao.setVolume(10);
        edicao.setNumero(1);
        
        return edicao;
    }
    
    private static Edicao criarEdicaoIGAPO_v9_n2(){
        Edicao edicao = new Edicao();
        edicao.setRevista(Revista.IGAPO);
        edicao.setAno(2015);
        edicao.setVolume(9);
        edicao.setNumero(2);
        
        return edicao;
    }
    
    public static Artigo criarArtigoIGAPOV10_N1_Prim() throws IOException{
        Artigo artigo = new Artigo();
        List<String> autores = new ArrayList<>();
        
        autores.add("Fabio Junior Ferreira da Silva");
        autores.add("Paulo Henrique Rocha Aride");
        autores.add("Suelen Miranda dos Santos");
        autores.add("Jackson Pantoja-Lima");
        autores.add("Adriano Teixeira de Oliveira");
        
        artigo.setTitulo("COMPRA DO PESCADO NA FEIRA DE JURUÁ: FATORES QUE INFLUENCIAM NA TOMADA DE DECISÃO");
        artigo.setAutores(autores);
        artigo.setConteudo(new PDFTextStripper().getText(PDDocument.load(new File(Const.DIRETORIO_TESTES + "\\artigo_p16-24.pdf"))));
        artigo.setCaminho(repositorio + "\\artigo_IGAPO_vol-10_N-1_pags16-24.pdf");
        artigo.setEdicao(edicao);
        
        return artigo; 
    }
    
    public static Artigo criarArtigoIGAPOV10_N1_Seg() throws IOException{
        Artigo artigo = new Artigo();
        List<String> autores = new ArrayList<>();
        
        autores.add("José Carlos Ramos Monteiro");
        autores.add("Paulo Henrique Rocha Aride");
        autores.add("Adriano Teixeira de Oliveira");
        autores.add("Suelen Miranda dos Santos");
        autores.add("Jackson Pantoja-Lima");
        autores.add("Ligia Fonseca Heyer");
        
        artigo.setTitulo("DESCRIÇÃO DA TEMPERATURA E UMIDADE RELATIVA DO AR EM DISTINTAS LOCALIDADES DA "
                + "CIDADE DE MANAUS COM DIFERENTES GEOMETRIZAÇÕES E ESPACIALIDADES URBANAS NOS BAIRROS DO "
                + "PARQUE DEZ, NOVA CIDADE E BAIRRO CENTRO - MANAUS/AM");
        artigo.setAutores(autores);
        artigo.setConteudo(new PDFTextStripper().getText(PDDocument.load(new File(Const.DIRETORIO_TESTES + "\\artigo_p25-45.pdf"))));
        artigo.setCaminho(repositorio + "\\artigo_IGAPO_vol-10_N-1_pags25-45.pdf");
        artigo.setEdicao(edicao);
        
        return artigo;
    }
    
    public static Artigo criarArtigoIGAPOV10_N1_Terc() throws IOException{
        Artigo artigo = new Artigo();
        List<String> autores = new ArrayList<>();
        
        autores.add("Rodrigo de Souza Amaral");
        autores.add("Bárbara Luiza Migueis Nunes");
        autores.add("Mayara Fonseca Ferreira");
        autores.add("Jonatas Maciel Claudio");
        
        artigo.setTitulo("AVALIAÇÃO DOS NÍVEIS DE METABÓLITOS FECAIS DE TESTOSTERONA E ESTRADIOL EM SUÍNOS");
        artigo.setAutores(autores);
        artigo.setConteudo(new PDFTextStripper().getText(PDDocument.load(new File(Const.DIRETORIO_TESTES + "\\artigo_p46-56.pdf"))));
        artigo.setCaminho(repositorio + "\\artigo_IGAPO_vol-10_N-1_pags46-56.pdf");
        artigo.setEdicao(edicao);
        
        return artigo;
    }
}
