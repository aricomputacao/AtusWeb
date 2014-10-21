/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.dao;

import br.com.atus.modelo.Evento;
import br.com.atus.modelo.Processo;
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

    public List<Evento> listarPorPeriodo(Date dataInicial, Date dataFinal) {
        TypedQuery<Evento> tq;
        tq = getEm().createQuery("SELECT e FROM Evento e WHERE e.data BETWEEN :dtIni and :dtFim ORDER BY e.data", Evento.class);
        tq.setParameter("dtIni", dataInicial);
        tq.setParameter("dtFim", dataFinal);
        if (tq.getResultList().isEmpty()) {
            return new ArrayList<>();
        } else {
            return tq.getResultList();
        }
    }

}
