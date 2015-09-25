/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.atus.processo.dao;

import br.com.atus.util.dao.DAO;
import br.com.atus.processo.modelo.Adversario;
import java.io.Serializable;
import javax.ejb.Stateless;

/**
 *
 * @author ari
 */
@Stateless
public class AdversarioDAO extends DAO<Adversario, Long> implements Serializable{

    public AdversarioDAO() {
        super(Adversario.class);
    }
    
}
