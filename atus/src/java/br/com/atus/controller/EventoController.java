/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.controller;

import br.com.atua.interfaces.Controller;
import br.com.atus.dao.EventoDAO;
import br.com.atus.modelo.Colaborador;
import br.com.atus.modelo.Evento;
import br.com.atus.modelo.Processo;
import br.com.atus.modelo.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author ari
 */
@Stateless
public class EventoController extends Controller<Evento, Long> implements Serializable {
    
    @EJB
    private EventoDAO dao;
    
    @PostConstruct
    @Override
    protected void inicializaDAO() {
        setDAO(dao);
    }
    
    public List<Evento> listarPorProcessos(Processo processo) {
        if (processo.getId() == null) {
            return new ArrayList<>();
        } else {
            return dao.listarPorProcesso(processo);
            
        }
    }
    
    public List<Evento> listarPorPeriodo(Date dataInicial, Date dataFinal) {
        return dao.consultaEventoPor(dataInicial, dataFinal);
    }
    
    public List<Evento> consultaEventoColaboradorPor(Colaborador colaborador, Date dataInicial, Date dataFinal) {
        List<Evento> listaEventos = new ArrayList<>();
        if (colaborador.getId() != null) {
            listaEventos = dao.consultaEventoColaboradorPor(colaborador, dataInicial, dataFinal);
        } else {
            listaEventos = dao.consultaEventoOrdenadoPorColaboradorPor(dataInicial, dataFinal);
        }
        Collections.sort(listaEventos);
        return listaEventos;
    }
    
    public List<Evento> consultaEventoColaboradorPor(Colaborador colaborador, Date dataInicial, Date dataFinal, boolean ehUsuarioDoEscritorio) {
        List<Evento> listaEventos = new ArrayList<>();
        if (ehUsuarioDoEscritorio) {
            if (colaborador.getId() != null) {
                listaEventos = dao.consultaEventoColaboradorPor(colaborador, dataInicial, dataFinal);
            } else {
                listaEventos = dao.consultaEventoOrdenadoPorColaboradorPor(dataInicial, dataFinal);
            }
        } else {
            listaEventos = dao.consultaEventoColaboradorPor(colaborador, dataInicial, dataFinal);
        }
        
        Collections.sort(listaEventos);
        return listaEventos;
    }
    
    public void adicionarEvento(Evento evento, Usuario usuarioLogado) throws Exception {
        
        if (evento.getId() == null) {
            if (evento.getObservacao().equals("") || evento.getObservacao() == null) {
                evento.setObservacao("---");
            }
            evento.setUsuario(usuarioLogado);
            dao.salvar(evento);
        } else {
            dao.atualizar(evento);
        }
    }
    
}
