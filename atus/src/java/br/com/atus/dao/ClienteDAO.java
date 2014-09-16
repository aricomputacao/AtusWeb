/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.atus.dao;

import br.com.atus.modelo.Cliente;
import java.io.Serializable;
import javax.ejb.Stateless;

/**
 *
 * @author ari
 */
@Stateless 
public class ClienteDAO extends DAO<Cliente, Long> implements Serializable{

    public ClienteDAO() {
        super(Cliente.class);
    }
    
    
    
}
