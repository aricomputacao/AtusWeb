/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.util;

import br.com.atus.util.peca.TipoMascara;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author gilmario
 */
public class MascaraUtil {

    public static String formatarValor(Object valor, String mascara, TipoMascara tipo) {
        if (valor == null) {
            return "";
        }
        if (TipoMascara.CPF_CNPJ.equals(tipo)) {
            if (valor.toString().length() == 11) {
                return formatar(valor.toString(), "###.###.###-##");
            } else {
                return formatar(valor.toString(), "##.###.###/####-##");
            }
        } else if (TipoMascara.DATA.equals(tipo)) {
            return formatar((Date) valor, "dd/MM/YYYY");
        } else if (TipoMascara.DINHEIRO.equals(tipo)) {
            return formatar((BigDecimal) valor, "#,##0.00");
        } else {
            return formatar(valor.toString(), mascara);
        }
    }

    public static String formatar(String valor, String mascara) {
        if (mascara.replaceAll("[^#]", "").length() == valor.length()) {
            String retorno = "";
            int j = 0;
            for (int i = 0; i < mascara.length(); i++) {
                if (mascara.substring(i, i + 1).equals("#")) {
                    retorno += valor.substring(j, j + 1);
                    j++;
                } else {
                    retorno += mascara.substring(i, i + 1);
                }
            }
            return retorno;
        } else {
            return valor;
        }
    }

    public static String formatar(Date valor, String mascara) {
        return new SimpleDateFormat(mascara).format(valor);
    }

    public static String formatar(BigDecimal valor, String mascara) {
        return new DecimalFormat(mascara).format(valor);
    }

}
