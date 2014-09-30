/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.atus.controller;

import br.com.atus.dao.ObjetoProcessoDAO;
import br.com.atus.modelo.ObjetoProcesso;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author ari
 */
@Stateless
public class ObjetoProcessoController extends Controller<ObjetoProcesso, Long> implements Serializable{

    @EJB
    private ObjetoProcessoDAO dao;
    
    @Override
    protected void inicializaDAO() {
        setDAO(dao);
    }
    
}
