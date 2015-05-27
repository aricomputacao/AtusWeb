/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.financeiro.dao;

import br.com.atus.financeiro.modelo.Recibo;
import br.com.atus.modelo.Advogado;
import br.com.atus.util.MetodosUtilitarios;
import br.com.atus.util.dao.DAO;
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
public class ReciboDAO extends DAO<Recibo, Long> implements Serializable{

    public ReciboDAO() {
        super(Recibo.class);
    }

    public List<Recibo> consultarRecibosAbertos(Advogado advogado) {
        TypedQuery<Recibo> tq;
        tq = getEm().createQuery("SELECT r FROM Recibo r WHERE r.advogadoQueRecebeu = :adv "
                + "and r.confirmacaoRecebimento = false ORDER BY r.id", Recibo.class);
        tq.setParameter("adv", advogado);
        
        return tq.getResultList().isEmpty() ? new ArrayList<Recibo>() : tq.getResultList();
    }
    
    public List<Recibo> consultarRecibosParaPrestarContas(Advogado advogado,Date dtIni,Date dtFim) {
        TypedQuery<Recibo> tq;
        tq = getEm().createQuery("SELECT r FROM Recibo r WHERE r.advogadoQueRecebeu = :ad "
                + "and r.prestadoConta = false and r.dataPagamento BETWEEN :dtIni AND :dtFim "
                + "and r.confirmacaoRecebimento = false ORDER BY r.id", Recibo.class);
        tq.setParameter("adv", advogado);
        tq.setParameter("dtIni", MetodosUtilitarios.processarDataInicial(dtIni));
        tq.setParameter("dtFim", MetodosUtilitarios.processarDataFinal(dtFim));
        return tq.getResultList().isEmpty() ? new ArrayList<Recibo>() : tq.getResultList();
    }
    
}
