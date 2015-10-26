/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.chat.controller;


import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 *
 * @author ari
 */
public class SessionListener implements HttpSessionListener {
//
//    @Inject
//    private ChatMb chatMb;

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        synchronized (this) {
            System.out.println("-----------------criado sessão-----------   " + "  " + event.getSession().getId());
        }

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        synchronized (this) {
//            System.out.println(event.getSession().getAttribute("usr"));
//            if (chatMb.getListaDeusuariosLogado().contains(event.getSession().getAttribute("usr").toString())) {
//                chatMb.removerUsuarioLogado(event.getSession().getAttribute("usr").toString());
//            }
        }
    }
}
