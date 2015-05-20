/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.financeiro.dao;

import br.com.atus.financeiro.modelo.ContaReceber;
import br.com.atus.modelo.Cliente;
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
public class ContaReceberDAO extends DAO<ContaReceber, Long> implements Serializable{

    public ContaReceberDAO() {
        super(ContaReceber.class);
    }

    public List<ContaReceber> consultarContasReceberAbertasDo(String cliente) {
        TypedQuery tq;
        tq = getEm().createQuery("SELECT DISTINCT c.contaReceber FROM ParcelasReceber c WHERE UPPER(c.contaReceber.processo.cliente.pessoa.nome) like :cl   ORDER BY c.contaReceber.dataCadastro", ContaReceber.class)
                .setParameter("cl", "%"+cliente.toUpperCase()+"%");
        return tq.getResultList().isEmpty() ? new ArrayList<ContaReceber>() : tq.getResultList();
    }
    
    public List<ContaReceber> consultarTodasContasReceberDo(String cliente) {
        TypedQuery tq;
        tq = getEm().createQuery("SELECT c FROM ContaReceber c WHERE UPPER(c.processo.cliente.pessoa.nome) like :cl   ORDER BY c.dataCadastro", ContaReceber.class)
                .setParameter("cl", "%"+cliente.toUpperCase()+"%");
        return tq.getResultList().isEmpty() ? new ArrayList<ContaReceber>() : tq.getResultList();
    }
}
