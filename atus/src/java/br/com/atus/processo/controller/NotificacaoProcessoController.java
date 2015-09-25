/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.processo.controller;

import br.com.atus.processo.dao.NotificacaoProcessoDAO;
import br.com.atus.interfaces.Controller;
import br.com.atus.processo.modelo.NotificacaoProcesso;
import br.com.atus.processo.modelo.Processo;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.primefaces.event.FileUploadEvent;

/**
 *
 * @author ari
 */
@Stateless
public class NotificacaoProcessoController extends Controller<NotificacaoProcesso, Long> implements Serializable {

    @Inject
    private NotificacaoProcessoDAO dao;

    @PostConstruct
    @Override
    protected void inicializaDAO() {
        setDAO(dao);
    }

    public List<NotificacaoProcesso> consultarPor(Processo processo) {
        return dao.consultarPor(processo);
    }
    public NotificacaoProcesso consultarPor(Long processo) {
        return dao.consultarPor(processo);
    }

    public void addNotificacao(NotificacaoProcesso notificacaoProcesso) throws Exception {
//        byte[] bimagem = event.getFile().getContents();
        notificacaoProcesso.setNome("ww");
//        notificacaoProcesso.setArquivo(bimagem);
        dao.salvar(notificacaoProcesso);
    }

}
