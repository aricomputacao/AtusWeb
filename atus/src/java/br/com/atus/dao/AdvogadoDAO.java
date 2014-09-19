/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.atus.dao;

import br.com.atus.modelo.Advogado;
import java.io.Serializable;
import javax.ejb.Stateless;

/**
 *
 * @author Ari
 */
@Stateless
public class AdvogadoDAO extends DAO<Advogado, Integer> implements Serializable{

    public AdvogadoDAO() {
        super(Advogado.class);
    }
    
}
