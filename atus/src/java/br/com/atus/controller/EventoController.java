/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.controller;

import br.com.atus.dao.EventoDAO;
import br.com.atus.modelo.Evento;
import br.com.atus.modelo.Processo;
import java.io.Serializable;
import java.util.ArrayList;
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
        return dao.listarPorPeriodo(dataInicial, dataFinal);
    }

}
