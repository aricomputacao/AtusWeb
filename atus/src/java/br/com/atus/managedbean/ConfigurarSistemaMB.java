/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.managedbean;

import br.com.atus.controller.ModuloController;
import br.com.atus.controller.TarefaController;
import br.com.atus.modelo.Modulo;
import br.com.atus.modelo.Tarefa;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

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

    @PostConstruct
    public void init() {
        criarModulos();
        criarTarefa();
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
