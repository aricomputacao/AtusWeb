/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.processo.dao;

import br.com.atus.util.dao.DAO;
import br.com.atus.enumerated.TipoAgenda;
import br.com.atus.cadastro.modelo.Colaborador;
import br.com.atus.cadastro.modelo.EspecieEvento;
import br.com.atus.processo.modelo.Evento;
import br.com.atus.processo.modelo.Processo;
import br.com.atus.util.MetodosUtilitarios;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

/**
 *
 * @author ari
 */
@Stateless
public class EventoDAO extends DAO<Evento, Long> implements Serializable {

    public EventoDAO() {
        super(Evento.class);
    }

    public List<Evento> listarPorProcesso(Processo processo) {
        TypedQuery<Evento> tq;
        tq = getEm().createQuery("SELECT e FROM Evento e WHERE e.processo = :pro ORDER BY e.data", Evento.class);
        tq.setParameter("pro", processo);
        if (tq.getResultList().isEmpty()) {
            return new ArrayList<>();
        } else {
            return tq.getResultList();
        }
    }

    public List<Evento> consultaEventoPor(Date dataInicial, Date dataFinal) {
        TypedQuery<Evento> tq;
        tq = getEm().createQuery("SELECT e FROM Evento e WHERE e.data BETWEEN :dtIni and :dtFim ORDER BY e.data,e.usuario", Evento.class);
        tq.setParameter("dtIni", MetodosUtilitarios.processarDataInicial(dataInicial));
        tq.setParameter("dtFim", MetodosUtilitarios.processarDataFinal(dataFinal));
        if (tq.getResultList().isEmpty()) {
            return new ArrayList<>();
        } else {
            return tq.getResultList();
        }
    }

    public List<Evento> consultaEventoOrdenadoPorColaboradorPor(Date dataInicial, Date dataFinal) {
        TypedQuery<Evento> tq;
        tq = getEm().createQuery("SELECT e FROM Evento e WHERE e.data BETWEEN :dtIni and :dtFim ORDER BY e.processo.colaborador", Evento.class);
        tq.setParameter("dtIni", MetodosUtilitarios.processarDataInicial(dataInicial));
        tq.setParameter("dtFim", MetodosUtilitarios.processarDataFinal(dataFinal));
        if (tq.getResultList().isEmpty()) {
            return new ArrayList<>();
        } else {
            return tq.getResultList();
        }
    }

    public List<Evento> consultaEventoOrdenadoPorColaboradorPor(Date dataInicial, Date dataFinal, List<EspecieEvento> especieEventos) {
        TypedQuery<Evento> tq;
        tq = getEm().createQuery("SELECT e FROM Evento e WHERE e.especieEvento in (:espEvs) and e.data BETWEEN :dtIni and :dtFim ORDER BY e.processo.colaborador", Evento.class);
        tq.setParameter("dtIni", MetodosUtilitarios.processarDataInicial(dataInicial));
        tq.setParameter("dtFim", MetodosUtilitarios.processarDataFinal(dataFinal));
        tq.setParameter("espEvs", especieEventos);

        if (tq.getResultList().isEmpty()) {
            return new ArrayList<>();
        } else {
            return tq.getResultList();
        }
    }

    public List<Evento> consultaEventoColaboradorPor(Colaborador colaborador, Date dtIni, Date dtFim) {
        TypedQuery<Evento> tq;
        tq = getEm().createQuery("SELECT e FROM Evento e WHERE e.processo.colaborador = :col and e.data BETWEEN :dtIni and :dtFim ORDER BY e.processo.colaborador", Evento.class);
        tq.setParameter("dtIni", MetodosUtilitarios.processarDataInicial(dtIni));
        tq.setParameter("dtFim", MetodosUtilitarios.processarDataFinal(dtFim));
        tq.setParameter("col", colaborador);

        if (tq.getResultList().isEmpty()) {
            return new ArrayList<>();
        } else {
            return tq.getResultList();
        }
    }

    public List<Evento> consultaEventoColaboradorPor(Colaborador colaborador, Date dtIni, Date dtFim, List<EspecieEvento> especieEventos) {
        TypedQuery<Evento> tq;
        tq = getEm().createQuery("SELECT e FROM Evento e WHERE e.especieEvento in (:espEvs) and  e.processo.colaborador = :col and e.data BETWEEN :dtIni and :dtFim ORDER BY e.processo.colaborador", Evento.class);
        tq.setParameter("dtIni", MetodosUtilitarios.processarDataInicial(dtIni));
        tq.setParameter("dtFim", MetodosUtilitarios.processarDataFinal(dtFim));
        tq.setParameter("col", colaborador);
        tq.setParameter("espEvs", especieEventos);

        if (tq.getResultList().isEmpty()) {
            return new ArrayList<>();
        } else {
            return tq.getResultList();
        }
    }

}
