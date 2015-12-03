/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.atus.cadastro.controller;

import br.com.atus.interfaces.Controller;
import br.com.atus.cadastro.dao.ColaboradorDAO;
import br.com.atus.cadastro.modelo.Colaborador;
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
public class ColaboradorController extends Controller<Colaborador, Long> implements Serializable{

    @EJB
    private ColaboradorDAO dao;
    
    @PostConstruct
    @Override
    protected void inicializaDAO() {
        setDAO(dao);
    }
    
    public List<Colaborador> listaOrdenadoPorNome(){
        return dao.listaOrdenadoPorNome();
    }
    
}
