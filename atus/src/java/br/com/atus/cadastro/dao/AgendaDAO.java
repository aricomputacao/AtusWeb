/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.cadastro.dao;

import br.com.atus.util.dao.DAO;
import br.com.atus.cadastro.modelo.Agenda;
import br.com.atus.processo.modelo.Evento;
import br.com.atus.cadastro.modelo.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

/**
 *
 * @author Ari
 */
@Stateless
public class AgendaDAO extends DAO<Agenda, Long> implements Serializable {

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

    public List<Agenda> listarPorUsuario(Usuario usuarioLogado) {
        TypedQuery tq;
        tq = getEm().createQuery("SELECT a FROM Agenda a WHERE a.usuario = :usr and a.especieEvento.nome <> :tp", Agenda.class);
        tq.setParameter("usr", usuarioLogado);
        tq.setParameter("tp", "Publicação");

        if (tq.getResultList().isEmpty()) {
            return new ArrayList<>();
        } else {
            return tq.getResultList();
        }
    }

}
