/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.cadastro.managedbean;

import br.com.atus.processo.controller.NotificacaoController;
import br.com.atus.processo.controller.ProcessoController;
import br.com.atus.dto.ProcessoAtrasadoDTO;
import br.com.atus.dto.ProcessoGrupoDiaAtrasadoDTO;
import br.com.atus.dto.ProcessoUltimaMovimentacaoDTO;
import br.com.atus.util.managedbean.NavegacaoMB;
import br.com.atus.processo.modelo.Fase;
import br.com.atus.processo.modelo.Notificacao;
import br.com.atus.processo.modelo.Processo;
import br.com.atus.util.AssistentedeRelatorio;
import br.com.atus.util.RelatorioSession;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author Ari
 */
@ManagedBean
@ViewScoped
public class PainelHomeMB implements Serializable {

    @Inject
    private NavegacaoMB navegacaoMB;
    @Inject
    private ProcessoController processoController;
    @Inject
    private NotificacaoController notificacaoController;
    private List<ProcessoAtrasadoDTO> listaAtrasadoUsuario;
    private List<ProcessoAtrasadoDTO> listaAtrasadoGeral;
    private List<ProcessoGrupoDiaAtrasadoDTO> listaGrupoDiaAtrasadoGeralDTOs;
    private List<ProcessoGrupoDiaAtrasadoDTO> listaGrupoDiaAtrasadoSetorDTOs;
    private List<Processo> listaProcessos;
    private List<Notificacao> listanNotificacao;
    private List<ProcessoUltimaMovimentacaoDTO> listaUltimaMovimentacaoDTOs;

    private Notificacao notificacao;

    @PostConstruct
    private void init() {
        listaGrupoDiaAtrasadoGeralDTOs = processoController.processoGrupoDiaAtrasadoGeral();
        listaGrupoDiaAtrasadoSetorDTOs = processoController.processoGrupoDiaAtrasadoSetor(navegacaoMB.getUsuarioLogado());
//        listanNotificacao = notificacaoController.listaNotificacaoAtiva();
        listaUltimaMovimentacaoDTOs = new ArrayList<>();
        listaProcessos = new ArrayList<>();
        notificacao = new Notificacao();
    }

    public void marcarLido(Notificacao n) {
        try {
            n.setAtivo(true);
            notificacaoController.atualizar(n);
            listanNotificacao = notificacaoController.listaNotificacaoAtiva();
        } catch (Exception ex) {
            Logger.getLogger(PainelHomeMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void imprimirProcessos(Fase f) {
        listarProcessoFase(f);
        if (!listaUltimaMovimentacaoDTOs.isEmpty()) {
            Map<String, Object> m = new HashMap<>();
            byte[] rel = new AssistentedeRelatorio().relatorioemByte(listaUltimaMovimentacaoDTOs, m, "WEB-INF/relatorios/rel_processo_2.jasper", "Relatório de Processos");
            RelatorioSession.setBytesRelatorioInSession(rel);
        }

    }

    public void setarNotificacao(Notificacao n) {
        notificacao = n;
    }

    public void listarProcessoFase(Fase f) {
        try {
            listaProcessos = processoController.consultarProcessoDa(f,navegacaoMB.getUsuarioLogado());
            listaUltimaMovimentacaoDTOs = processoController.ultimasMovimentacoesDe(listaProcessos);
            Collections.sort(listaUltimaMovimentacaoDTOs);
        } catch (Exception ex) {
            Logger.getLogger(PainelHomeMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<ProcessoGrupoDiaAtrasadoDTO> getListaGrupoDiaAtrasadoGeralDTOs() {
        return listaGrupoDiaAtrasadoGeralDTOs;
    }

    public void setListaGrupoDiaAtrasadoGeralDTOs(List<ProcessoGrupoDiaAtrasadoDTO> listaGrupoDiaAtrasadoGeralDTOs) {
        this.listaGrupoDiaAtrasadoGeralDTOs = listaGrupoDiaAtrasadoGeralDTOs;
    }

    public List<ProcessoAtrasadoDTO> getListaAtrasadoUsuario() {
        return listaAtrasadoUsuario;
    }

    public void setListaAtrasadoUsuario(List<ProcessoAtrasadoDTO> listaAtrasadoUsuario) {
        this.listaAtrasadoUsuario = listaAtrasadoUsuario;
    }

    public List<ProcessoAtrasadoDTO> getListaAtrasadoGeral() {
        return listaAtrasadoGeral;
    }

    public void setListaAtrasadoGeral(List<ProcessoAtrasadoDTO> listaAtrasadoGeral) {
        this.listaAtrasadoGeral = listaAtrasadoGeral;
    }

    public List<ProcessoGrupoDiaAtrasadoDTO> getListaGrupoDiaAtrasadoSetorDTOs() {
        return listaGrupoDiaAtrasadoSetorDTOs;
    }

    public void setListaGrupoDiaAtrasadoSetorDTOs(List<ProcessoGrupoDiaAtrasadoDTO> listaGrupoDiaAtrasadoSetorDTOs) {
        this.listaGrupoDiaAtrasadoSetorDTOs = listaGrupoDiaAtrasadoSetorDTOs;
    }

    public List<Processo> getListaProcessos() {
        return listaProcessos;
    }

    public void setListaProcessos(List<Processo> listaProcessos) {
        this.listaProcessos = listaProcessos;
    }

    public List<Notificacao> getListanNotificacao() {
        return listanNotificacao;
    }

    public void setListanNotificacao(List<Notificacao> listanNotificacao) {
        this.listanNotificacao = listanNotificacao;
    }

    public Notificacao getNotificacao() {
        return notificacao;
    }

    public void setNotificacao(Notificacao notificacao) {
        this.notificacao = notificacao;
    }

    public List<ProcessoUltimaMovimentacaoDTO> getListaUltimaMovimentacaoDTOs() {
        return listaUltimaMovimentacaoDTOs;
    }

    public void setListaUltimaMovimentacaoDTOs(List<ProcessoUltimaMovimentacaoDTO> listaUltimaMovimentacaoDTOs) {
        this.listaUltimaMovimentacaoDTOs = listaUltimaMovimentacaoDTOs;
    }

}
