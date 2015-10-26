/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.chat.controller;

import br.com.atus.chat.modelo.Chat;
import br.com.atus.chat.modelo.Menssagem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author ari
 */
@Stateless
public class ChatController implements Serializable {

    public void addMsg(String origem, String destino, String msg, List<Chat> chats) {
        Chat ch = new Chat();
        ch.setOrigem(origem);
        ch.setDestino(destino);

        if (chats.contains(ch)) {
            int i = chats.indexOf(ch);
            ch = chats.get(i);
            ch.addMessage(msg, origem, destino);
            chats.get(i).marcarMsgComoLida(origem);
        }

    }

    public List<Menssagem> lerMsg(String origem, String destino, List<Chat> chats) {
        Chat ch = new Chat();
        ch.setOrigem(origem);
        ch.setDestino(destino);
        if (chats.contains(ch)) {
            int i = chats.indexOf(ch);
            return chats.get(i).getMenssagensNaoLidas(origem, destino);
        }
        return new ArrayList<>();

    }

    public List<Menssagem> lerMsgUsuarioLogado(String origem, String destino, String usuarioLogado, List<Chat> chats) {
        Chat ch = new Chat();
        ch.setOrigem(origem);
        ch.setDestino(destino);
        if (chats.contains(ch)) {
            int i = chats.indexOf(ch);
            return chats.get(i).getMenssagensNaoLidas(origem, destino);
        }
        return new ArrayList<>();

    }

    public void addMsgResposta(String usuarioLogado, String origemExterno, String msg, List<Chat> chats) {
        Chat ch = new Chat();
        ch.setOrigem(origemExterno);
        ch.setDestino(usuarioLogado);

        if (chats.contains(ch)) {
            int i = chats.indexOf(ch);
            ch = chats.get(i);
            ch.addMessage(msg, usuarioLogado, origemExterno);
            chats.get(i).marcarMsgComoLida(usuarioLogado);

        }
    }

    public int contadorDeMensagensParaUsuarioLogado(String usuarioLogado, List<Chat> chats) {
        int qtdDeMensagens = 0;
        for (Chat ch : chats) {
            if (ch.getDestino().equals(usuarioLogado) || ch.getOrigem().equals(usuarioLogado)) {
                List<Menssagem> naoLidas = ch.getMenssagensNaoLidas();
                if (!naoLidas.isEmpty()) {
                    for (Menssagem naoLida : naoLidas) {
                        if (naoLida.getDestino().equals(usuarioLogado)) {
                            qtdDeMensagens++;
                        }
                    }
                }
            }
        }
        return qtdDeMensagens;
    }

    public int mensagensParaUsuarioLogado(String origem, String usuarioLogado, List<Chat> chats) {
        int i = 0;
        for (Chat ch : chats) {
            if ((ch.getDestino().equals(usuarioLogado) && ch.getOrigem().equals(origem)) || (ch.getDestino().equals(origem) && ch.getOrigem().equals(usuarioLogado))) {
                List<Menssagem> naoLidas = ch.getMenssagensNaoLidas();
                if (!naoLidas.isEmpty()) {
                    for (Menssagem nl : naoLidas) {
                        if (nl.getDestino().equals(usuarioLogado)) {
                            i++;
                        }
                    }
                }
            }
        }
        return i;
    }
}
