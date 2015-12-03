/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.processo.dao;

import br.com.atus.util.dao.DAO;
import br.com.atus.dto.ProcessoUltimaMovimentacaoDTO;
import br.com.atus.cadastro.modelo.Colaborador;
import br.com.atus.processo.modelo.Fase;
import br.com.atus.processo.modelo.Movimentacao;
import br.com.atus.processo.modelo.Processo;
import br.com.atus.cadastro.modelo.Usuario;
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

    public ProcessoUltimaMovimentacaoDTO ultimaMovimentacaoDTO(Processo p, Usuario u) {
        TypedQuery q;
        q = getEm().createQuery("SELECT new br.com.atus.dto.ProcessoUltimaMovimentacaoDTO(m.processo,MAX(m))  FROM Movimentacao m WHERE m.processo.fase.usuario = :usr and m.processo = :pro GROUP BY m.processo", ProcessoUltimaMovimentacaoDTO.class)
                .setParameter("usr", u)
                .setParameter("pro", p);

        return q.getResultList().isEmpty() ? new ProcessoUltimaMovimentacaoDTO(p, null) : (ProcessoUltimaMovimentacaoDTO) q.getSingleResult();

    }

    public ProcessoUltimaMovimentacaoDTO ultimaMovimentacaoDTO(Processo p) {
        TypedQuery q;
        q = getEm().createQuery("SELECT new br.com.atus.dto.ProcessoUltimaMovimentacaoDTO(m.processo,MAX(m))  FROM Movimentacao m WHERE m.processo = :pro GROUP BY m.processo ", ProcessoUltimaMovimentacaoDTO.class)
                .setParameter("pro", p);

        return q.getResultList().isEmpty() ? new ProcessoUltimaMovimentacaoDTO(p, null) : (ProcessoUltimaMovimentacaoDTO) q.getSingleResult();
    }

    public List<ProcessoUltimaMovimentacaoDTO> listarProcessoUltimaMovimentacao(Colaborador c) {
        TypedQuery q;
        q = getEm().createQuery("SELECT new br.com.atus.dto.ProcessoUltimaMovimentacaoDTO(m.processo,MAX(m))  FROM Movimentacao m WHERE m.processo.colaborador = :col GROUP BY m.processo,m.processo.colaborador ORDER BY m.processo.colaborador ", ProcessoUltimaMovimentacaoDTO.class)
                .setParameter("col", c);

        return q.getResultList().isEmpty() ? new ArrayList<>() : q.getResultList();
    }

    public List<Movimentacao> consultaMovimentacaoPor(List<Fase> list, Date dtIni, Date dtFim) {
        TypedQuery q;
        q = getEm().createQuery("SELECT m  FROM Movimentacao m WHERE m.dataMovimentacao BETWEEN :dtIni and :dtFim and m.faseAntiga IN (:fas)  ORDER BY m.faseAntiga,m.usuario,m.dataMovimentacao ", Movimentacao.class)
                .setParameter("fas", list)
                .setParameter("dtIni", MetodosUtilitarios.processarDataInicial(dtIni))
                .setParameter("dtFim", MetodosUtilitarios.processarDataFinal(dtFim));

        return q.getResultList().isEmpty() ? new ArrayList<>() : q.getResultList();
    }

    public List<Movimentacao> consultarMovimentacaoPorUsarios(List<Usuario> list, Date dtIni, Date dtFim) {
           TypedQuery q;
        q = getEm().createQuery("SELECT m  FROM Movimentacao m WHERE m.dataMovimentacao BETWEEN :dtIni and :dtFim and m.usuario IN (:usrs)  ORDER BY m.usuario,m.faseAntiga,m.dataMovimentacao ", Movimentacao.class)
                .setParameter("usrs", list)
                .setParameter("dtIni", MetodosUtilitarios.processarDataInicial(dtIni))
                .setParameter("dtFim", MetodosUtilitarios.processarDataFinal(dtFim));

        return q.getResultList().isEmpty() ? new ArrayList<>() : q.getResultList();
    }

}
