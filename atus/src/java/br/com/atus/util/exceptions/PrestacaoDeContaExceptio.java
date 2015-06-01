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
 * @author ari
 */
public class PrestacaoDeContaExceptio extends Exception {

    public PrestacaoDeContaExceptio() {
    }

    public PrestacaoDeContaExceptio(String message) {
        super(NavegacaoMB.getMsg("prestacao_conta_falha", MenssagemUtil.MENSAGENS));

    }

}
