/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.dao;

import br.com.atus.modelo.Permissao;
import br.com.atus.modelo.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

/**
 *
 * @author gilmario
 */
@Stateless
public class PermissaoDAO extends DAO<Permissao, Long> implements Serializable {

    public PermissaoDAO() {
        super(Permissao.class);
    }

    public List<Permissao> listar(Usuario usuario) {
        TypedQuery<Permissao> tq;
        tq = getEm().createQuery("SELECT p FROM Permissao p WHERE p.usuario = :usr", Permissao.class)
                .setParameter("usr",usuario);
        if (tq.getResultList().isEmpty()) {
            return new ArrayList<>();
        } else {
            return tq.getResultList();
        }
    }

}
