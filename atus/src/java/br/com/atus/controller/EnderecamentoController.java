/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.atus.controller;

import br.com.atus.dao.EnderecamentoDAO;
import br.com.atus.modelo.Enderecamento;
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
