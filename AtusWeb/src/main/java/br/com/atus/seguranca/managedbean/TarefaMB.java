/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.seguranca.managedbean;

import br.com.atus.cadastro.managedbean.ClienteMB;
import br.com.atus.util.managedbean.BeanGenerico;
import br.com.atus.util.managedbean.NavegacaoMB;
import br.com.atus.seguranca.controller.ModuloController;
import br.com.atus.seguranca.controller.TarefaController;
import br.com.atus.seguranca.modelo.Modulo;
import br.com.atus.seguranca.modelo.Tarefa;
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
 * @author gilmario
 */
@ViewScoped
@ManagedBean
public class TarefaMB extends BeanGenerico<Tarefa> implements Serializable {

    @EJB
    private TarefaController controller;
    @EJB
    private ModuloController moduloController;
    @Inject
    private NavegacaoMB navegacaoMB;
    private Tarefa tarefa;
    private List<Tarefa> lista;
    private List<Modulo> listaModulos;

    public TarefaMB() {
        super(Tarefa.class);
    }

    @PostConstruct
    public void init() {
        tarefa = (Tarefa) navegacaoMB.getRegistroMapa("tarefa", new Tarefa());
        lista = new ArrayList<>();
        atualizarListaModulos();
    }

    public void salvar() {
        try {
            if (tarefa.getId() == null) {
                controller.salvar(tarefa);
            } else {
                controller.atualizar(tarefa);
            }
            init();
            MenssagemUtil.addMessageInfo(NavegacaoMB.getMsg("salvar", MenssagemUtil.MENSAGENS));
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("falha", MenssagemUtil.MENSAGENS), ex, "Tarefa");
            Logger.getLogger(ClienteMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void listar() {
        try {
            lista = controller.consultarLike(getCampoBusca(), getValorBusca());
            if (lista.isEmpty()) {
                MenssagemUtil.addMessageInfo(NavegacaoMB.getMsg("consulta.vazia", MenssagemUtil.MENSAGENS));
            }
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("consulta.vazia", MenssagemUtil.MENSAGENS));
            Logger.getLogger(ClienteMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void atualizarListaModulos() {
        try {
            listaModulos = moduloController.consultarTodos("nome");
        } catch (Exception e) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("consulta.vazia", MenssagemUtil.MENSAGENS));
            Logger.getLogger(ClienteMB.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public Tarefa getTarefa() {
        return tarefa;
    }

    public void setTarefa(Tarefa tarefa) {
        this.tarefa = tarefa;
    }

    public List<Tarefa> getLista() {
        return lista;
    }

    public void setLista(List<Tarefa> lista) {
        this.lista = lista;
    }

    public List<Modulo> getListaModulos() {
        return listaModulos;
    }

    public void setListaModulos(List<Modulo> listaModulos) {
        this.listaModulos = listaModulos;
    }

}
