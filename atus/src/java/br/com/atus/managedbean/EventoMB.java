/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.managedbean;

import br.com.atus.controller.AgendaController;
import br.com.atus.controller.EventoController;
import br.com.atus.modelo.Adversario;
import br.com.atus.modelo.Agenda;
import br.com.atus.modelo.Evento;
import br.com.atus.modelo.Processo;
import br.com.atus.util.AssistentedeRelatorio;
import br.com.atus.util.MenssagemUtil;
import br.com.atus.util.RelatorioSession;
import java.io.Serializable;
import java.util.ArrayList;
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
import javax.inject.Inject;

/**
 *
 * @author ari
 */
@ManagedBean
@ViewScoped
public class EventoMB extends BeanGenerico<Evento> implements Serializable {

    @Inject
    private NavegacaoMB navegacaoMB;
    @EJB
    private EventoController controller;
    @EJB
    private AgendaController agendaController;
    private Evento evento;
    private List<Agenda> listaAgendas;
    private List<Evento> listaEventos;
    private Date dataInicial;
    private Date dataFinal;

    public EventoMB() {
        super(Evento.class);
    }

    @PostConstruct
    public void init() {
        evento = (Evento) navegacaoMB.getRegistroMapa("evento", new Evento());
        dataFinal = new Date();
        dataInicial = new Date();
        listaEventos = new ArrayList<>();
    }

    public void salvar() {
        try {
            if (evento.getId() == null) {
                controller.salvar(evento);
            } else {
                controller.salvarouAtualizar(evento);
            }
            agendaController.addAgenda(evento);
            init();
            MenssagemUtil.addMessageInfo(NavegacaoMB.getMsg("salvar", MenssagemUtil.MENSAGENS));

        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("falha", MenssagemUtil.MENSAGENS), ex, "Evento");
            Logger.getLogger(AdvogadoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void listarPorPeriodo() {
        listaEventos = controller.listarPorPeriodo(dataInicial, dataFinal);
        if (listaEventos.isEmpty()) {
            MenssagemUtil.addMessageWarn(NavegacaoMB.getMsg("consulta.vazia", MenssagemUtil.MENSAGENS));

        }
    }

  

    public void listar() {
        try {
            if (getValorBusca() == null || getValorBusca().equals("")) {
                listaEventos = controller.listarTodos("nome");
            } else {
                listaEventos = controller.listarLike("nome", getValorBusca());

            }
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("consulta.vazia", MenssagemUtil.MENSAGENS));
            Logger.getLogger(EventoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void excluir(Evento ee) {
        try {
            ee = controller.gerenciar(ee.getId());
            agendaController.delAgenda(ee);
            controller.excluir(ee);
            listaEventos.remove(ee);
            MenssagemUtil.addMessageInfo(NavegacaoMB.getMsg("excluir", MenssagemUtil.MENSAGENS));

        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("excluir.falha", MenssagemUtil.MENSAGENS));
            Logger.getLogger(AdvogadoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void imprimir() {
        if (!listaEventos.isEmpty()) {
           
            Map<String, Object> m = new HashMap<>();
            byte[] rel = new AssistentedeRelatorio().relatorioemByte(listaEventos, m, "WEB-INF/relatorios/rel_eventos.jasper", "Relatório de Eventos");
            RelatorioSession.setBytesRelatorioInSession(rel);
        }

    }

    public void imprimirEvento(Evento e) {
        List<Evento> l = new ArrayList<>();
        l.add(e);
        Map<String, Object> m = new HashMap<>();
        byte[] rel = new AssistentedeRelatorio().relatorioemByte(l, m, "WEB-INF/relatorios/rel_prot_evento.jasper", "teste");
        RelatorioSession.setBytesRelatorioInSession(rel);

    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public List<Agenda> getListaAgendas() {
        return listaAgendas;
    }

    public void setListaAgendas(List<Agenda> listaAgendas) {
        this.listaAgendas = listaAgendas;
    }

    public List<Evento> getListaEventos() {
        return listaEventos;
    }

    public void setListaEventos(List<Evento> listaEventos) {
        this.listaEventos = listaEventos;
    }

    public Date getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(Date dataInicial) {
        this.dataInicial = dataInicial;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

}
