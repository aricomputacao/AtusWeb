/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.atus.controller;

import br.com.atua.interfaces.Controller;
import br.com.atus.dao.ParteInteressadaDAO;
import br.com.atus.modelo.ParteInteressada;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author ari
 */
@Stateless
public class ParteInteressadaController extends Controller<ParteInteressada, Long> implements Serializable{

    @EJB
    private ParteInteressadaDAO dao;
    
    @PostConstruct
    @Override
    protected void inicializaDAO() {
        setDAO(dao);
    }
    
}
