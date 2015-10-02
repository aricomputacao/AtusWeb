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
import br.com.atus.util.UploadArquivoUtil;
import br.com.atus.util.peca.ArquivoUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

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
        notificacaoProcesso = dao.atualizarGerenciar(notificacaoProcesso);

        String pasta = notificacaoProcesso.getProcesso().getId().toString();
        String nomeArquivo = notificacaoProcesso.getId().toString().concat(" - ").concat(notificacaoProcesso.getNome()) + ".pdf";

        notificacaoProcesso = dao.atualizarGerenciar(notificacaoProcesso);
        notificacaoProcesso.setCaminho(UploadArquivoUtil.caminhoDoArquivo(pasta, nomeArquivo));
        UploadArquivoUtil.gravaArquivoNoticacao(pasta, nomeArquivo, notificacaoProcesso.getArquivo());;
    }

    public void addNotificacao(NotificacaoProcesso notificacaoProcesso, UploadedFile arquivoUpload) throws Exception {
        byte[] bs;
        bs = arquivoUpload.getContents();
        notificacaoProcesso.setArquivo(bs);
        notificacaoProcesso.setDataRegistro(new Date());
        notificacaoProcesso = dao.atualizarGerenciar(notificacaoProcesso);
        
        String pasta = notificacaoProcesso.getProcesso().getId().toString();
        String arquivo = notificacaoProcesso.getId().toString().concat(" - ").concat(notificacaoProcesso.getNome()) + ".pdf";
        
        UploadArquivoUtil.gravaArquivoNoticacao(pasta,arquivo, notificacaoProcesso.getArquivo());
    }

    public StreamedContent donwloadArquivo(NotificacaoProcesso np) throws FileNotFoundException, Exception {
        StreamedContent file;
        String arquivo = UploadArquivoUtil.checarExistenciaDoArquivoNaPasta(np);
        FileInputStream stream = new FileInputStream(arquivo);
        file = new DefaultStreamedContent(stream, "application/pdf", "teste.pdf");
        return file;
    }

}
