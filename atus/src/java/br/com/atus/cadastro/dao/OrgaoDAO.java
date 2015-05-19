/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.atus.cadastro.dao;

import br.com.atus.util.dao.DAO;
import br.com.atus.modelo.Orgao;
import java.io.Serializable;
import javax.ejb.Stateless;

/**
 *
 * @author Ari
 */
@Stateless
public class OrgaoDAO extends DAO<Orgao, Integer> implements Serializable{

    public OrgaoDAO() {
        super(Orgao.class);
    }
    
}
