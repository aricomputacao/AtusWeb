/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.atus.dao;

import br.com.atus.modelo.Movimentacao;
import br.com.atus.modelo.Processo;
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
public class MovimentacaoDAO extends DAO<Movimentacao, Long> implements Serializable{

    public MovimentacaoDAO() {
        super(Movimentacao.class);
    }

    public List<Movimentacao> listarPorProcesso(Processo processo) {
        TypedQuery tq;
        tq = getEm().createQuery("SELECT m FROM Movimentacao m  WHERE m.processo = :pro ORDER BY m.dataMovimentacao DESC", Movimentacao.class);
        tq.setParameter("pro", processo);
        if (tq.getResultList().isEmpty()) {
            return new ArrayList<>();
        } else {
            return tq.getResultList();
        }
    }

    public Long ultimaMovimentacao(Processo p) {
        TypedQuery tq;
        tq = getEm().createQuery("SELECT MAX(m.id) FROM Movimentacao m  WHERE m.processo = :pro ", Long.class);
        tq.setParameter("pro", p);
        return (Long) tq.getSingleResult();
    }
    
}
