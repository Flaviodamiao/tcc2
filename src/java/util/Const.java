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
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;

/**
 *
 * @author Flávio Almeida
 */
public class Const {
    //Quando for aplicar o padrao Singleton
    private static Const instanciaUnica;
    private final String contextPath;
    
    private Const(HttpServlet httpServlet){
        this.contextPath = httpServlet.getServletContext().getContextPath();
    }
    
    /**
     * Pastas de trabalho do sistema em produção
     */
    
    public static final String DIRETORIO_SISTEMA = new File(Const.class.getClassLoader().getResource("").getPath()).getParentFile().getParentFile().getParentFile().getParentFile().getAbsolutePath();
    public static final String DIRETORIO_INDICE = DIRETORIO_SISTEMA + "\\build\\web\\indice";
    public static final String DIRETORIO_REPOSITORIO = DIRETORIO_SISTEMA + "\\build\\web\\repositorio";
    public static final String CONTEXTO_REPOSITORIO = "\\TCC2\\repositorio";
    
    //As classes de teste dependem diretamente destas constantes
    public static final String DIRETORIO_TESTES = new File("").getAbsolutePath() + "\\test\\arquivos";
    public static final String DIRETORIO_REPOSITORIO_TESTES = new File("").getAbsolutePath() + "\\test\\repositorio-teste";
    public static final String DIRETORIO_INDICE_TESTES = new File("").getAbsolutePath() + "\\test\\indice-teste";
    
    
    //public static final String DIRETORIO_SISTEMA = DIRETORIO_TESTES;
    //public static final String DIRETORIO_REPOSITORIO = DIRETORIO_REPOSITORIO_TESTES;
    //public static final String DIRETORIO_INDICE = DIRETORIO_INDICE_TESTES;
    
    /**
     * Configurações para extrair artigos de cada revista.
     * Configurações para extrair artigos da Revista IGAPÓ
     */
    public static final String PADRAO_INICIO_ARTIGO_IGP = "resumo";
    public static final String PADRAO_FIM_ANALISE_IGP = "(Modelo de artigo da Revista Igapó)";
    public static final String FONTE_TITULO_IGP = "MyriadPro-Bold";
    public static final int TAM_FONTE_TITULO_IGP = 12;
    public static final int TAM_FONTE_NOTA_RODAPE_IGP = 9;
    public static final String PADRAO_CABECALHO_IGP = "REVISTA DE EDUCAÇÃO, CIÊNCIA E TECNOLOGIA DO IFAM";
    public static final String PADRAO_RODAPE_IGP = "";
    
    /**
     * Configurações para extrair artigos da Revista NEXUS
     */
    public static final String PADRAO_INICIO_ARTIGO_NXS = "resumo";
    public static final String PADRAO_FIM_ANALISE_NXS = "(Modelo de artigo da Revista Igapó)";
    public static final String FONTE_TITULO_NXS = "MyriadPro-Bold";
    public static final int TAM_FONTE_TITULO_NXS = 12;
    public static final int TAM_FONTE_NOTA_RODAPE_NXS = 9;
    public static final String PADRAO_CABECALHO_NXS = "REVISTA DE EDUCAÇÃO, CIÊNCIA E TECNOLOGIA DO IFAM";
    public static final String PADRAO_RODAPE_NXS = "";
    
    /**
     * Configurações para extrair artigos da Revista EDUCITEC
     */
    public static final String PADRAO_INICIO_ARTIGO_EDCT = "resumo";
    public static final String PADRAO_FIM_ANALISE_EDCT = "(Modelo de artigo da Revista Igapó)";
    public static final String FONTE_TITULO_EDCT = "MyriadPro-Bold";
    public static final int TAM_FONTE_TITULO_EDCT = 12;
    public static final int TAM_FONTE_NOTA_RODAPE_EDCT = 9;
    public static final String PADRAO_CABECALHO_EDCT = "REVISTA DE EDUCAÇÃO, CIÊNCIA E TECNOLOGIA DO IFAM";
    public static final String PADRAO_RODAPE_EDCT = "";
    
    
    
    /**
     * Nomenclatura dos campos utilizados nos documentos Lucene
     * Modificar após a indexação de algum artigo, pode torná-lo ilocalizável
     */
    public static final String CAMPO_TITULO = "titulo";
    public static final String CAMPO_AUTORES = "autores";
    public static final String CAMPO_CONTEUDO = "conteudo";
    public static final String CAMPO_CAMINHO = "caminho";
    public static final String CAMPO_NUMERO_EDICAO = "numeroEdicao";
    public static final String CAMPO_VOLUME_EDICAO = "volumeEdicao";
    public static final String CAMPO_ANO_EDICAO = "anoEdicao";
    public static final String CAMPO_REVISTA = "revista";
}
