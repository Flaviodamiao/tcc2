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
package modelo;

import util.Const;

/**
 *
 * @author Flávio Almeida
 */
public enum Revista {
    IGAPO(
            Const.PADRAO_CABECALHO_IGP,
            Const.PADRAO_INICIO_ARTIGO_IGP,
            Const.PADRAO_FIM_ANALISE_IGP
        ),
    NEXUS(
            Const.PADRAO_CABECALHO_NXS,
            Const.PADRAO_INICIO_ARTIGO_NXS,
            Const.PADRAO_FIM_ANALISE_NXS
    ),
    EDUCITEC(
            Const.PADRAO_CABECALHO_EDCT,
            Const.PADRAO_INICIO_ARTIGO_EDCT,
            Const.PADRAO_FIM_ANALISE_EDCT
    );
    
    private final String padraoCabecalho;
    private final String padraoInicioArtigo;
    private final String padraoFimAnalise;
    
    public String getPadraoCabecalho(){
        return padraoCabecalho;
    }

    public String getPadraoInicioArtigo() {
        return padraoInicioArtigo;
    }

    public String getPadraoFimAnalise() {
        return padraoFimAnalise;
    }
    
    
    private Revista(String padraoCabecalho, String padraoInicioArtigo, String padraoFimAnalise){
        this.padraoCabecalho = padraoCabecalho;
        this.padraoInicioArtigo = padraoInicioArtigo;
        this.padraoFimAnalise = padraoFimAnalise;
    }
}
