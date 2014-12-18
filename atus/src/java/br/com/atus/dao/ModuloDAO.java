/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.dao;

import br.com.atus.modelo.Modulo;
import java.io.Serializable;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

/**
 *
 * @author gilmario
 */
@Stateless
public class ModuloDAO extends DAO<Modulo, Long> implements Serializable {

    public ModuloDAO() {
        super(Modulo.class);
    }

    public boolean existeModulo(String nome){
        TypedQuery q;
        q = getEm().createQuery("SELECT m from Modulo m WHERE m.nome = :nome", Modulo.class)
                .setParameter("nome", nome);
        return !q.getResultList().isEmpty();
    }    
    
    public Modulo moduloMnemonico(String mine){
        TypedQuery q;
        q = getEm().createQuery("SELECT m from Modulo m WHERE m.mnemonico = :mine", Modulo.class)
                .setParameter("mine", mine);
        if (q.getResultList().isEmpty()) {
            return new Modulo();
        } else {
            return (Modulo) q.getSingleResult();
        }
    }
}
