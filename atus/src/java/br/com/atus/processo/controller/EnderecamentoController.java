/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.atus.processo.controller;

import br.com.atus.interfaces.Controller;
import br.com.atus.processo.dao.EnderecamentoDAO;
import br.com.atus.processo.modelo.Enderecamento;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author ari
 */
@Stateless
public class EnderecamentoController extends Controller<Enderecamento,Long> implements Serializable{

    @EJB
    private EnderecamentoDAO dao;
    
    @PostConstruct
    @Override
    protected void inicializaDAO() {
        setDAO(dao);
    }
    
}
