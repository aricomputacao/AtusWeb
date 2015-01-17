/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.managedbean;

import br.com.atus.controller.PermissaoController;
import br.com.atus.controller.TarefaController;
import br.com.atus.controller.UsuarioController;
import br.com.atus.enumerated.Perfil;
import br.com.atus.modelo.Permissao;
import br.com.atus.modelo.Tarefa;
import br.com.atus.modelo.Usuario;
import br.com.atus.util.CriptografiaSenha;
import br.com.atus.util.MenssagemUtil;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author Ari
 */
@ApplicationScoped
@ManagedBean
public class ConfiguracaoSistemaMB implements Serializable {

    @EJB
    private UsuarioController usuarioController;
    @EJB
    private PermissaoController permissaoController;
    @EJB
    private TarefaController tarefaController;
    private Usuario usuario;
    private List<Tarefa> listaTarefas;
    private  String nomeSistema = "Atus";

    @PostConstruct
    public void init() {
        try {
            CriptografiaSenha cs = new CriptografiaSenha();
            usuario = new Usuario();
            listaTarefas = tarefaController.listarTodos("id");
            //Fazer o cadastro do suario administrador
            usuario = usuarioController.usuarioLogin("atus");
            if (usuario.getId() == null) {
                System.out.print("-------------------------------- Inicio Registrando usuário administrador ---------------------------------------");
                usuario.setAtivo(true);
                usuario.setLogin("atus");
                usuario.setNome("Administrador do Sistema ");
                usuario.setPerfil(Perfil.ADMINISTRADOR);
                usuario.setSenha(cs.criptografarSenha("aiwansxv90*"));
                usuarioController.salvar(usuario);
                System.out.print("-------------------------------- Fim Registrando usuário administrador ---------------------------------------");

            }
            System.out.print("-------------------------------- Inicio Inclui Permissões ---------------------------------------");

            for (Tarefa tr : listaTarefas) {
                Permissao pr = new Permissao();
                pr.setUsuario(usuario);
                pr.setTarefa(tr);
                pr.setConsultar(true);
                pr.setEditar(true);
                pr.setExcluir(true);
                pr.setIncluir(true);
                permissaoController.atualizar(pr);
            }
            System.out.print("-------------------------------- Fim Inclui Permissões ---------------------------------------");

        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("usuarioadmin_falha", MenssagemUtil.MENSAGENS));
            Logger.getLogger(ConfiguracaoSistemaMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getNomeSistema() {
        return nomeSistema;
    }

    public void setNomeSistema(String nomeSistema) {
        this.nomeSistema = nomeSistema;
    }

    
}
