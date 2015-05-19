/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.financeiro.dao;

import br.com.atus.util.dao.DAO;
import br.com.atus.financeiro.modelo.Cooptacao;
import java.io.Serializable;
import javax.ejb.Stateless;

/**
 *
 * @author Ari
 */
@Stateless
public class CooptacaoDAO extends DAO<Cooptacao, Long> implements Serializable{

    public CooptacaoDAO() {
        super(Cooptacao.class);
    }
    
}
