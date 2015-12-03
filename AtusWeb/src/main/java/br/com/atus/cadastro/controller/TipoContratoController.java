/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.atus.cadastro.controller;

import br.com.atus.interfaces.Controller;
import br.com.atus.cadastro.dao.TipoContratoDAO;
import br.com.atus.cadastro.managedbean.TipoContratoMB;
import br.com.atus.cadastro.modelo.TipoContrato;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Ari
 */
@Stateless
public class TipoContratoController extends Controller<TipoContrato, Integer> implements Serializable {
    
    @EJB
    private TipoContratoDAO dao;

    @PostConstruct
    @Override
    protected void inicializaDAO() {
        setDAO(dao);
    }

 
    
}
