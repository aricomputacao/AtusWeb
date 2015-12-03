/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.atus.cadastro.dao;

import br.com.atus.util.dao.DAO;
import br.com.atus.cadastro.modelo.Cliente;
import java.io.Serializable;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

/**
 *
 * @author ari
 */
@Stateless 
public class ClienteDAO extends DAO<Cliente, Long> implements Serializable{

    public ClienteDAO() {
        super(Cliente.class);
    }

    public Cliente buscarPorDocumento(String doc) {
        TypedQuery q;
        q = getEm().createQuery("SELECT c from Cliente c WHERE c.cpfCpnj = :doc", Cliente.class);
        q.setParameter("doc", doc);
        if (q.getResultList().isEmpty()) {
            return new Cliente();
        } else {
            return (Cliente) q.getSingleResult();
        }
    }
    
    
    
}
