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

package mvc.bean;

import java.util.List;

/**
 *
 * @author Flávio Almeida
 */
public class Artigo {
    private String titulo;
    private List<String> autores;
    
    //No processo busca, o conteudo não é recuperado do índice, somente no próprio arquivo
    private String conteudo = "";
    
    private String caminho;
    private Edicao edicao;
    
    public boolean equals(Artigo artigo){
        return  artigo != null
                & this.titulo.equalsIgnoreCase(artigo.getTitulo())
                & this.conteudo.equalsIgnoreCase(artigo.getConteudo()) //Útil nos testes de extração e para exibição de trechos em buscas
                & this.edicao.equals(edicao)
                & this.autores.containsAll(artigo.autores)
                & artigo.autores.containsAll(this.autores);
    }
    
    @Override
    public String toString(){
        System.out.println("Artigo é objeto não nulo: " + (this != null)
                + "\nTítulo: " + titulo
                + "\nConteudo: " + conteudo
                + "\nCaminho: " + caminho
        );
        
        System.out.println("Autores:");
        
        for(String autor: autores){
            System.out.println("\t" + autor);
        }
        
        edicao.toString();
        
        return null;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<String> getAutores() {
        return autores;
    }

    public void setAutores(List<String> autores) {
        this.autores = autores;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

    public Edicao getEdicao() {
        return edicao;
    }

    public void setEdicao(Edicao edicao) {
        this.edicao = edicao;
    }
}
