/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.processo.dao;

import br.com.atus.util.dao.DAO;
import br.com.atus.modelo.Contrato;
import java.io.Serializable;
import javax.ejb.Stateless;

/**
 *
 * @author gilmario
 */
@Stateless
public class ContratoDAO extends DAO<Contrato, Long> implements Serializable {

    public ContratoDAO() {
        super(Contrato.class);
    }

}
