/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.util.exceptions;

import br.com.atus.util.MenssagemUtil;
import br.com.atus.util.managedbean.NavegacaoMB;

/**
 *
 * @author Ari
 */

public class PorcentagemException extends Exception{

    public PorcentagemException() {
    }

    public PorcentagemException(String message) {
        super(NavegacaoMB.getMsg("falha_soma_cooptacao", MenssagemUtil.MENSAGENS));
    }
    
    
}
