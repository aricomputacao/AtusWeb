/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.atus.controller;

import br.com.atus.dao.AdversarioDAO;
import br.com.atus.modelo.Adversario;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author ari
 */
@Stateless
public class AdversarioController extends Controller<Adversario, Long> implements Serializable{

    @EJB
    private AdversarioDAO dao;
    
    @Override
    protected void inicializaDAO() {
        setDAO(dao);
    }
    
}
