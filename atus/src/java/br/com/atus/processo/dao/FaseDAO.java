/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.processo.dao;

import br.com.atus.util.dao.DAO;
import br.com.atus.modelo.Fase;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

/**
 *
 * @author ari
 */
@Stateless
public class FaseDAO extends DAO<Fase, Long> implements Serializable {

    public FaseDAO() {
        super(Fase.class);
    }

    public List<Fase> listarTodosOrderNome() {
        TypedQuery tq;
        tq = getEm().createQuery("SELECT f FROM Fase f ORDER BY f.nome", Fase.class);
        return tq.getResultList();
    }

}
