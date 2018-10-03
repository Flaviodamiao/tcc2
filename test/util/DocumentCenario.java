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

import java.io.IOException;
import modelo.Artigo;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.IndexOptions;

/**
 *
 * @author Flávio Almeida
 */
public class DocumentCenario {
    private DocumentCenario(){
        
    }
    
    public static Document getUmDocument() throws IOException{
        return criarDocumentoUm();
    }
    
    private static Document criarDocumentoUm() throws IOException{
        Artigo artigo = ModeloCenario.getUmArtigo();
        Document documento = new Document();
        FieldType fieldType = new FieldType();
        String autores = "";
        
        for(String s: artigo.getAutores()){
            autores += s + " - ";
        }
        
        fieldType.setIndexOptions(IndexOptions.NONE);
        fieldType.setStored(true);
        
        documento.add(new Field("titulo", artigo.getTitulo(), fieldType));
        documento.add(new Field("autores", autores, fieldType));
        documento.add(new Field("caminho", artigo.getCaminho(), fieldType));
        documento.add(new Field("numeroEdicao", Integer.toString(artigo.getEdicao().getNumero()), fieldType));
        documento.add(new Field("volumeEdicao", Integer.toString(artigo.getEdicao().getVolume()), fieldType));
        documento.add(new Field("anoEdicao", Integer.toString(artigo.getEdicao().getAno()), fieldType));
        documento.add(new Field("revista", artigo.getEdicao().getRevista().toString(), fieldType));
        return documento;
    }
}
