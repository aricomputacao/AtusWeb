/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.controller;

import br.com.atua.interfaces.ConsultaDeProcessos;
import br.com.atus.dao.ProcessoDAO;
import br.com.atus.modelo.Cliente;
import br.com.atus.modelo.Processo;
import br.com.atus.modelo.Usuario;
import java.util.List;

/**
 *
 * @author Ari
 */

public class ConsultaDeProcessoEscritorio implements ConsultaDeProcessos{
    private final ProcessoDAO dao;

    public ConsultaDeProcessoEscritorio(ProcessoDAO dao) {
        this.dao = dao;
    }   
   

    @Override
    public List<Processo> consultaProcessosPor(Cliente cliente, Usuario usuarioLogado) throws Exception {
        return this.dao.consultarPor(cliente);
    }

    @Override
    public List<Processo> consultaProcessosPor(String numero, Usuario usuarioLogado) throws Exception {
       return dao.consultaLikePor(numero);
    }

    @Override
    public Processo consultaProcessosPor(Long id, Usuario usuarioLogado) throws Exception {
        return dao.carregar(id);
    }

    @Override
    public List<Processo> consultaProcessosPorLike(String nomeCliente, Usuario usuarioLogado) throws Exception {
      return dao.consultaProcessosPorLike(nomeCliente);
    }
    
   
}
