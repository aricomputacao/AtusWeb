/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author gilmario
 */
public class RegexUtil {

    public static Pattern getPattern(String regex) {
        return Pattern.compile(regex);
    }

    public static Matcher getMatcher(String regex, String valor) {
        return getPattern(regex).matcher(valor);
    }
}
