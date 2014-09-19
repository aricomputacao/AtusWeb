/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.controller;

import br.com.atus.dao.PecaDAO;
import br.com.atus.exceptions.PecaFileException;
import br.com.atus.modelo.Peca;
import br.com.atus.util.ArquivoUtil;
import br.com.atus.util.DocumentoConverter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author gilmario
 */
@Stateless
public class PecaController extends Controller<Peca, Long> implements Serializable {

    @EJB
    private PecaDAO dao;

    @Override
    @PostConstruct
    protected void inicializaDAO() {
        setDAO(dao);
    }

    public void salvar(Peca t, UploadedFile file) throws Exception {
        t.setArquivo(file.getFileName());
        super.salvar(t);
        ArquivoUtil.gravaArquivo(t.getId().toString(), file.getFileName(), file.getContents());
    }

    public StreamedContent getDownload(Peca p) throws PecaFileException, FileNotFoundException {
        List<File> files = ArquivoUtil.aquivos(p.getId().toString());
        if (files.isEmpty()) {
            throw new PecaFileException();
        }
        File f = files.get(0);
        return new DefaultStreamedContent(new FileInputStream(f), "docx", p.getArquivo());
    }

    public StreamedContent getModeloDownload(Peca peca, Object entidade) throws PecaFileException, FileNotFoundException, Docx4JException, IllegalArgumentException, IllegalAccessException {
        InputStream stream = getDownload(peca).getStream();
        return DocumentoConverter.converterArquivo(stream, entidade, peca);
    }

    public boolean validaArquivoDocx(UploadedFile f, String grupo) {
        try {
            return DocumentoConverter.validarArquivo(f.getInputstream(), Class.forName(grupo));
        } catch (IOException | ClassNotFoundException | Docx4JException ex) {
            Logger.getLogger(PecaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

}
