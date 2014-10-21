/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.atus.controller;

import br.com.atus.dao.MovimentacaoDAO;
import br.com.atus.modelo.Movimentacao;
import br.com.atus.modelo.Processo;
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
public class MovimentacaoController extends Controller<Movimentacao, Long> implements Serializable{

    @EJB
    private MovimentacaoDAO dao;
    
    @PostConstruct
    @Override
    protected void inicializaDAO() {
        setDAO(dao);
    }

    public List<Movimentacao> listarPorProcesso(Processo processo) {
        return dao.listarPorProcesso(processo);
    }
    
}
