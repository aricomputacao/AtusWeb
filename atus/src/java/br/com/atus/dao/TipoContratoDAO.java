/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.atus.dao;

import br.com.atus.modelo.TipoContrato;
import java.io.Serializable;
import javax.ejb.Stateless;

/**
 *
 * @author Ari
 */
@Stateless
public class TipoContratoDAO extends DAO<TipoContrato, Integer> implements Serializable{

    public TipoContratoDAO() {
        super(TipoContrato.class);
    }
    
}
