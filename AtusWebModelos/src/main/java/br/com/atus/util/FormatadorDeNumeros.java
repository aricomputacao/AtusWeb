/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 *
 * @author ari
 */
public class FormatadorDeNumeros {

    public static String converterBigDecimalEmStrng(BigDecimal bd) {
        DecimalFormat numFormat;
        String number;
        // use of , to group numbers
        numFormat = new DecimalFormat("#,##0.00");
        number = numFormat.format(bd);
        return number;

    }
}
