/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.atus.controller;

import br.com.atua.interfaces.Controller;
import br.com.atus.dao.NotificacaoDAO;
import br.com.atus.modelo.Notificacao;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Ari
 */
@Stateless
public class NotificacaoController extends Controller<Notificacao, Long> implements Serializable{

    @EJB
    private NotificacaoDAO dao;
    
    @PostConstruct
    @Override
    protected void inicializaDAO() {
        setDAO(dao);
    }
    
    public List<Notificacao> listaNotificacaoAtiva(){
        return dao.listarNotificacaoAtiva();
    }
    
}
