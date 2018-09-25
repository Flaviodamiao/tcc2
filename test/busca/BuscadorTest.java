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
package busca;

import java.io.IOException;
import java.util.List;
import modelo.Artigo;
import org.junit.Test;
import static org.junit.Assert.*;
import util.ModeloCenario;

/**
 *
 * @author Flávio Almeida
 */
public class BuscadorTest {
    
    public BuscadorTest() {
    }

    @Test
    public void DeveRetornarArtigoEspecificoEmBuscaSemFiltrosTeste() throws IOException {
        Artigo artigoEsperado = ModeloCenario.getUmArtigo();
        
        String termosPesquisa = "pescado";
        List<Artigo> artigosResultado = new Buscador().buscar(termosPesquisa);
        
        assertTrue(artigosResultado.size() == 1 & artigoEsperado.equals(artigosResultado.get(0)));
    }
}
