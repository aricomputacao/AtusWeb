/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.managedbean;

import br.com.atus.controller.EspecieEventoController;
import br.com.atus.modelo.EspecieEvento;
import br.com.atus.util.MenssagemUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
public class EspecieEventoMB extends BeanGenerico<EspecieEvento> implements Serializable {

    @Inject
    private NavegacaoMB navegacaoMB;
    @EJB
    private EspecieEventoController controller;
    private List<EspecieEvento> listaEspecieEventos;
    private EspecieEvento especieEvento;

    public EspecieEventoMB() {
        super(EspecieEvento.class);

    }

    @PostConstruct
    public void init() {
        listaEspecieEventos = new ArrayList<>();
        especieEvento = (EspecieEvento) navegacaoMB.getRegistroMapa("especie_evento", new EspecieEvento());
    }

    public void salvar() {
        try {
            controller.atualizar(especieEvento);
            init();
            MenssagemUtil.addMessageInfo(NavegacaoMB.getMsg("salvar", MenssagemUtil.MENSAGENS));

        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("falha", MenssagemUtil.MENSAGENS), ex, "RamoJudiciario");
            Logger.getLogger(EspecieEventoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void listar() {
        try {
            if (getValorBusca() == null || getValorBusca().equals("")) {
                listaEspecieEventos = controller.listarTodos("nome");
            } else {

                listaEspecieEventos = controller.listarLike("nome", getValorBusca());

            }
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg(MenssagemUtil.MENSAGENS, "lista.vazia"));

            Logger.getLogger(EspecieEventoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<EspecieEvento> getListaEspecieEventos() {
        return listaEspecieEventos;
    }

    public void setListaEspecieEventos(List<EspecieEvento> listaEspecieEventos) {
        this.listaEspecieEventos = listaEspecieEventos;
    }

    public EspecieEvento getEspecieEvento() {
        return especieEvento;
    }

    public void setEspecieEvento(EspecieEvento especieEvento) {
        this.especieEvento = especieEvento;
    }

}
