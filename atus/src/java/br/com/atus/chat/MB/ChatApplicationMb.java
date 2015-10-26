/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.chat.MB;


import br.com.atus.chat.controller.ChatController;
import br.com.atus.chat.modelo.Chat;
import br.com.atus.chat.modelo.Menssagem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author ari
 */
@ApplicationScoped
@Named
public class ChatApplicationMb implements Serializable {

    @Inject
    private ChatController chatController;

    private List<Chat> chats = Collections.synchronizedList(new ArrayList<Chat>());

    private List<String> listaDeusuariosLogado;
    private Map<String, String> map = new HashMap<>();

    @PostConstruct
    public void init() {
        chats = new ArrayList<>();
        listaDeusuariosLogado = new ArrayList<>();
    }

    public void addUsuarioLogado(String login) {
        if (!listaDeusuariosLogado.contains(login)) {
            listaDeusuariosLogado.add(login);
        }
    }

    public void removerUsuarioLogado(String login) {
        listaDeusuariosLogado.remove(login);
    }

    public void addMsg(String origem, String destino, String msg) {
        chatController.addMsg(origem, destino, msg, chats);
    }

    public List<Menssagem> lerMensagem(String origem, String destino) {
        return chatController.lerMsg(origem, destino, chats);
    }
    public List<Menssagem> lerMensagemUsuarioLogado(String origem, String destino,String usuarioLogado) {
        return chatController.lerMsgUsuarioLogado(origem, destino, usuarioLogado,chats);
    }

    public void addChats(String origem, String destino) {
        Chat ch = new Chat();
        ch.setOrigem(origem);
        ch.setDestino(destino);

        if (!existeChat(origem, destino)) {
            chats.add(ch);
            System.out.println("-----------------criado chat okkk-------------------------------");
        }
    }

    public boolean existeChat(String origem, String destino) {
        Chat ch = new Chat();
        ch.setOrigem(origem);
        ch.setDestino(destino);

        return chats.contains(ch);
    }

//    public List<Menssagem> getMessages(String origem, String destino) {
//        List<Menssagem> result = null;
//        if (existeChat(origem, destino)) {
//            result = chatController.lerMsg(origem, destino, chats);
//        }
//        return result;
//    }

    public List<String> getListaDeusuariosLogado() {
        return Collections.unmodifiableList(listaDeusuariosLogado);
    }

    public void addMsgResposta(String origemExterno, String usuarioLogado, String msg) {
        chatController.addMsgResposta(usuarioLogado, origemExterno, msg, chats);
    }

    public List<Chat> getChats() {
        return Collections.unmodifiableList(chats);
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    
}
