/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.chat.MB;

import br.com.atus.chat.controller.ChatController;
import br.com.atus.chat.modelo.Menssagem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.primefaces.context.RequestContext;

/**
 *
 * @author ari
 */
//@SessionScoped
//@Named
public abstract class GerenciadorChat implements Serializable {

    @Inject
    private ChatApplicationMb chatMb;
    @Inject
    private ChatController chatController;
    private String usuarioLogado;
    private String destino;
    private String origemExterno;
    private String msg;
    private List<Menssagem> lerMsg;
    private int qtdDeMensagens;

    @PostConstruct
    public void init() {
        lerMsg = new ArrayList<>();
        qtdDeMensagens = 0;

    }

    public void checarChegadaDeMensagens() {
        if (chatMb.getMap().containsKey(usuarioLogado)) {
            String[] fr = chatMb.getMap().remove(usuarioLogado).split(";");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(usuarioLogado, new FacesMessage(fr[0], fr[1]));

        }
        quantidadeDeMensagensParaUsuarioLogado();
    }

    public RequestContext contextoRequisicaoPrimefaces() {
        return RequestContext.getCurrentInstance();
    }

    public static ExternalContext contexto() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext external = context.getExternalContext();
        return external;
    }

    public int mensagensParaUsuarioLogado(String origem) {
        return chatController.mensagensParaUsuarioLogado(origem, usuarioLogado, chatMb.getChats());
    }

    private void quantidadeDeMensagensParaUsuarioLogado() {
        qtdDeMensagens = chatController.contadorDeMensagensParaUsuarioLogado(usuarioLogado, chatMb.getChats());
    }

    public void logar() {
        chatMb.addUsuarioLogado(usuarioLogado);
        contexto().getSessionMap().put("usr", usuarioLogado);
    }

    @PreDestroy
    public void logout() {
        contexto().invalidateSession();
    }

    public void addChat(String dest) {
        destino = dest;
        lerMsg.clear();
        chatMb.addChats(usuarioLogado, destino);
    }

    public void addMsg() {
        System.out.println("Enviando Mensagem para " + destino);
        chatMb.addMsg(usuarioLogado, destino, msg);
//        lerMensagem();
//        chatMb.getMap().put(destino, usuarioLogado + ";" + msg);
        msg = "";

    }

    public void addMsgResposta() {
        System.out.println("Enviando Mensagem " + origemExterno);
        chatMb.addMsgResposta(origemExterno, usuarioLogado, msg);
//        lerMensagem();
//        lerMensagemDoExterno();
//        chatMb.getMap().put(origemExterno, usuarioLogado + ";" + msg);
        msg = "";

    }

    public void lerMensagem() {
        lerMsg.removeAll(chatMb.lerMensagemUsuarioLogado(usuarioLogado, destino, usuarioLogado));
        lerMsg.addAll(chatMb.lerMensagemUsuarioLogado(usuarioLogado, destino, usuarioLogado));
        Collections.sort(lerMsg);

    }

    public void lerMensagemDoExterno() {
        lerMsg.removeAll(chatMb.lerMensagemUsuarioLogado(origemExterno, usuarioLogado, usuarioLogado));
        lerMsg.addAll(chatMb.lerMensagemUsuarioLogado(origemExterno, usuarioLogado, usuarioLogado));
        Collections.sort(lerMsg);

    }
    
    public boolean existeChat(String origem,String destino){
        return chatMb.existeChat(origem, destino);
    }

    public void setarOrigemExterno(String origem) {
        origemExterno = origem;
    }

    public void setUsuarioLogado(String usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Menssagem> getLerMsg() {
        return lerMsg;
    }

    public String getOrigemExterno() {
        return origemExterno;
    }

    public List<String> getListaDeUsuariosLogado(String usuarioLogado) {
        List<String> listApp = chatMb.getListaDeusuariosLogado();
        List<String> list = new ArrayList<>();
        for (String s : listApp) {
            if (!usuarioLogado.equals(s)) {
                list.add(s);
            }
        }
        return list;
    }

    public int getQtdDeMensagens() {
        return qtdDeMensagens;
    }

}
