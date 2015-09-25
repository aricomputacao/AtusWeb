/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.cadastro.dao;

import br.com.atus.util.dao.DAO;
import br.com.atus.processo.modelo.GrupoPeca;
import br.com.atus.processo.modelo.SubGrupoPeca;
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
public class SubGrupoPecaDAO extends DAO<SubGrupoPeca, Integer> implements Serializable {

    public SubGrupoPecaDAO() {
        super(SubGrupoPeca.class);
    }

    public List<SubGrupoPeca> listar(GrupoPeca grupo) {
        return getEm().createQuery("SELECT s FROM SubGrupoPeca s WHERE s.grupoPeca = :g").setParameter("g", grupo).getResultList();
    }

    public List<SubGrupoPeca> consultarLikeNomeGrupo(String valorBusca) {
        TypedQuery<SubGrupoPeca> tq;
        tq = getEm().createQuery("SELECT s from SubGrupoPeca s WHERE UPPER(s.grupoPeca.nome) LIKE :grp", SubGrupoPeca.class)
                .setParameter("grp", "%"+valorBusca.toUpperCase()+"%");
        
        return tq.getResultList().isEmpty() ? new ArrayList<SubGrupoPeca>() : tq.getResultList();
    }

}
