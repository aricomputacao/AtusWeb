/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.atus.cadastro.controller;

import br.com.atus.interfaces.Controller;
import br.com.atus.cadastro.dao.NacionalidadeDAO;
import br.com.atus.modelo.Nacionalidade;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author ari
 */
@Stateless
public class NacionalidadeController extends Controller<Nacionalidade, Integer> implements Serializable{

    @EJB
    private NacionalidadeDAO dao;
    
    @PostConstruct
    @Override
    protected void inicializaDAO() {
        setDAO(dao);
    }
    
}
