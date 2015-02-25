/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.controller;

import br.com.atus.dao.ProcessoDAO;
import br.com.atus.dto.ProcessoAtrasadoDTO;
import br.com.atus.dto.ProcessoGrupoDiaAtrasadoDTO;
import br.com.atus.dto.ProcessosAtrasadoRelatorioDTO;
import br.com.atus.modelo.Cliente;
import br.com.atus.modelo.Fase;
import br.com.atus.modelo.Processo;
import br.com.atus.modelo.Usuario;
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
public class ProcessoController extends Controller<Processo, Long> implements Serializable {

    @EJB
    private ProcessoDAO dao;
    @EJB
    private MovimentacaoController movimentacaoController;

    @PostConstruct
    @Override
    protected void inicializaDAO() {
        setDAO(dao);
    }

    public List<ProcessoAtrasadoDTO> processoAtrasadoUsuario(Usuario u) {
        return dao.processoAtrasadoUsuario(u);
    }

    public List<ProcessoAtrasadoDTO> processoAtrasadoGeral() {
        return dao.processoAtrasadoGeral();
    }

    public List<ProcessoGrupoDiaAtrasadoDTO> processoGrupoDiaAtrasadoGeral() {
        return dao.processoGrupoDiaAtrasadoGeral();
    }

    public List<ProcessoGrupoDiaAtrasadoDTO> processoGrupoDiaAtrasadoSetor(Usuario usuarioLogado) {
        return dao.processoGrupoDiaAtrasadoSetor(usuarioLogado);

    }

    public List<ProcessosAtrasadoRelatorioDTO> listaProcessosAtrasadosRelatorio(Cliente c) {
        if (c.getId() != null) {
            return dao.listaProcessosAtrasadosRelatorio(c);

        } else {
            return dao.listaProcessosAtrasadosRelatorio();

        }
    }
    public List<ProcessosAtrasadoRelatorioDTO> listaProcessosAtrasadosRelatorio(Usuario u) {
        if (u.getId() != null) {
            return dao.listaProcessosAtrasadosRelatorio(u);

        } else {
            return dao.listaProcessosAtrasadosRelatorio();

        }
    }

    public List<Processo> listarPorFase(Fase f) {
        return dao.listarPorFase(f);
    }

    public List<Processo> listarPorCliente(Cliente c) {
        return dao.listarPorCliente(c);
    }

    public List<Processo> listarLikeNumero(String num) {
        return dao.listarLikeNumero(num);
    }

    public void salvarouAtualizarEditarFase(Processo processo, Fase fs,Usuario u) throws Exception {
        if (processo.getId() == null) {
            processo.setUsuarioCadastro(u);
        }else{
            //se a fase alterar add movimentação
            if (!fs.equals(processo.getFase())) {
                movimentacaoController.addMovimentacao(processo, fs,u);
            }
        }
        salvarouAtualizar(processo);
        
    }

    public List<Processo> listarFase() {
        return dao.listarFase();
    }
}
