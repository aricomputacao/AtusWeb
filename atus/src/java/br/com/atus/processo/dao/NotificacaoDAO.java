/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.atus.processo.dao;

import br.com.atus.util.dao.DAO;
import br.com.atus.modelo.Notificacao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

/**
 *
 * @author Ari
 */
@Stateless
public class NotificacaoDAO extends DAO<Notificacao, Long> implements Serializable{

    public NotificacaoDAO() {
        super(Notificacao.class);
    }
    
    public List<Notificacao> listarNotificacaoAtiva(){
        TypedQuery<Notificacao> q;
        q = getEm().createQuery("SELECT n FROM Notificacao n WHERE n.ativo = FALSE ORDER BY n.data", Notificacao.class);
        if (q.getResultList().isEmpty()) {
            return new ArrayList<>();
        } else {
            return q.getResultList();
        }
    }
    
}
