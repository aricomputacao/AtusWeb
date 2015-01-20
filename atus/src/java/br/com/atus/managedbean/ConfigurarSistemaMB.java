/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.managedbean;

import br.com.atus.controller.ModuloController;
import br.com.atus.controller.PermissaoController;
import br.com.atus.controller.TarefaController;
import br.com.atus.controller.UsuarioController;
import br.com.atus.enumerated.Perfil;
import br.com.atus.modelo.Modulo;
import br.com.atus.modelo.Permissao;
import br.com.atus.modelo.Tarefa;
import br.com.atus.modelo.Usuario;
import br.com.atus.util.CriptografiaSenha;
import br.com.atus.util.MenssagemUtil;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

/**
 *
 * @author ari
 */
@Singleton
@Startup
public class ConfigurarSistemaMB implements Serializable {

    @EJB
    private ModuloController moduloController;
    @EJB
    private TarefaController tarefaController;
    @EJB
    private UsuarioController usuarioController;
    @EJB
    private PermissaoController permissaoController;
    private Usuario usuario;
    private List<Tarefa> listaTarefas;
    private List<Permissao> listaPermissoes;

    @Inject
    private void zerarPermissao() {
        try {
            System.out.print("-------------------------------- iniciando zerar ---------------------------------------");
            usuario = new Usuario();
            //Fazer o cadastro do suario administrador
            usuario = usuarioController.usuarioLogin("atus");
            permissaoController.zerarPermissaoInicial(usuario);
        } catch (Exception ex) {
            Logger.getLogger(ConfigurarSistemaMB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @PostConstruct
    public void init() {
        criarModulos();
        criarTarefa();

        try {
            CriptografiaSenha cs = new CriptografiaSenha();
            listaTarefas = tarefaController.listarTodos("id");

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
            Logger.getLogger(ConfigurarSistemaMB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void criarModulos() {
        ResourceBundle bundle = ResourceBundle.getBundle("modulos");
        Enumeration<String> modulos = bundle.getKeys();
        while (modulos.hasMoreElements()) {
            String string = modulos.nextElement();
            String nome = bundle.getString(string);
            try {
                if (!moduloController.existeModulo(nome)) {
                    Modulo m = new Modulo();
                    m.setMnemonico(string);
                    m.setNome(nome);
                    moduloController.salvar(m);
                }
            } catch (Exception ex) {
                Logger.getLogger(ConfigurarSistemaMB.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    private void criarTarefa() {
        ResourceBundle bundle = ResourceBundle.getBundle("navegacao");
        Enumeration<String> tarefas = bundle.getKeys();
        while (tarefas.hasMoreElements()) {
            try {
                String string = tarefas.nextElement();
                String nome = bundle.getString(string);
                String mod = string.substring(0, 3);

                Tarefa taf = new Tarefa();
                taf.setDescricao(nome.substring(4).replace("_", " ").toUpperCase());
                taf.setNome(nome.substring(4));
                switch (mod) {
                    case ("cad"):
                        taf.setModulo(moduloController.buscarUnique("01"));
                        break;
                    case ("pro"):
                        taf.setModulo(moduloController.buscarUnique("02"));
                        break;
                    case ("seg"):
                        taf.setModulo(moduloController.buscarUnique("03"));
                        break;
                    case ("rel"):
                        taf.setModulo(moduloController.buscarUnique("04"));
                        break;

                }
                if (!tarefaController.existeTarefa(taf)) {
                    tarefaController.salvar(taf);
                }
            } catch (Exception ex) {
                Logger.getLogger(ConfigurarSistemaMB.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

}
