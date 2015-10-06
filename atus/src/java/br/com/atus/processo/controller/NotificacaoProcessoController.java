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
import br.com.sisdelta.utilitarios.ManipuladorDeArquivo;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

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
        notificacaoProcesso.setDataRegistro(new Date());
        notificacaoProcesso = dao.atualizarGerenciar(notificacaoProcesso);

        if (notificacaoProcesso.getArquivo() != null) {

            String pasta = notificacaoProcesso.getProcesso().getId().toString();
            String nomeArquivo = notificacaoProcesso.getId().toString().concat(" - ").concat(notificacaoProcesso.getNome()) + ".pdf";

            notificacaoProcesso = dao.atualizarGerenciar(notificacaoProcesso);
            notificacaoProcesso.setCaminho(ManipuladorDeArquivo.caminhoDoArquivo(ManipuladorDeArquivo.PASTA_NOTIFICACOES,pasta, nomeArquivo));
            ManipuladorDeArquivo.gravaArquivo(ManipuladorDeArquivo.PASTA_NOTIFICACOES,pasta, nomeArquivo, notificacaoProcesso.getArquivo());
        }
    }

    public StreamedContent donwloadArquivo(NotificacaoProcesso np) throws FileNotFoundException, Exception {
        StreamedContent file;
        
        String pasta = np.getProcesso().getId().toString();
        String nomeArquivo = np.getId().toString().concat(" - ").concat(np.getNome()) + ".pdf";
        
        String arquivo = ManipuladorDeArquivo.checarExistenciaDoArquivoNaPasta(ManipuladorDeArquivo.PASTA_NOTIFICACOES,pasta, nomeArquivo, np.getArquivo());
        FileInputStream stream = new FileInputStream(arquivo);
        file = new DefaultStreamedContent(stream, "application/pdf", "teste.pdf");
        return file;
    }

}
