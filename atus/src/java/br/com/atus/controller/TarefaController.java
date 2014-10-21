/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.controller;

import br.com.atus.dao.TarefaDAO;
import br.com.atus.modelo.Modulo;
import br.com.atus.modelo.Tarefa;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author gilmario
 */
@Stateless
public class TarefaController extends Controller<Tarefa, Long> implements Serializable {

    @EJB
    private TarefaDAO dao;

    public TarefaController() {
    }

    @Override
    @PostConstruct
    protected void inicializaDAO() {
        setDAO(dao);
    }

    public List<Tarefa> listar(Modulo modulo) {
        return dao.listar(modulo);
    }

}
