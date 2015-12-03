/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.financeiro.dao;

import br.com.atus.financeiro.modelo.CaixaColaborador;
import br.com.atus.cadastro.modelo.Colaborador;
import br.com.atus.util.dao.DAO;
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
public class CaixaColaboradorDAO extends DAO<CaixaColaborador, Long> implements Serializable{

    public CaixaColaboradorDAO() {
        super(CaixaColaborador.class);
    }
    
    public List<CaixaColaborador> consultaValoresAReceberDo(Colaborador col){
        TypedQuery<CaixaColaborador> tq;
        tq = getEm().createQuery("SELECT c FROM CaixaColaborador c WHERE c.colaborador = :col and c.dataDeRecebimento = NULL ORDER BY c.recibo", CaixaColaborador.class)
                .setParameter("col", col);
        
        return tq.getResultList().isEmpty() ? new ArrayList<CaixaColaborador>() : tq.getResultList();
    }
    
    public List<CaixaColaborador> consultaValoresPagosrDo(Colaborador col){
        TypedQuery<CaixaColaborador> tq;
        tq = getEm().createQuery("SELECT c FROM CaixaColaborador c WHERE c.colaborador = :col AND c.dataDeRecebimento <> NULL ORDER BY c.recibo", CaixaColaborador.class)
                .setParameter("col", col);
        
        return tq.getResultList().isEmpty() ? new ArrayList<CaixaColaborador>() : tq.getResultList();
    }
    
     public List<CaixaColaborador> consultaValoresAbertosOrdenadosPorProcessoDo(Colaborador col){
        TypedQuery<CaixaColaborador> tq;
        tq = getEm().createQuery("SELECT c FROM CaixaColaborador c WHERE c.colaborador = :col and c.dataDeRecebimento = NULL ORDER BY c.processo", CaixaColaborador.class)
                .setParameter("col", col);
        
        return tq.getResultList().isEmpty() ? new ArrayList<CaixaColaborador>() : tq.getResultList();
    }
    
    public List<CaixaColaborador> consultaValoresPagosrOrdenadoPorProcessoDo(Colaborador col){
        TypedQuery<CaixaColaborador> tq;
        tq = getEm().createQuery("SELECT c FROM CaixaColaborador c WHERE c.colaborador = :col AND c.dataDeRecebimento <> NULL ORDER BY c.processo", CaixaColaborador.class)
                .setParameter("col", col);
        
        return tq.getResultList().isEmpty() ? new ArrayList<CaixaColaborador>() : tq.getResultList();
    }
    
    
    public List<CaixaColaborador> consultaCaixasAbertos(){
        TypedQuery<CaixaColaborador> tq;
        tq = getEm().createQuery("SELECT c FROM CaixaColaborador c WHERE c.dataDeRecebimento = NULL ORDER BY c.colaborador,c.recibo", CaixaColaborador.class);
        
        return tq.getResultList().isEmpty() ? new ArrayList<CaixaColaborador>() : tq.getResultList();
    }
}
