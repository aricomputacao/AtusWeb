/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.atus.controller;

import br.com.atus.dao.FaseDAO;
import br.com.atus.modelo.Fase;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author ari
 */
@Stateless
public class FaseController extends Controller<Fase, Long> implements Serializable {

    @EJB
    private FaseDAO dao;
    
    @PostConstruct
    @Override
    protected void inicializaDAO() {
        setDAO(dao);
    }
    
    
}
