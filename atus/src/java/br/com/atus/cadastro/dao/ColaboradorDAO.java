/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.atus.cadastro.dao;

import br.com.atus.util.dao.DAO;
import br.com.atus.modelo.Colaborador;
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
public class ColaboradorDAO extends DAO<Colaborador, Long> implements Serializable{

    public ColaboradorDAO() {
        super(Colaborador.class);
    }
    
    public List<Colaborador> listaOrdenadoPorNome(){
        TypedQuery<Colaborador> tq;
        tq = getEm().createQuery("SELECT c from Colaborador c ORDER BY c.pessoa.nome", Colaborador.class);
        return tq.getResultList().isEmpty() ? new ArrayList<Colaborador>() : tq.getResultList();
    }
    
}
