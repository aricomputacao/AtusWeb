/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.atus.cadastro.dao;

import br.com.atus.util.dao.DAO;
import br.com.atus.modelo.UnidadeFederativa;
import java.io.Serializable;
import javax.ejb.Stateless;

/**
 *
 * @author ari
 */
@Stateless
public class UnidadeFederativaDAO extends DAO<UnidadeFederativa, Integer> implements Serializable{

    public UnidadeFederativaDAO() {
        super(UnidadeFederativa.class);
    }

    public UnidadeFederativa buscaAbreviacao(String abre) {
       return getEm().createQuery("SELECT u from UnidadeFederativa u WHERE u.abreviacao = :abre", UnidadeFederativa.class)
               .setParameter("abre", abre)
               .getSingleResult();
    }
    
}
