/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.atus.controller;

import br.com.atus.dao.ProcessoDAO;
import br.com.atus.modelo.Processo;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.ejb.Startup;
import javax.ejb.Stateless;

/**
 *
 * @author ari
 */
@Stateless
public class ProcessoController extends Controller<Processo, Long> implements Serializable{

    @EJB
    private ProcessoDAO dao;
    
    @Override
    protected void inicializaDAO() {
        setDAO(dao);
    }
    
}
