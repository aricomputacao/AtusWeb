/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.atus.cadastro.controller;

import br.com.atus.interfaces.Controller;
import br.com.atus.cadastro.dao.CidadeDAO;
import br.com.atus.cadastro.modelo.Cidade;
import br.com.atus.cadastro.modelo.UnidadeFederativa;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author ari
 */
@Stateless
public class CidadeController extends Controller<Cidade, Integer> implements Serializable{

    @EJB
    private CidadeDAO dao;
    
    @PostConstruct
    @Override
    protected void inicializaDAO() {
        setDAO(dao);
    }

    public List<Cidade> listaPorUf(UnidadeFederativa uf) {
       return dao.listarPorUf(uf);
    }

    public Cidade buscarUfNome(UnidadeFederativa uf, String cidade) {
       return dao.buscarUfNome(uf,cidade);
    }
    
}
