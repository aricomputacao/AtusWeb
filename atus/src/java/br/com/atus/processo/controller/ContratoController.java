/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.processo.controller;

import br.com.atus.interfaces.Controller;
import br.com.atus.processo.dao.ContratoDAO;
import br.com.atus.modelo.Contrato;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author gilmario
 */
@Stateless
public class ContratoController extends Controller<Contrato, Long> implements Serializable {

    @EJB
    private ContratoDAO dao;

    @Override
    @PostConstruct
    protected void inicializaDAO() {
        setDAO(dao);
    }

}
