/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.atus.dao;

import br.com.atus.modelo.Atendimento;
import java.io.Serializable;
import javax.ejb.Stateless;

/**
 *
 * @author ari
 */
@Stateless
public class AtendimentoDAO extends DAO<Atendimento,Long> implements Serializable{

    public AtendimentoDAO() {
        super(Atendimento.class);
    }
    
}
