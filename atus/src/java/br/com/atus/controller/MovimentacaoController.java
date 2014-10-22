/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.controller;

import br.com.atus.dao.MovimentacaoDAO;
import br.com.atus.modelo.Fase;
import br.com.atus.modelo.Movimentacao;
import br.com.atus.modelo.Processo;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author ari
 */
@Stateless
public class MovimentacaoController extends Controller<Movimentacao, Long> implements Serializable {

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

    public void addMovimentacao(Processo p, Fase f) throws Exception {
        Movimentacao m = new Movimentacao();
        if (f != null) {
            if (!Objects.equals(p.getFase().getId(), f.getId())) {
                m.setDataMovimentacao(new Date());
                m.setFaseAntiga(p.getFase());
                m.setFaseNova(f);
                m.setProcesso(p);
                dao.salvar(m);
            }
        } else {
            
            m.setDataMovimentacao(new Date());
            m.setFaseNova(p.getFase());
            m.setProcesso(p);
            dao.salvar(m);

        }

    }

}
