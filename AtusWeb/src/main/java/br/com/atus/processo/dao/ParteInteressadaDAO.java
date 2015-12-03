/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.atus.processo.dao;

import br.com.atus.util.dao.DAO;
import br.com.atus.processo.modelo.ParteInteressada;
import java.io.Serializable;
import javax.ejb.Stateless;

/**
 *
 * @author ari
 */
@Stateless
public class ParteInteressadaDAO extends DAO<ParteInteressada, Long> implements Serializable{

    public ParteInteressadaDAO() {
        super(ParteInteressada.class);
    }
    
}
