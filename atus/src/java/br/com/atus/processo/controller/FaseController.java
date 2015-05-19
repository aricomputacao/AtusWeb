/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.atus.processo.controller;

import br.com.atus.interfaces.Controller;
import br.com.atus.processo.dao.FaseDAO;
import br.com.atus.modelo.Fase;
import java.io.Serializable;
import java.util.List;
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

    public List<Fase> listarTodosOrderNome() {
        return dao.listarTodosOrderNome();
    }
    
    
}
