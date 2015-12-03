/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.processo.controller;

import br.com.atus.cadastro.controller.ColaboradorController;
import br.com.atus.interfaces.ConsultaDeProcessos;
import br.com.atus.processo.dao.ProcessoDAO;
import br.com.atus.cadastro.modelo.Cliente;
import br.com.atus.cadastro.modelo.Colaborador;
import br.com.atus.processo.modelo.Processo;
import br.com.atus.cadastro.modelo.Usuario;
import java.util.List;

/**
 *
 * @author Ari
 */
public class ConsultaDeProcessoPorColaborador implements ConsultaDeProcessos {

    private final ColaboradorController colaboradorControlerr;
    private final ProcessoDAO dao;
    private Colaborador colaborador;

    public ConsultaDeProcessoPorColaborador(ProcessoDAO dao, ColaboradorController colaboradorController) {
        this.dao = dao;
        this.colaboradorControlerr = colaboradorController;
    }

    private void carregarColaboradorPor(Usuario usuarioLogado) throws Exception {
        colaborador = this.colaboradorControlerr.carregar(usuarioLogado.getReferencia());
    }

    @Override
    public List<Processo> consultaProcessosPor(Cliente cliente, Usuario usuarioLogado) throws Exception {
        carregarColaboradorPor(usuarioLogado);
        return this.dao.consultaProcessosPor(cliente, colaborador);
    }

    @Override
    public List<Processo> consultaProcessosPor(String numero, Usuario usuarioLogado) throws Exception {
        carregarColaboradorPor(usuarioLogado);
        return dao.consultarLikePor(numero, colaborador);
    }

    @Override
    public Processo consultaProcessosPor(Long id, Usuario usuarioLogado) throws Exception {
        carregarColaboradorPor(usuarioLogado);
        return dao.carregarPor(id, colaborador);
    }

    @Override
    public List<Processo> consultaProcessosPorLike(String nomeCliente, Usuario usuarioLogado) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
