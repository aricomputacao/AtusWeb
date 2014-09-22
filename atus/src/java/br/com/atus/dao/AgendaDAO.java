/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.atus.dao;

import br.com.atus.modelo.Agenda;
import java.io.Serializable;
import javax.ejb.Stateless;

/**
 *
 * @author Ari
 */
@Stateless
public class AgendaDAO extends DAO<Agenda, Long> implements Serializable{

    public AgendaDAO() {
        super(Agenda.class);
    }
    
}
