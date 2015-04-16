/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.atus.controller;

import br.com.atua.interfaces.Controller;
import br.com.atus.dao.UnidadeFederativaDAO;
import br.com.atus.modelo.UnidadeFederativa;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author ari
 */
@Stateless
public class UnidadeFederativaController extends Controller<UnidadeFederativa, Integer> implements Serializable{

    @EJB
    private UnidadeFederativaDAO dao;
    
    @PostConstruct
    @Override
    protected void inicializaDAO() {
        setDAO(dao);
    }

    public UnidadeFederativa buscaAbreviacao(String abre) {
       return dao.buscaAbreviacao(abre);
    }
    
}
