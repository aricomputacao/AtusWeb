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

    private Usuario usuario;
    private Tarefa tarefa;
    private Permissao permissao;
    private Modulo modulo;
    private List<Modulo> listaModulos;
    private List<Tarefa> listaTarefas;
    @EJB
    private PermissaoController controller;
    @EJB
    private ModuloController moduloController;
    @EJB
    private TarefaController tarefaController;
    private List<Permissao> permissoes;

    public PermissaoMB() {
        super(Permissao.class);
        permissao = new Permissao();
        listaModulos = new ArrayList<>();
        listaTarefas = new ArrayList<>();
    }

    public void salvar(Permissao p) throws Exception {
        if (p.getId() == null) {
            controller.salvar(p);
        } else {
            controller.atualizar(p);
        }
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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

}
