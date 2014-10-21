/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.controller;

import br.com.atus.dao.AgendaDAO;
import br.com.atus.modelo.Agenda;
import br.com.atus.modelo.Evento;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Ari
 */
@Stateless
public class AgendaController extends Controller<Agenda, Long> implements Serializable {

    @EJB
    private AgendaDAO dao;

    @PostConstruct
    @Override
    protected void inicializaDAO() {
        setDAO(dao);
    }

    public void addAgenda(Evento e) throws Exception {
        Agenda a ;
        a = dao.buscarPorEvento(e);
        a.setDataFim(e.getData());
        a.setDataInicio(e.getData());
        a.setDescricao(e.getObservacao());
        a.setEspecieEvento(e.getEspecieEvento());
        a.setEvento(e);
        a.setTitulo(e.getNome());
        dao.atualizar(a);        
    }
    
    public void delAgenda(Evento e) throws Exception{
        Agenda a ;
        a = dao.buscarPorEvento(e);
        dao.excluir(a);
    }

}
