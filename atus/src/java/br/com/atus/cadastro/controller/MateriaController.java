/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.atus.cadastro.controller;

import br.com.atus.interfaces.Controller;
import br.com.atus.cadastro.dao.MateriaDAO;
import br.com.atus.modelo.Materia;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Ari
 */
@Stateless
public class MateriaController extends Controller<Materia, Integer> implements Serializable{

    @EJB
    private MateriaDAO dao;
    
    @PostConstruct
    @Override
    protected void inicializaDAO() {
        setDAO(dao);
    }
    
}
