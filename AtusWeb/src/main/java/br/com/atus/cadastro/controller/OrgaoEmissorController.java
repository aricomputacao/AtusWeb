/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.atus.cadastro.controller;

import br.com.atus.interfaces.Controller;
import br.com.atus.cadastro.dao.OrgaoDAO;
import br.com.atus.cadastro.dao.OrgaoEmissorDAO;
import br.com.atus.cadastro.modelo.OrgaoEmissor;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author ari
 */
@Stateless
public class OrgaoEmissorController extends Controller<OrgaoEmissor, Integer> implements Serializable{

    @EJB
    private OrgaoEmissorDAO dao;
    
    @PostConstruct
    @Override
    protected void inicializaDAO() {
        setDAO(dao);
    }
    
}
