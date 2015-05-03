/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.atus.controller;

import br.com.atua.interfaces.Controller;
import br.com.atus.dao.AtendimentoDAO;
import br.com.atus.modelo.Atendimento;
import br.com.atus.modelo.Usuario;
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
public class AtendimentoController extends Controller<Atendimento, Long> implements Serializable{

    @EJB
    private AtendimentoDAO dao;
    
    @PostConstruct
    @Override
    protected void inicializaDAO() {
        setDAO(dao);
    }

    public List<Atendimento> listarAtendFrente() {
        return dao.listarAtendFrente();
    }

    public List<Atendimento> listarAtendFundo(Usuario usuarioLogado) {
       return dao.listarAtendFundo(usuarioLogado);
    }

    public void apagarTodosAtendimentos()  {
       dao.apagarTodosAtendimentos();
    }

   
    
}
