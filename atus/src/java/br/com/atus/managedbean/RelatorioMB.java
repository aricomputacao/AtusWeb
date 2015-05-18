/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.managedbean;

import br.com.atus.controller.EventoController;
import br.com.atus.controller.MovimentacaoController;
import br.com.atus.controller.ProcessoController;
import br.com.atus.controller.UsuarioController;
import br.com.atus.dto.ProcessoUltimaMovimentacaoDTO;
import br.com.atus.dto.ProcessosAtrasadoRelatorioDTO;
import br.com.atus.modelo.Cliente;
import br.com.atus.modelo.Colaborador;
import br.com.atus.modelo.Evento;
import br.com.atus.modelo.Fase;
import br.com.atus.modelo.Movimentacao;
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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

/**
 * Bean para relatorio
 *
 * @author Ari
 */
@ViewScoped
@ManagedBean
public class RelatorioMB extends BeanGenerico<ProcessosAtrasadoRelatorioDTO> implements Serializable {

    @Inject
    private ProcessoController processoController;
    @Inject
    private EventoController eventoController;
    @Inject
    private NavegacaoMB navegacaoMB;
    @Inject
    private MovimentacaoController movimentacaoController;
    @Inject
    private UsuarioController usuarioController;
    private List<ProcessosAtrasadoRelatorioDTO> listaProcessosAtrasadoRelatorioDTOs;
    private List<ProcessoUltimaMovimentacaoDTO> listaProcessoUltimaMovimentacaoDTOs;
    private List<Evento> listaEventos;
    private List<Movimentacao> listaMovimentacaos;
    private List<Fase> listaFasesSelection;
    private List<Usuario> listaDeUsuariosSelection;
    private List<Usuario> listaDeUsuarios;
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
        try {
            listaProcessosAtrasadoRelatorioDTOs = new ArrayList<>();
            listaProcessoUltimaMovimentacaoDTOs = new ArrayList<>();
            listaDeUsuariosSelection = new ArrayList<>();
            listaEventos = new ArrayList<>();
            listaMovimentacaos = new ArrayList<>();
            listaDeUsuarios = usuarioController.consultarTodos("login");
            
            usuario = new Usuario();
            colaborador = new Colaborador();
            dataInicial = new Date();
            dataFinal = new Date();
        } catch (Exception ex) {
            Logger.getLogger(RelatorioMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void consultaMovimentacaoFase() {
        listaMovimentacaos = movimentacaoController.consultarMovimentacaoPor(listaFasesSelection, dataInicial, dataFinal);
    }
    
    public void consultarMovimentacaoPorUsuarios(){
        listaMovimentacaos = movimentacaoController.consultarMovimentacaoPorUsarios(listaDeUsuariosSelection, dataInicial, dataFinal);
    }

    public void consultaEventoColaborador() {
        listaEventos = eventoController.consultaEventoColaboradorPor(colaborador, dataInicial, dataFinal, navegacaoMB.isEhUsuarioDoEscritorio());
    }

    public void consultaEventoUsuarioColaborador() {
        listaEventos = eventoController.consultaEventoColaboradorPor(navegacaoMB.getColaborador(), dataInicial, dataFinal, navegacaoMB.isEhUsuarioDoEscritorio());
    }

    public void listarProcessosAtrasados() {
        listaProcessoUltimaMovimentacaoDTOs.clear();
        listaProcessoUltimaMovimentacaoDTOs = processoController.listaProcessosAtrasadosRelatorio(usuario);
        Collections.sort(listaProcessoUltimaMovimentacaoDTOs);
        usuario = new Usuario();

    }

    public void listarProcessoColaborador() {
        try {
            List<Processo> listaProcessos = processoController.consultaPorColaborador(colaborador,listaFasesSelection);
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

    public void imprimirMovimentacoesFAse() {
        if (!listaMovimentacaos.isEmpty()) {
            Map<String, Object> m = new HashMap<>();
            byte[] rel = new AssistentedeRelatorio().relatorioemByte(listaMovimentacaos, m, "WEB-INF/relatorios/rel_movimentacao_fase.jasper", "Movimentações da Fase");
            RelatorioSession.setBytesRelatorioInSession(rel);
        }

    }
    public void imprimirMovimentacoesUsuario() {
        if (!listaMovimentacaos.isEmpty()) {
            Map<String, Object> m = new HashMap<>();
            byte[] rel = new AssistentedeRelatorio().relatorioemByte(listaMovimentacaos, m, "WEB-INF/relatorios/rel_movimentacao_usuario.jasper", "Movimentações do Usuário");
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

    public List<Movimentacao> getListaMovimentacaos() {
        return listaMovimentacaos;
    }

    public void setListaMovimentacaos(List<Movimentacao> listaMovimentacaos) {
        this.listaMovimentacaos = listaMovimentacaos;
    }

    public List<Fase> getListaFasesSelection() {
        return listaFasesSelection;
    }

    public void setListaFasesSelection(List<Fase> listaFasesSelection) {
        this.listaFasesSelection = listaFasesSelection;
    }

    public List<Usuario> getListaDeUsuariosSelection() {
        return listaDeUsuariosSelection;
    }

    public void setListaDeUsuariosSelection(List<Usuario> listaDeUsuariosSelection) {
        this.listaDeUsuariosSelection = listaDeUsuariosSelection;
    }

    public List<Usuario> getListaDeUsuarios() {
        return listaDeUsuarios;
    }

}
