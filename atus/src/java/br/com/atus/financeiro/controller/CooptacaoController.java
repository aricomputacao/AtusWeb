/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.financeiro.controller;

import br.com.atus.interfaces.Controller;
import br.com.atus.financeiro.dao.CooptacaoDAO;
import br.com.atus.financeiro.modelo.Cooptacao;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Ari
 */
@Stateless
public class CooptacaoController extends Controller<Cooptacao, Long> implements Serializable{

    @EJB
    private CooptacaoDAO dao;
    
    @PostConstruct
    @Override
    protected void inicializaDAO() {
        setDAO(dao);
    }
    
}
