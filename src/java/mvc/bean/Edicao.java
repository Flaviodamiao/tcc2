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

/**
 *
 * @author Flávio Almeida
 */
public class Edicao {
    private Revista revista;
    private int numero;
    private int volume;
    private int ano;
    
    public boolean equals(Edicao edicao){
        return    this.revista == edicao.getRevista()
                & this.numero == edicao.getNumero()
                & this.volume == edicao.getVolume()
                & this.ano == edicao.getAno();
    }

    public void imprimir() {
        System.out.println("Edição é objeto não nulo: " + (this != null)
                + "\nRevista: " + revista
                + "\nVolume: " + volume
                + "\nNumero: " + numero
                + "\nAno: " + ano
        );
    }

    public Revista getRevista() {
        return revista;
    }

    public void setRevista(Revista revista) {
        this.revista = revista;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }
    
    
}
