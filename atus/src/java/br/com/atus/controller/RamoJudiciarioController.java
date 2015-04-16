/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.atus.controller;

import br.com.atua.interfaces.Controller;
import br.com.atus.dao.RamoJudiciarioDAO;
import br.com.atus.modelo.RamoJudiciario;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Ari
 */
@Stateless
public class RamoJudiciarioController extends Controller<RamoJudiciario, Integer> implements Serializable{

    @EJB
    private RamoJudiciarioDAO dao;
    
    @PostConstruct
    @Override
    protected void inicializaDAO() {
        setDAO(dao);
    }
    
}
