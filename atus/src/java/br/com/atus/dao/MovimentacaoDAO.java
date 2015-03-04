/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.dao;

import br.com.atus.dto.ProcessoUltimaMovimentacaoDTO;
import br.com.atus.modelo.Colaborador;
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
public class MovimentacaoDAO extends DAO<Movimentacao, Long> implements Serializable {

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

    public List<ProcessoUltimaMovimentacaoDTO> listarProcessoUltimaMovimentacao() {
        TypedQuery q;
        q = getEm().createQuery("SELECT new br.com.atus.dto.ProcessoUltimaMovimentacaoDTO(m.processo,MAX(m))  FROM Movimentacao m GROUP BY m.processo,m.processo.colaborador ORDER BY m.processo.colaborador  ", ProcessoUltimaMovimentacaoDTO.class);
        return q.getResultList().isEmpty() ? new ArrayList<>() : q.getResultList();
    }
    
    public List<ProcessoUltimaMovimentacaoDTO> listarProcessoUltimaMovimentacaoOrdenadoDataMovimentacao() {
        TypedQuery q;
        q = getEm().createQuery("SELECT new br.com.atus.dto.ProcessoUltimaMovimentacaoDTO(m.processo,MAX(m))  FROM Movimentacao m GROUP BY m.processo,m.processo.colaborador,m.dataMovimentacao ORDER BY m.processo.colaborador, m.dataMovimentacao DESC ", ProcessoUltimaMovimentacaoDTO.class);
        return q.getResultList().isEmpty() ? new ArrayList<>() : q.getResultList();
    }
    
    public List<ProcessoUltimaMovimentacaoDTO> listarProcessoUltimaMovimentacaoOrdenadoDataMovimentacao(Colaborador c) {
        TypedQuery q;
        q = getEm().createQuery("SELECT new br.com.atus.dto.ProcessoUltimaMovimentacaoDTO(m.processo,MAX(m))  FROM Movimentacao m WHERE m.processo.colaborador = :col GROUP BY m.processo,m.processo.colaborador,m.dataMovimentacao ORDER BY m.processo.colaborador,m.dataMovimentacao ASC ", ProcessoUltimaMovimentacaoDTO.class)
                .setParameter("col", c);

        return q.getResultList().isEmpty() ? new ArrayList<>() : q.getResultList();
    }

    public List<ProcessoUltimaMovimentacaoDTO> listarProcessoUltimaMovimentacao(Colaborador c) {
        TypedQuery q;
        q = getEm().createQuery("SELECT new br.com.atus.dto.ProcessoUltimaMovimentacaoDTO(m.processo,MAX(m))  FROM Movimentacao m WHERE m.processo.colaborador = :col GROUP BY m.processo,m.processo.colaborador ORDER BY m.processo.colaborador ", ProcessoUltimaMovimentacaoDTO.class)
                .setParameter("col", c);

        return q.getResultList().isEmpty() ? new ArrayList<>() : q.getResultList();
    }

}
