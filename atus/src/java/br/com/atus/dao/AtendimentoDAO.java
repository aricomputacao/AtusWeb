/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.atus.dao;

import br.com.atus.modelo.Atendimento;
import br.com.atus.modelo.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

/**
 *
 * @author ari
 */
@Stateless
public class AtendimentoDAO extends DAO<Atendimento,Long> implements Serializable{

    public AtendimentoDAO() {
        super(Atendimento.class);
    }

    public List<Atendimento> listarAtendFrente() {
        TypedQuery<Atendimento> q;
        q = getEm().createQuery("SELECT a FROM Atendimento a WHERE a.dataSaida IS NULL ORDER BY a.id",Atendimento.class);
        if (q.getResultList().isEmpty()) {
            return new ArrayList<>();
        } else {
            return q.getResultList();
        }
    }

    public List<Atendimento> listarAtendFundo(Usuario usuarioLogado) {
       TypedQuery<Atendimento> q;
        q = getEm().createQuery("SELECT a FROM Atendimento a WHERE a.dataSaida IS NULL AND a.usuarioAtendimento = :usr ORDER BY a.id",Atendimento.class);
        q.setParameter("usr", usuarioLogado);
        if (q.getResultList().isEmpty()) {
            return new ArrayList<>();
        } else {
            return q.getResultList();
        }
    }
    
}
