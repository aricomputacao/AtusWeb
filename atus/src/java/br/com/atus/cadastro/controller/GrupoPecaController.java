/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.cadastro.controller;

import br.com.atus.interfaces.Controller;
import br.com.atus.cadastro.dao.GrupoPecaDAO;
import br.com.atus.modelo.GrupoPeca;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author gilmario
 */
@Stateless
public class GrupoPecaController extends Controller<GrupoPeca, Integer> implements Serializable {

    @EJB
    private GrupoPecaDAO dao;

    @Override
    @PostConstruct
    protected void inicializaDAO() {
        setDAO(dao);
    }

}
