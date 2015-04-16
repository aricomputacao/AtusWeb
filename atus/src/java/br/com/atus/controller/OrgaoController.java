/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.atus.controller;

import br.com.atua.interfaces.Controller;
import br.com.atus.dao.OrgaoDAO;
import br.com.atus.modelo.Orgao;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.inject.Stereotype;

/**
 *
 * @author Ari
 */
@Stateless
public class OrgaoController extends Controller<Orgao, Integer> implements Serializable{

    @EJB
    private OrgaoDAO dao;
    
    @PostConstruct
    @Override
    protected void inicializaDAO() {
        setDAO(dao);
    }

    
    
    
}
