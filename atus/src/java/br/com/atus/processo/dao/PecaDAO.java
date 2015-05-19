/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.processo.dao;

import br.com.atus.util.dao.DAO;
import br.com.atus.modelo.Peca;
import br.com.atus.modelo.SubGrupoPeca;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author gilmario
 */
@Stateless
public class PecaDAO extends DAO<Peca, Long> implements Serializable {

    public PecaDAO() {
        super(Peca.class);
    }

    public List<Peca> listar(SubGrupoPeca sgp) {
        return getEm().createQuery("SELECT p FROM Peca p WHERE p.subgrupo=:s ").setParameter("s", sgp).getResultList();
    }

}
