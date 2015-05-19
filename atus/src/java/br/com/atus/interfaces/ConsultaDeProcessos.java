package br.com.atus.interfaces;


import br.com.atus.modelo.Cliente;
import br.com.atus.modelo.Processo;
import br.com.atus.modelo.Usuario;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Ari
 */
public interface ConsultaDeProcessos {

    public List<Processo> consultaProcessosPor(Cliente cliente,Usuario usuarioLogado) throws Exception ;
    public List<Processo> consultaProcessosPorLike(String nomeCliente,Usuario usuarioLogado) throws Exception ;

    public List<Processo> consultaProcessosPor(String numero,Usuario usuarioLogado) throws Exception ;

    public Processo consultaProcessosPor(Long id,Usuario usuarioLogado) throws Exception ;

}
