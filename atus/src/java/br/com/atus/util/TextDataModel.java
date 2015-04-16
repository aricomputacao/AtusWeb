/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.util;

import java.util.List;
import javax.faces.model.ListDataModel;
import org.docx4j.wml.Text;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author Ari
 */
public class TextDataModel extends ListDataModel<Text> implements SelectableDataModel<Text> {

    public TextDataModel() {
    }

    public TextDataModel(List<Text> list) {
        super(list);
    }

    
   

    
    @Override
    public Object getRowKey(Text t) {
         List<Text> txs = (List<Text>) getWrappedData();

        for(Text tx : txs) {
            if(tx.getValue().equals(t))
                return tx;
        }

        return null;
    }

    @Override
    public Text getRowData(String string) {
        Text text = new Text();
        text.setValue(string);
        return text;
    }

    
}
