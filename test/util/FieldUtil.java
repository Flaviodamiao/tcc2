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

import java.util.List;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.IndexableField;

/**
 *
 * @author Flávio Almeida
 */
public class FieldUtil {
    
    /**
     * Compara o name e o value de cada {@link IndexableField} dos dois {@link Document} passados.
     * Não compara o conteúdo, pois está configurado como {@link Field.Store.NO}
     * @param docEsp Documento com os valores esperados para a comparação
     * @param docRes Documento a ser verificado
     * @return 
     */
    public static boolean documentsSaoIguais(Document docEsp, Document docRes){
        List<IndexableField> fieldsEspList = docEsp.getFields();
        List<IndexableField> fieldsResList = docRes.getFields();
        boolean possuiFieldsIguais = false;
        
        if (fieldsEspList.size() == fieldsResList.size()){
            for (IndexableField fieldEsp : fieldsEspList) {
                for(IndexableField fieldRes: fieldsResList){
                    if(fieldEsp.name().equals(fieldRes.name()) 
                            & fieldEsp.stringValue().equalsIgnoreCase(fieldRes.stringValue())){
                        possuiFieldsIguais = true;
                        break;
                    }
                    possuiFieldsIguais = false;
                }
                if(!possuiFieldsIguais){
                    break;
                }
            }
            return possuiFieldsIguais;
        }
        
        return false;
    }
}
