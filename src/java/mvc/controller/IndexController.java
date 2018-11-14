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
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mvc.bean.Artigo;
import mvc.bean.Edicao;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
    public String realizarBusca(/*@Valid*/Artigo artigo, BindingResult result, Model model){
        try {
            /*
            for(Artigo art: buscador.buscar(artigo)){
                System.out.println("_______________---------- -- - - -" + art.getCaminho());
            }//*/
            
            model.addAttribute("artigos", buscador.buscar(artigo, new HashMap<>()));
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
    
    @RequestMapping(value = "/indexarArtigo")
    public String indexarArtigo(@RequestParam("arquivo") MultipartFile mpFile, Edicao edicao, Model model){
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
}
