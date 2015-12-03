/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.financeiro.dao;

import br.com.atus.financeiro.modelo.ContaReceber;
import br.com.atus.financeiro.modelo.ParcelasReceber;
import br.com.atus.cadastro.modelo.Advogado;
import br.com.atus.cadastro.modelo.Cliente;
import br.com.atus.cadastro.modelo.Colaborador;
import br.com.atus.processo.modelo.Processo;
import br.com.atus.util.MetodosUtilitarios;
import br.com.atus.util.dao.DAO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author ari
 */
@Stateless
public class ParcelaReceberDAO extends DAO<ParcelasReceber, Long> implements Serializable {

    public ParcelaReceberDAO() {
        super(ParcelasReceber.class);
    }

    public List<ParcelasReceber> consultaParcelasPagasDo(Processo pr) {
        TypedQuery tq;
        tq = getEm().createQuery("SELECT p from ParcelasReceber p WHERE p.contaReceber.processo = :pr AND p.dataPagamento IS NOT NULL and p.valorPago > 0 ORDER BY  p.vencimento", ParcelasReceber.class)
                .setParameter("pr", pr);

        return tq.getResultList().isEmpty() ? new ArrayList<ParcelasReceber>() : tq.getResultList();
    }

    public List<ParcelasReceber> consultaParcelasAbertasDo(ContaReceber cr) {
        TypedQuery tq;
        tq = getEm().createQuery("SELECT p from ParcelasReceber p WHERE p.contaReceber = :ctr AND p.dataPagamento IS NULL AND p.vencimento >= :dt ORDER BY  p.vencimento", ParcelasReceber.class)
                .setParameter("ctr", cr)
                .setParameter("dt", new Date());

        return tq.getResultList().isEmpty() ? new ArrayList<ParcelasReceber>() : tq.getResultList();
    }

    public List<ParcelasReceber> consultaParcelasAbertasDo(Cliente cliente) {
        TypedQuery tq;
        tq = getEm().createQuery("SELECT p from ParcelasReceber p WHERE p.contaReceber.processo.cliente = :cl AND p.dataPagamento IS NULL AND p.vencimento >= :dt  ORDER BY p.vencimento", ParcelasReceber.class)
                .setParameter("cl", cliente)
                .setParameter("dt", new Date());

        return tq.getResultList().isEmpty() ? new ArrayList<ParcelasReceber>() : tq.getResultList();
    }

    public List<ParcelasReceber> consultaParcelasAbertarDo(Advogado advogado) {
        TypedQuery tq;
        tq = getEm().createQuery("SELECT p from ParcelasReceber p WHERE p.contaReceber.advogado = :adv AND p.dataPagamento IS NULL AND p.vencimento >= :dt ORDER BY p.vencimento", ParcelasReceber.class)
                .setParameter("adv", advogado)
                .setParameter("dt", new Date());

        return tq.getResultList().isEmpty() ? new ArrayList<ParcelasReceber>() : tq.getResultList();
    }

    public List<ParcelasReceber> consultaParcelasAbertarDo(Colaborador colaborador) {
        TypedQuery tq;
        tq = getEm().createQuery("SELECT p from ParcelasReceber p WHERE p.contaReceber.colaborador = :col AND p.dataPagamento IS NULL AND p.vencimento >= :dt ORDER BY p.vencimento", ParcelasReceber.class)
                .setParameter("adv", colaborador)
                .setParameter("dt", new Date());

        return tq.getResultList().isEmpty() ? new ArrayList<ParcelasReceber>() : tq.getResultList();
    }

    public List<ParcelasReceber> consultaParcelasAbertarDo(Processo pr) {
        TypedQuery tq;
        tq = getEm().createQuery("SELECT p from ParcelasReceber p WHERE p.contaReceber.processo = :pr AND p.dataPagamento IS NULL AND p.vencimento >= :dt ORDER BY p.vencimento", ParcelasReceber.class)
                .setParameter("pr", pr)
                .setParameter("dt", new Date());

        return tq.getResultList().isEmpty() ? new ArrayList<ParcelasReceber>() : tq.getResultList();
    }

    public List<ParcelasReceber> consultaParcelasVencidasEntre(Date dtIni, Date dtFim) {
        TypedQuery tq;
        tq = getEm().createQuery("SELECT p from ParcelasReceber p WHERE p.dataPagamento IS NULL AND  p.vencimento BETWEEN :dtIni and :dtFim "
                + " ORDER BY p.contaReceber.processo.cliente,p.contaReceber.processo,p.vencimento", ParcelasReceber.class)
                .setParameter("dtIni", MetodosUtilitarios.processarDataInicial(dtIni))
                .setParameter("dtFim",MetodosUtilitarios.processarDataFinal(dtFim));

        return tq.getResultList().isEmpty() ? new ArrayList<ParcelasReceber>() : tq.getResultList();
    }

    public List<ParcelasReceber> consultaParcelasVencidasDo(ContaReceber cr) {
        TypedQuery tq;
        tq = getEm().createQuery("SELECT p from ParcelasReceber p WHERE p.contaReceber = :ctr AND p.dataPagamento IS NULL AND p.vencimento < :dt ORDER BY p.vencimento", ParcelasReceber.class)
                .setParameter("ctr", cr)
                .setParameter("dt", new Date());

        return tq.getResultList().isEmpty() ? new ArrayList<ParcelasReceber>() : tq.getResultList();
    }

    public List<ParcelasReceber> consultaParcelasVencidasDo(Processo pr) {
        TypedQuery tq;
        tq = getEm().createQuery("SELECT p from ParcelasReceber p WHERE p.contaReceber.processo = :pr AND p.dataPagamento IS NULL AND p.vencimento < :dt ORDER BY p.vencimento", ParcelasReceber.class)
                .setParameter("pr", pr)
                .setParameter("dt", new Date());

        return tq.getResultList().isEmpty() ? new ArrayList<ParcelasReceber>() : tq.getResultList();
    }

    public List<ParcelasReceber> consultaParcelasVencidasDo(Cliente cliente) {
        TypedQuery tq;
        tq = getEm().createQuery("SELECT p from ParcelasReceber p WHERE p.contaReceber.processo.cliente = :cl AND p.dataPagamento IS NULL AND p.vencimento < :dt  ORDER BY p.vencimento", ParcelasReceber.class)
                .setParameter("cl", cliente)
                .setParameter("dt", new Date());

        return tq.getResultList().isEmpty() ? new ArrayList<ParcelasReceber>() : tq.getResultList();
    }

    public List<ParcelasReceber> consultaParcelasVencidasDo(Advogado advogado) {
        TypedQuery tq;
        tq = getEm().createQuery("SELECT p from ParcelasReceber p WHERE p.contaReceber.advogado = :adv AND p.dataPagamento IS NULL AND p.vencimento < :dt ORDER BY p.vencimento", ParcelasReceber.class)
                .setParameter("adv", advogado)
                .setParameter("dt", new Date());

        return tq.getResultList().isEmpty() ? new ArrayList<ParcelasReceber>() : tq.getResultList();
    }

    public List<ParcelasReceber> consultaParcelasVencidasDo(Colaborador colaborador) {
        TypedQuery tq;
        tq = getEm().createQuery("SELECT p from ParcelasReceber p WHERE p.contaReceber.colaborador = :col AND p.dataPagamento IS NULL AND p.vencimento < :dt ORDER BY p.vencimento", ParcelasReceber.class)
                .setParameter("adv", colaborador)
                .setParameter("dt", new Date());

        return tq.getResultList().isEmpty() ? new ArrayList<ParcelasReceber>() : tq.getResultList();
    }

    public List<ParcelasReceber> consultaTodasParcelasDo(ContaReceber cr) {
        TypedQuery tq;
        tq = getEm().createQuery("SELECT p from ParcelasReceber p WHERE p.contaReceber = :ctr  ORDER BY p.vencimento", ParcelasReceber.class)
                .setParameter("ctr", cr);

        return tq.getResultList().isEmpty() ? new ArrayList<ParcelasReceber>() : tq.getResultList();
    }

    public int pegarNumeroDaUltimaParcelaDo(ContaReceber cr) {
        Query tq;
        tq = getEm().createQuery("SELECT MAX(p.numeroDaParcela) from ParcelasReceber p WHERE p.contaReceber = :ctr  ")
                .setParameter("ctr", cr);

        return (int) (tq.getResultList().isEmpty() ? new Integer(0).intValue() : tq.getSingleResult());
    }

}
