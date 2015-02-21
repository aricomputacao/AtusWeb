/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.controller;

import br.com.atus.dao.MovimentacaoDAO;
import br.com.atus.dto.ProcessoUltimaMovimentacaoDTO;
import br.com.atus.modelo.Colaborador;
import br.com.atus.modelo.Fase;
import br.com.atus.modelo.Movimentacao;
import br.com.atus.modelo.Processo;
import br.com.atus.modelo.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
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
        if (processo.getId() == null) {
            return new ArrayList<>();
        } else {
            return dao.listarPorProcesso(processo);

        }
    }

    public void addMovimentacao(Processo p, Fase f, Usuario u) throws Exception {
        Movimentacao m = new Movimentacao();
        if (f != null) {
            if (!Objects.equals(p.getFase().getId(), f.getId())) {
                m.setDataMovimentacao(new Date());
                m.setFaseAntiga(f);
                m.setFaseNova(p.getFase());
                m.setProcesso(p);
                m.setUsuario(u);
                dao.salvar(m);
            }
        } else {
            m.setUsuario(u);
            m.setDataMovimentacao(new Date());
            m.setFaseNova(p.getFase());
            m.setProcesso(p);
            dao.salvar(m);

        }

    }

    public Movimentacao ultimaMovimentacao(Processo p) throws Exception {
        Long ultimoId = dao.ultimaMovimentacao(p);
        
        System.out.println(ultimoId);
        return ultimoId == null ? null : dao.carregar(ultimoId);
    }

    public List<ProcessoUltimaMovimentacaoDTO> listarProcessosColaboradores() {
        return dao.listarProcessoUltimaMovimentacao();
    }

    public List<ProcessoUltimaMovimentacaoDTO> listarProcessosColaboradores(Colaborador c) {
        if (c.getId() == null) {
            return listarProcessosColaboradores();
        }else{
           return dao.listarProcessoUltimaMovimentacao(c);
        }
    }

}
