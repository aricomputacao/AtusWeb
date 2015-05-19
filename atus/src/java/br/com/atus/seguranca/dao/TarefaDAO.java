/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.seguranca.dao;

import br.com.atus.util.dao.DAO;
import br.com.atus.modelo.Modulo;
import br.com.atus.modelo.Tarefa;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author gilmario
 */
@Stateless
public class TarefaDAO extends DAO<Tarefa, Long> implements Serializable {

    public TarefaDAO() {
        super(Tarefa.class);
    }

    public List<Tarefa> listar(Modulo modulo) {
        return getEm().createQuery("SELECT t FROM Tarefa t WHERE t.modulo =:m ").setParameter("m", modulo).getResultList();
    }

    public boolean existeTarefa(Tarefa taf) {
        Query q;
        q = getEm().createQuery("SELECT t FROM Tarefa t WHERE t.modulo = :mod and t.nome = :nome")
                .setParameter("mod", taf.getModulo())
                .setParameter("nome", taf.getNome());
        return  !q.getResultList().isEmpty();
    }

}
