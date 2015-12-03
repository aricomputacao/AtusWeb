/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.processo.dao;

import br.com.atus.processo.modelo.NotificacaoProcesso;
import br.com.atus.processo.modelo.Processo;
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
public class NotificacaoProcessoDAO extends DAO<NotificacaoProcesso, Long> implements Serializable{

    public NotificacaoProcessoDAO() {
        super(NotificacaoProcesso.class);
    }

    public List<NotificacaoProcesso> consultarPor(Processo processo) {
        TypedQuery<NotificacaoProcesso> tq;
        tq = getEm().createQuery("SELECT n from NotificacaoProcesso n WHERE n.processo = :pro ORDER BY n.id", NotificacaoProcesso.class)
                .setParameter("pro", processo);
        
        return tq.getResultList().isEmpty() ? new ArrayList<NotificacaoProcesso>() : tq.getResultList();
    }

    public NotificacaoProcesso consultarPor(Long processo) {
        TypedQuery<NotificacaoProcesso> tq;
        tq = getEm().createQuery("SELECT n from NotificacaoProcesso n WHERE n.processo.id = :pro ORDER BY n.id", NotificacaoProcesso.class)
                .setParameter("pro", processo);
        
        return tq.getResultList().isEmpty() ? new NotificacaoProcesso() : tq.getSingleResult();
    }
    
}
