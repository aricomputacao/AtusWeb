/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.atus.cadastro.dao;

import br.com.atus.util.dao.DAO;
import br.com.atus.modelo.Profissao;
import java.io.Serializable;
import javax.ejb.Stateless;

/**
 *
 * @author ari
 */
@Stateless
public class ProfissaoDAO extends DAO<Profissao, Integer>{

    public ProfissaoDAO() {
        super(Profissao.class);
    }
    
}
