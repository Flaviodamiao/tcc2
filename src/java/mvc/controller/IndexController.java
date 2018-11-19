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
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletResponse;
import mvc.bean.Artigo;
import mvc.bean.Edicao;
import org.apache.catalina.connector.Response;
import org.apache.lucene.search.BooleanClause;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import util.Const;

/**
 *
 * @author Flávio Almeida
 */

@Controller
public class IndexController {
    private final Buscador buscador;
    private List<Artigo> artigos;
    
    public IndexController(){
        buscador = new Buscador();
    }
    
    @RequestMapping(value = {"/", "/index"}, produces = "text/plain;charset=UTF-8")
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
                artigo.setConteudo(new String(artigo.getConteudo().getBytes(Charset.forName("ISO-8859-1")), StandardCharsets.UTF_8));
                filtros.put(Const.CAMPO_CONTEUDO, filtroConteudo);
            }
            if(!artigo.getTitulo().isEmpty()){
                artigo.setTitulo(new String(artigo.getTitulo().getBytes(Charset.forName("ISO-8859-1")), StandardCharsets.UTF_8));
                filtros.put(Const.CAMPO_TITULO, filtroTitulo);
            }
            if(!artigo.getAutores().isEmpty()){
                artigo.setAutores(new String(artigo.getAutores().getBytes(Charset.forName("ISO-8859-1")), StandardCharsets.UTF_8));
                filtros.put(Const.CAMPO_AUTORES, filtroAutor);
            }
            if(edicao != null){
                artigo.setEdicao(edicao);
                
                if(artigo.getEdicao().getRevista() != null){
                    filtros.put(Const.CAMPO_REVISTA, filtroRevista);
                }
                if(artigo.getEdicao().getAno() > 0){
                    filtros.put(Const.CAMPO_ANO_EDICAO, filtroAno);
                }
                if(artigo.getEdicao().getVolume() > 0){
                    filtros.put(Const.CAMPO_VOLUME_EDICAO, filtroVolume);
                }
                if(artigo.getEdicao().getNumero() > 0){
                    filtros.put(Const.CAMPO_NUMERO_EDICAO, filtroNumero);
                }
            }
            
            List<Artigo> artigosResultado = buscador.buscar(artigo, filtros);
            
            if(!artigosResultado.isEmpty()){
                model.addAttribute("artigos", artigosResultado);
                return "resultadoBusca";
            }else{
                model.addAttribute("msgErro", "Não foi encontrado nenhum artigo com os parâmetros informados.");
                return "/index";
            }
        } catch (Exception ex) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
            model.addAttribute("msgErro", ex.getMessage());
            return "/index";
        }
    }
    
    @RequestMapping(value = "/recuperarArtigo", method = RequestMethod.GET)
    public HttpEntity<byte[]> recuperarArtigo(Artigo artigo, BindingResult result, Model model){
        HttpEntity<byte[]> httpEntity = null;
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
    
    @RequestMapping(value = "/extrairArtigo", produces = "text/html;charset=UTF-8")
    public String extrairArtigo(@RequestParam("arquivo") MultipartFile mpFile, Edicao edicao, Model model){
        try {
            HttpServletResponse h = new Response();
            InputStream arquivo = new ByteArrayInputStream(mpFile.getBytes());
            String nome = mpFile.getContentType();
            Extrator extrator = Extrator.getExtrator(arquivo, nome, edicao);
            artigos = extrator.processarEdicao();
            model.addAttribute("artigos", artigos);
        } catch (Exception ex) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
            model.addAttribute("msgErro", "");
        }
        return "/listaArtigosExtraidos";
    }
    
    @RequestMapping(value = "indexarArtigo")
    public String indexarArtigo(@RequestParam("titulo") String[] titulos,
            @RequestParam("autores") String[] autores,
            Model model){
        try{
            System.out.println("\n\n-----------------------\n\n");
            for(int i = 0; i < artigos.size(); i++){
                artigos.get(i).setTitulo(new String(titulos[i].getBytes(Charset.forName("ISO-8859-1")), StandardCharsets.UTF_8));
                artigos.get(i).setAutores(new String(autores[i].getBytes(Charset.forName("ISO-8859-1")), StandardCharsets.UTF_8));
                System.out.println("TÍTULO: " + artigos.get(i).getTitulo() + "\n"
                        + "AUTORES: " + artigos.get(i).getAutores());
            }
            System.out.println("\n\n---------------------\n\n");

            Indexador indexador = new Indexador(artigos);
            indexador.indexa();
            model.addAttribute("msgErro", "Artigos indexados com sucesso!");
        } catch (Exception e){
            model.addAttribute("msgErro", e.getMessage());
        }
        
        return "/formIndexarArtigo";
    }
}
