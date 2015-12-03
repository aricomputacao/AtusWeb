/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.atus.cadastro.dao;

import br.com.atus.interfaces.Controller;
import br.com.atus.util.dao.DAO;
import br.com.atus.processo.modelo.ObjetoProcesso;
import java.io.Serializable;
import javax.ejb.Stateless;

/**
 *
 * @author ari
 */
@Stateless
public class ObjetoProcessoDAO extends DAO<ObjetoProcesso, Long> implements Serializable{

    public ObjetoProcessoDAO() {
        super(ObjetoProcesso.class);
    }

    
}
