/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.controller;

import br.com.atus.dao.ProcessoDAO;
import br.com.atus.modelo.Cliente;
import br.com.atus.modelo.Colaborador;
import br.com.atus.modelo.Processo;
import br.com.atus.modelo.Usuario;
import java.util.List;

/**
 *
 * @author Ari
 */

public class ConsultaDeProcessoPorColaborador implements ConsultaDeProcessos {
    private final ColaboradorController colaboradorControlerr;
    private final ProcessoDAO dao;
        private Colaborador colaborador;


    public ConsultaDeProcessoPorColaborador(ProcessoDAO dao,ColaboradorController colaboradorController) {
        this.dao = dao;
        this.colaboradorControlerr = colaboradorController;
    }

   

    private void carregarColaboradorPor(Usuario usuarioLogado) throws Exception {
        colaborador = this.colaboradorControlerr.carregar(usuarioLogado.getReferencia());
    }

    @Override
    public List<Processo> consultaProcessosPor(Cliente cliente, Usuario usuarioLogado) throws Exception  {
        carregarColaboradorPor(usuarioLogado);
        return this.dao.consultaProcessosPor(cliente, colaborador);
    }

    @Override
    public List<Processo> consultaProcessosPor(String numero, Usuario usuarioLogado) throws Exception {
        carregarColaboradorPor(usuarioLogado);
        return dao.consultarLikePor(numero,colaborador);
    }
    @Override
    public Processo consultaProcessosPor(Long id, Usuario usuarioLogado) throws Exception {
        carregarColaboradorPor(usuarioLogado);
        return dao.carregarPor(id,colaborador);
    }

}
