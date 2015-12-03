/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.seguranca.controller;

import br.com.atus.interfaces.Controller;
import br.com.atus.seguranca.dao.ModuloDAO;
import br.com.atus.seguranca.modelo.Modulo;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author gilmario
 */
@Stateless
public class ModuloController extends Controller<Modulo, Long> implements Serializable {
    
    @EJB
    private ModuloDAO dao;
    
    public ModuloController() {
    }
    
    @Override
    @PostConstruct
    protected void inicializaDAO() {
        setDAO(this.dao);
    }
    
    public boolean existeModulo(String nome) throws Exception {
      return dao.existeModulo(nome);
    }
    
    public Modulo buscarUnique(String mine) throws Exception{
        return dao.moduloMnemonico(mine);
    }
    
}
