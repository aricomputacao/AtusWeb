/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.managedbean;

import br.com.atus.controller.EventoController;
import br.com.atus.controller.ProcessoController;
import br.com.atus.dto.ProcessoUltimaMovimentacaoDTO;
import br.com.atus.dto.ProcessosAtrasadoRelatorioDTO;
import br.com.atus.modelo.Cliente;
import br.com.atus.modelo.Colaborador;
import br.com.atus.modelo.Evento;
import br.com.atus.modelo.Processo;
import br.com.atus.modelo.Usuario;
import br.com.atus.util.AssistentedeRelatorio;
import br.com.atus.util.RelatorioSession;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * Bean para relatorio
 *
 * @author Ari
 */
@ViewScoped
@ManagedBean
public class RelatorioMB extends BeanGenerico<ProcessosAtrasadoRelatorioDTO> implements Serializable {

    @EJB
    private ProcessoController processoController;
    @EJB
    private EventoController eventoController;
    
    private List<ProcessosAtrasadoRelatorioDTO> listaProcessosAtrasadoRelatorioDTOs;
    private List<ProcessoUltimaMovimentacaoDTO> listaProcessoUltimaMovimentacaoDTOs;
    private List<Evento> listaEventos;
    private Cliente cliente;
    private Usuario usuario;
    private Colaborador colaborador;
    private Date dataInicial;
    private Date dataFinal;

    public RelatorioMB() {
        super(ProcessosAtrasadoRelatorioDTO.class);
    }

    @PostConstruct
    public void init() {
        listaProcessosAtrasadoRelatorioDTOs = new ArrayList<>();
        listaProcessoUltimaMovimentacaoDTOs = new ArrayList<>();
        listaEventos = new ArrayList<>();
        usuario = new Usuario();
        colaborador = new Colaborador();
        dataInicial = new Date();
        dataFinal = new Date();
    }

    
    public void consultaEventoColaborador(){
        listaEventos = eventoController.consultaEventoColaboradorPor(colaborador,dataInicial,dataFinal);
    }
    
    public void listarProcessosAtrasados() {
        listaProcessoUltimaMovimentacaoDTOs.clear();
        listaProcessoUltimaMovimentacaoDTOs = processoController.listaProcessosAtrasadosRelatorio(usuario);
        Collections.sort(listaProcessoUltimaMovimentacaoDTOs);
        usuario = new Usuario();

    }

    public void listarProcessoColaborador() {
        try {
            List<Processo> listaProcessos = processoController.consultaPorColaborador(colaborador);
            listaProcessoUltimaMovimentacaoDTOs.clear();
            listaProcessoUltimaMovimentacaoDTOs = processoController.ultimasMovimentacoesDe(listaProcessos);
            Collections.sort(listaProcessoUltimaMovimentacaoDTOs);
        } catch (Exception ex) {
            Logger.getLogger(RelatorioMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void imprimirProcessoColaborador() {
        if (!listaProcessoUltimaMovimentacaoDTOs.isEmpty()) {
            Map<String, Object> m = new HashMap<>();
            byte[] rel = new AssistentedeRelatorio().relatorioemByte(listaProcessoUltimaMovimentacaoDTOs, m, "WEB-INF/relatorios/rel_processo_colaborador.jasper", "Relatório de Processos por Colaborador");
            RelatorioSession.setBytesRelatorioInSession(rel);
        }

    }

    public void imprimirProcessoAtrasados() {
        if (!listaProcessoUltimaMovimentacaoDTOs.isEmpty()) {
            Map<String, Object> m = new HashMap<>();
            byte[] rel = new AssistentedeRelatorio().relatorioemByte(listaProcessoUltimaMovimentacaoDTOs, m, "WEB-INF/relatorios/rel_processos_atrasados.jasper", "Relatório de Processos Atrasados");
            RelatorioSession.setBytesRelatorioInSession(rel);
        }

    }
    
    
    public void imprimirAgendaColaborador() {
        if (!listaEventos.isEmpty()) {
            Map<String, Object> m = new HashMap<>();
            byte[] rel = new AssistentedeRelatorio().relatorioemByte(listaEventos, m, "WEB-INF/relatorios/rel_agenda_colaborador.jasper", "Agenda do Colaborador");
            RelatorioSession.setBytesRelatorioInSession(rel);
        }

    }

    public List<ProcessosAtrasadoRelatorioDTO> getListaProcessosAtrasadoRelatorioDTOs() {
        return listaProcessosAtrasadoRelatorioDTOs;
    }

    public void setListaProcessosAtrasadoRelatorioDTOs(List<ProcessosAtrasadoRelatorioDTO> listaProcessosAtrasadoRelatorioDTOs) {
        this.listaProcessosAtrasadoRelatorioDTOs = listaProcessosAtrasadoRelatorioDTOs;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Colaborador getColaborador() {
        return colaborador;
    }

    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
    }

    public List<ProcessoUltimaMovimentacaoDTO> getListaProcessoUltimaMovimentacaoDTOs() {
        return listaProcessoUltimaMovimentacaoDTOs;
    }

    public void setListaProcessoUltimaMovimentacaoDTOs(List<ProcessoUltimaMovimentacaoDTO> listaProcessoUltimaMovimentacaoDTOs) {
        this.listaProcessoUltimaMovimentacaoDTOs = listaProcessoUltimaMovimentacaoDTOs;
    }

    public List<Evento> getListaEventos() {
        return listaEventos;
    }

    public Date getDataInicial() {
        return dataInicial;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataInicial(Date dataInicial) {
        this.dataInicial = dataInicial;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    
}
