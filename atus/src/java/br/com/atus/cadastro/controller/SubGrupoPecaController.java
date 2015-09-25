/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.cadastro.controller;

import br.com.atus.interfaces.Controller;
import br.com.atus.cadastro.dao.SubGrupoPecaDAO;
import br.com.atus.processo.modelo.GrupoPeca;
import br.com.atus.processo.modelo.SubGrupoPeca;
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
public class SubGrupoPecaController extends Controller<SubGrupoPeca, Integer> implements Serializable {

    @EJB
    private SubGrupoPecaDAO dao;

    @Override
    @PostConstruct
    protected void inicializaDAO() {
        setDAO(dao);
    }

    public List<SubGrupoPeca> listar(GrupoPeca grupo) {
        return dao.listar(grupo);
    }

    public List<SubGrupoPeca> connsultarPor(String campoBusca, String valorBusca) throws Exception {
        if (campoBusca.equals("nome")) {
            return dao.consultarLike(campoBusca, valorBusca);
        } else if (campoBusca.equals("grupo")) {
            return dao.consultarLikeNomeGrupo(valorBusca);
        } else {
            return dao.consultarTodos("nome");
        }

    }

}
