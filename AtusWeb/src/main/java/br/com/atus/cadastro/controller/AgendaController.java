/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.cadastro.controller;

import br.com.atus.interfaces.Controller;
import br.com.atus.cadastro.dao.AgendaDAO;
import br.com.atus.cadastro.modelo.Agenda;
import br.com.atus.processo.modelo.Evento;
import br.com.atus.cadastro.modelo.Usuario;
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
        a.setUsuario(e.getUsuario());
        a.setTitulo(e.getNome());
        dao.atualizar(a);        
    }
    
    public void delAgenda(Evento e) throws Exception{
        Agenda a ;
        a = dao.buscarPorEvento(e);
        dao.excluir(a);
    }

    public List<Agenda> listarPorUsuario(Usuario usuarioLogado) {
      return dao.listarPorUsuario(usuarioLogado);
    }

}
