/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.dao;

import br.com.atus.modelo.GrupoPeca;
import br.com.atus.modelo.SubGrupoPeca;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author gilmario
 */
@Stateless
public class SubGrupoPecaDAO extends DAO<SubGrupoPeca, Integer> implements Serializable {

    public SubGrupoPecaDAO() {
        super(SubGrupoPeca.class);
    }

    public List<SubGrupoPeca> listar(GrupoPeca grupo) {
        return getEm().createQuery("SELECT s FROM SubGrupoPeca s WHERE s.grupoPeca = :g").setParameter("g", grupo).getResultList();
    }

}
