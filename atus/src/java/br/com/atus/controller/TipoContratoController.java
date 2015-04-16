/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.atus.controller;

import br.com.atua.interfaces.Controller;
import br.com.atus.dao.TipoContratoDAO;
import br.com.atus.managedbean.TipoContratoMB;
import br.com.atus.modelo.TipoContrato;
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
