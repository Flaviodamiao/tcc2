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

package mvc.controller;

import busca.Buscador;
import extracao.Extrator;
import indexacao.Indexador;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import mvc.bean.Artigo;
import mvc.bean.Edicao;
import org.apache.lucene.search.BooleanClause;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import util.Const;

/**
 *
 * @author Flávio Almeida
 */

@Controller
public class IndexController {
    private final Buscador buscador;
    
    public IndexController(){
        buscador = new Buscador();
    }
    
    @RequestMapping(value = {"/", "/index"})
    public String index(){
        return "index";
    }
    
    @RequestMapping(value = "/realizarBusca")
    public String realizarBusca(@RequestParam("filtroConteudo") BooleanClause.Occur filtroConteudo,
            @RequestParam("filtroTitulo") BooleanClause.Occur filtroTitulo,
            @RequestParam("filtroAutores") BooleanClause.Occur filtroAutor,
            @RequestParam("filtroRevista") BooleanClause.Occur filtroRevista,
            @RequestParam("filtroAno") BooleanClause.Occur filtroAno,
            @RequestParam("filtroVolume") BooleanClause.Occur filtroVolume,
            @RequestParam("filtroNumero") BooleanClause.Occur filtroNumero,
            Artigo artigo, Edicao edicao, BindingResult result, Model model){
        try {
            Map<String, BooleanClause.Occur> filtros = new HashMap<>();
            
            if(!artigo.getConteudo().isEmpty()){
                System.out.println("\n\n-----------------\nFIELD:  artigo.getConteudo()");
                filtros.put(Const.CAMPO_CONTEUDO, filtroConteudo);
            }
            if(!artigo.getTitulo().isEmpty()){
                System.out.println("\n\n-----------------\nFIELD:  artigo.getTitulo()" + artigo.getTitulo());
                System.out.println("\n\n-----------------\nFILTRO: " + filtroTitulo.name());
                filtros.put(Const.CAMPO_TITULO, filtroTitulo);
            }
            if(!artigo.getAutores().isEmpty()){
                System.out.println("\n\n-----------------\nFIELD: artigo.getAutores()");
                filtros.put(Const.CAMPO_AUTORES, filtroAutor);
            }
            if(edicao != null){
                artigo.setEdicao(edicao);
                
                if(artigo.getEdicao().getRevista() != null){
                System.out.println("\n\n-----------------\nFIELD: artigo.getEdicao().getRevista()");
                    filtros.put(Const.CAMPO_REVISTA, filtroRevista);
                }
                if(artigo.getEdicao().getAno() > 0){
                System.out.println("\n\n-----------------\nFIELD: artigo.getEdicao().getAno()");
                    filtros.put(Const.CAMPO_ANO_EDICAO, filtroAno);
                }
                if(artigo.getEdicao().getVolume() > 0){
                System.out.println("\n\n-----------------\nFIELD: artigo.getEdicao().getVolume()");
                    filtros.put(Const.CAMPO_VOLUME_EDICAO, filtroVolume);
                }
                if(artigo.getEdicao().getNumero() > 0){
                System.out.println("\n\n-----------------\nFIELD: artigo.getEdicao().getNumero()");
                    filtros.put(Const.CAMPO_NUMERO_EDICAO, filtroNumero);
                }
            }
            System.out.println("\n\n----------------------------------Resultado da busca\n");
            for(Artigo a: buscador.buscar(artigo, filtros)){
                System.out.println(a.getTitulo());
            }
            System.out.println("\n----------------------------------\n\n");
        
            model.addAttribute("artigos", buscador.buscar(artigo, filtros));
            return "resultadoBusca";
        } catch (Exception ex) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
            model.addAttribute("msgErro", ex.getMessage());
            return "/index";
        }
    }
    
    @RequestMapping(value = "/recuperarArtigo", method = RequestMethod.GET)
    public HttpEntity<byte[]> recuperarArtigo(Artigo artigo, BindingResult result, Model model){
        HttpEntity<byte[]> httpEntity = null;
        System.out.println("\n\n\n --------- \nRECUPERAR ARTIGO : " + artigo.getCaminho() + "\n\n---------------\n\n");
        try {
            byte[] bytesArq = Files.readAllBytes(Paths.get(Const.DIRETORIO_REPOSITORIO + artigo.getCaminho()));
            HttpHeaders httpHeaders = new HttpHeaders();
            String nomeArquivo = artigo.getCaminho().substring(artigo.getCaminho().lastIndexOf("\\"));
            httpHeaders.add("Content-Disposition", "attachment; filename=\"" + nomeArquivo + "\"");
            httpEntity = new HttpEntity(bytesArq, httpHeaders);
        } catch (IOException ex) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return httpEntity;
    }
    
    @RequestMapping(value = "/formIndexarArtigo")
    public String formIndexarArtigo(){
        return "/formIndexarArtigo";
    }
    
    @RequestMapping(value = "/extrairArtigo")
    public String extrairArtigo(@RequestParam("arquivo") MultipartFile mpFile, Edicao edicao, Model model){
        try {
            edicao.imprimir();
            InputStream arquivo = new ByteArrayInputStream(mpFile.getBytes());
            String nome = mpFile.getContentType();
            System.out.println("\n\n ----- TIPO ARQUIVO: " + nome + "\n------------\n\n");
            Extrator extrator = Extrator.getExtrator(arquivo, nome, edicao);
            model.addAttribute("artigos", extrator.processarEdicao());
        } catch (Exception ex) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
            model.addAttribute("msgErro", "");
        }
        return "/listaArtigosExtraidos";
    }
    
    @RequestMapping(value = "indexarArtigo")
    public String indexarArtigo(@ModelAttribute("artigos") ArrayList<Artigo> artigos, Model model, BindingResult result){
        Indexador indexador = new Indexador(artigos);
        indexador.indexa();
        model.addAttribute("msgErro", "Artigos indexados com sucesso!");
        return "/formIndexarArtigo";
    }
}
