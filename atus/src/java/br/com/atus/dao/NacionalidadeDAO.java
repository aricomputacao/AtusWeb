/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.atus.dao;

import br.com.atus.modelo.Nacionalidade;
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
