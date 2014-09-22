/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.managedbean;

import br.com.atus.controller.AgendaController;
import br.com.atus.enumerated.TipoAgenda;
import br.com.atus.modelo.Agenda;
import br.com.atus.modelo.EspecieEvento;
import br.com.atus.util.MenssagemUtil;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

/**
 *
 * @author Ari
 */
@ManagedBean
@ViewScoped
public class AgendaMB extends BeanGenerico<Agenda> implements Serializable {

    @Inject
    private NavegacaoMB navegacaoMB;
    @EJB
    private AgendaController controller;
    private List<Agenda> listaAgenda;
    private Agenda agenda;
    private TipoAgenda tipoAgenda;
    private ScheduleModel eventModel;

    public AgendaMB() {
        super(Agenda.class);
    }

    /**
     * Metodo para inicializar a nossa agenda. Atraves deste metodo retornamos a
     * lista de eventos e montamos nosso calendario ScheduleModel atraves de um
     * DefaultScheduleModel
     */
    @PostConstruct
    private void init() {

        try {
            agenda = new Agenda();
            eventModel = new DefaultScheduleModel();

            //recupera a lista de eventos
            listaAgenda = controller.listarTodos("id");

            //percorre a lista de eventos e popula o calendario
            for (Agenda agenda : listaAgenda) {

                DefaultScheduleEvent evento = new DefaultScheduleEvent();
                evento.setAllDay(agenda.isDiaTodo());
                evento.setEndDate(agenda.getDataFim());
                evento.setStartDate(agenda.getDataInicio());
                evento.setTitle(agenda.getTitulo());
                evento.setDescription(agenda.getDescricao());
                evento.setData(agenda.getId());
                evento.setEditable(true); //pertir que o usuario edite

                //aqui e setado um tipo especifico de css para cada tipo de evento
                if (agenda.getEspecieEvento().getId() == 7) {
                    evento.setStyleClass("audiencia");
                } else if (agenda.getEspecieEvento().getId() == 8) {
                    evento.setStyleClass("pericia");
                } else if (agenda.getEspecieEvento().getId() == 9) {
                    evento.setStyleClass("sessao");
                } else if (agenda.getEspecieEvento().getId() == 10) {
                    evento.setStyleClass("carga");
                } else if (agenda.getEspecieEvento().getId() == 11) {
                    evento.setStyleClass("requerimento");
                } else if (agenda.getEspecieEvento().getId() == 12) {
                    evento.setStyleClass("publicacao");
                } else {
                    evento.setStyleClass("outro");
                }

                eventModel.addEvent(evento); //o evento e adicionado na lista
            }
        } catch (Exception ex) {
            Logger.getLogger(AgendaMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo para salvar o evento
     */
    public void salvar() {

        //se o ID for zero tenho que incluir na lista
        //caso contrario nao e necessario
        if (agenda.getId() == null) {

            //uma validacao basica para verificar se o usuario informou 
            //as datas corretamente
            if (agenda.getDataInicio().getTime() <= agenda.getDataFim().getTime()) {

                try {
                    controller.salvarouAtualizar(agenda);
                    agenda = new Agenda();
                    init();
                    MenssagemUtil.addMessageInfo(NavegacaoMB.getMsg("salvar", MenssagemUtil.MENSAGENS));

                } catch (Exception ex) {
                    Logger.getLogger(AgendaMB.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                MenssagemUtil.addMessageInfo("data");

            }

        } else {
            init();
        }
    }

    /**
     * Evento para quando o usuario clica em um espaco em branco no calendario
     *
     * @param selectEvent
     */
    public void quandoNovo(SelectEvent selectEvent) {

        ScheduleEvent event = new DefaultScheduleEvent("",
                (Date) selectEvent.getObject(), (Date) selectEvent.getObject());

        agenda = new Agenda();
        //recupero a data em q ele clicou
        agenda.setDataInicio(event.getStartDate());
        agenda.setDataFim(event.getEndDate());
    }

    /**
     * Evento para quando usuario clica em um enveto ja existente
     *
     * @param selectEvent
     */
    public void quandoSelecionado(SelectEvent selectEvent) {

        ScheduleEvent event = (ScheduleEvent) selectEvent.getObject();

        for (Agenda agda : listaAgenda) {
            if (agda.getId() == (Long) event.getData()) {
                agenda = agda;
                break;
            }
        }
    }

    /**
     * Evento para quando o usuario 'move' um evento atraves de 'drag and drop'
     *
     * @param event
     */
    public void quandoMovido(ScheduleEntryMoveEvent event) {

        for (Agenda agda : listaAgenda) {

            if (agda.getId() == (Long) event.getScheduleEvent().getData()) {
                try {
                    agenda = agda;
                    controller.salvarouAtualizar(agda);
                    break;
                } catch (Exception ex) {
                    Logger.getLogger(AgendaMB.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    /**
     * Evento para quando o usuario 'redimenciona' um evento
     *
     * @param event
     */
    public void quandoRedimensionado(ScheduleEntryResizeEvent event) {

        for (Agenda agda : listaAgenda) {
            if (agda.getId() == (Long) event.getScheduleEvent().getData()) {
                try {
                    agenda = agda;
                    controller.salvarouAtualizar(agda);
                    break;
                } catch (Exception ex) {
                    Logger.getLogger(AgendaMB.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public List<Agenda> getListaAgenda() {
        return listaAgenda;
    }

    public void setListaAgenda(List<Agenda> listaAgenda) {
        this.listaAgenda = listaAgenda;
    }

    public Agenda getAgenda() {
        return agenda;
    }

    public void setAgenda(Agenda agenda) {
        this.agenda = agenda;
    }

    public TipoAgenda getTipoAgenda() {
        return tipoAgenda;
    }

    public void setTipoAgenda(TipoAgenda tipoAgenda) {
        this.tipoAgenda = tipoAgenda;
    }

    public ScheduleModel getEventModel() {
        return eventModel;
    }

    public void setEventModel(ScheduleModel eventModel) {
        this.eventModel = eventModel;
    }

}
