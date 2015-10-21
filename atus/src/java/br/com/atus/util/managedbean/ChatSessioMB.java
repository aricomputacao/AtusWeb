/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.util.managedbean;

import br.com.chat.managedbean.GerenciadorChat;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author ari
 */
@Named
@SessionScoped
public class ChatSessioMB extends GerenciadorChat implements Serializable {

//    @Inject
//    private NavegacaoMB navegacaoMB;
    private List<String> listaDeUsuariosLogados;
//    private String usuarioDaSessao;

    /**
     *
     */
    @PostConstruct
    public void iniciar() {    
        
        setUsuarioLogado(contexto().getRemoteUser());
        logar();
        listaDeUsuariosLogados = getListaDeUsuariosLogado(contexto().getRemoteUser());

    }

    public void adicionarChat(String dest) {
        getLerMsg().clear();
        if (mensagensParaUsuarioLogado(dest)) {
            setarOrigemExterno(dest);
            contextoRequisicaoPrimefaces().execute("PF('chat_2').show();");
            contextoRequisicaoPrimefaces().update("chat_2");
        } else {
            addChat(dest);
            contextoRequisicaoPrimefaces().execute("PF('chat').show();");
            contextoRequisicaoPrimefaces().update("chat");
        }
    }

    public void atualizarListaDeUsuariosLogados() {
        listaDeUsuariosLogados = getListaDeUsuariosLogado(contexto().getRemoteUser());
    }

    @PreDestroy
    public void sair() {
        listaDeUsuariosLogados.remove(contexto().getRemoteUser());
        logout();
    }

    public List<String> getListaDeUsuariosLogados() {
        return listaDeUsuariosLogados;
    }

   
}
