/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.atus.cadastro.dao;

import br.com.atus.util.dao.DAO;
import br.com.atus.cadastro.modelo.Nacionalidade;
import java.io.Serializable;
import javax.ejb.Stateless;
import javax.enterprise.inject.Stereotype;

/**
 *
 * @author ari
 */
@Stateless
public class NacionalidadeDAO extends DAO<Nacionalidade, Integer> implements Serializable{

    public NacionalidadeDAO() {
        super(Nacionalidade.class);
    }
    
}
