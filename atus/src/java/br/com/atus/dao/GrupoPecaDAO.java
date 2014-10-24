/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.dao;

import br.com.atus.modelo.GrupoPeca;
import java.io.Serializable;
import javax.ejb.Stateless;

/**
 *
 * @author gilmario
 */
@Stateless
public class GrupoPecaDAO extends DAO<GrupoPeca, Integer> implements Serializable {

    public GrupoPecaDAO() {
        super(GrupoPeca.class);
    }

}
