/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.managedbean;

import br.com.atus.controller.ModuloController;
import br.com.atus.controller.PermissaoController;
import br.com.atus.controller.TarefaController;
import br.com.atus.modelo.Modulo;
import br.com.atus.modelo.Permissao;
import br.com.atus.modelo.Tarefa;
import br.com.atus.modelo.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author gilmario
 */
@ViewScoped
@ManagedBean
public class PermissaoMB extends BeanGenerico<Permissao> implements Serializable {

    @EJB
    private PermissaoController controller;
    @EJB
    private ModuloController moduloController;
    @EJB
    private TarefaController tarefaController;
    private List<Permissao> permissoes;
    private Usuario usuario;
    private Tarefa tarefa;
    private Permissao permissao;
    private Modulo modulo;
    private List<Modulo> listaModulos;
    private List<Tarefa> listaTarefas;

    private boolean renderSelecionarUrs;

    public PermissaoMB() {
        super(Permissao.class);
        permissao = new Permissao();
        listaModulos = new ArrayList<>();
        listaTarefas = new ArrayList<>();
    }

    @PostConstruct
    private void init() {
        try {
            listaModulos = moduloController.listarTodos("nome");
            renderSelecionarUrs = true;
            usuario = new Usuario();
        } catch (Exception ex) {
            Logger.getLogger(PermissaoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void salvar(Permissao p) {
        try {
            if (p.getId() == null) {
                permissao.setUsuario(usuario);
                controller.salvar(p);
                permissoes.add(p);
                permissao = new Permissao();
            } else {
                controller.atualizar(p);
                permissao = new Permissao();

            }
        } catch (Exception ex) {
            Logger.getLogger(PermissaoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void listaPermUsuario(Usuario u) {
        usuario = u;
        permissoes = controller.listar(usuario);
    }

    public void trocarTela() {
        usuario = new Usuario();
    }

    public boolean isRenderSelecionarUrs() {
        return renderSelecionarUrs;
    }

    public void setRenderSelecionarUrs(boolean renderSelecionarUrs) {
        this.renderSelecionarUrs = renderSelecionarUrs;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void inicializaUsuario() {
        permissoes = controller.listar(usuario, modulo, tarefa);
    }

    public Tarefa getTarefa() {
        return tarefa;
    }

    public void setTarefa(Tarefa tarefa) {
        this.tarefa = tarefa;
    }

    public Modulo getModulo() {
        return modulo;
    }

    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }

    public List<Permissao> getPermissoes() {
        return permissoes;
    }

    public void setPermissoes(List<Permissao> permissoes) {
        this.permissoes = permissoes;
    }

    public Permissao getPermissao() {
        return permissao;
    }

    public void setPermissao(Permissao permissao) {
        this.permissao = permissao;
    }

    public void atualizaListaTarefas() {
        listaTarefas = tarefaController.listar(modulo);
    }

    public List<Modulo> getListaModulos() {
        return listaModulos;
    }

    public void setListaModulos(List<Modulo> listaModulos) {
        this.listaModulos = listaModulos;
    }

    public List<Tarefa> getListaTarefas() {
        return listaTarefas;
    }

    public void setListaTarefas(List<Tarefa> listaTarefas) {
        this.listaTarefas = listaTarefas;
    }

}
