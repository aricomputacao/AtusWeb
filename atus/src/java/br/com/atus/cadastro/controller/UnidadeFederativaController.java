/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.atus.cadastro.controller;

import br.com.atus.interfaces.Controller;
import br.com.atus.cadastro.dao.UnidadeFederativaDAO;
import br.com.atus.cadastro.modelo.UnidadeFederativa;
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
