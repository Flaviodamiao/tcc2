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
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import mvc.bean.Artigo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.validation.BindingResult;

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
            
            model.addAttribute("artigos", buscador.buscar(artigo));
            return "resultadoBusca";
        } catch (Exception ex) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
            model.addAttribute("msgErro", ex.getMessage());
            return "/index";
        }
    }
    
    /*
    @RequestMapping(value = "/recuperarArtigo")
    public String recuperarArtigo(Artigo artigo, BindingResult result, Model model){
        File arquivo = new File(artigo.getCaminho());
        return "";
    }//*/
}
