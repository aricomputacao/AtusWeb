/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.atus.dao;

import br.com.atus.modelo.Agenda;
import br.com.atus.modelo.Evento;
import java.io.Serializable;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

/**
 *
 * @author Ari
 */
@Stateless
public class AgendaDAO extends DAO<Agenda, Long> implements Serializable{

    public AgendaDAO() {
        super(Agenda.class);
    }

    public Agenda buscarPorEvento(Evento evento) {
        TypedQuery tq;
        tq = getEm().createQuery("SELECT a FROM Agenda a WHERE a.evento = :ev", Agenda.class);
        tq.setParameter("ev", evento);
        if (tq.getResultList().isEmpty()) {
            return new Agenda();
        } else {
            return (Agenda) tq.getSingleResult();
        }
    }
    
}
