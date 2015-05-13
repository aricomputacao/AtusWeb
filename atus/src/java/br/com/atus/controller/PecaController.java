/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.controller;

import br.com.atua.interfaces.Controller;
import br.com.atus.dao.PecaDAO;
import br.com.atus.exceptions.PecaFileException;
import br.com.atus.modelo.Peca;
import br.com.atus.modelo.SubGrupoPeca;
import br.com.atus.util.peca.ArquivoUtil;
import br.com.atus.util.peca.CampoPersonalizado;
import br.com.atus.util.peca.DocumentoConverter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.apache.commons.fileupload.FileItem;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.Text;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.DefaultUploadedFile;
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

    public List<Peca> listar(SubGrupoPeca sgp) {
        return dao.listar(sgp);
    }

    @Override
    public void excluir(Peca t) throws Exception {
        ArquivoUtil.excluirAquivo(t.getId().toString(), "");
        t = carregar(t.getId());
        super.excluir(t);
    }

    public void salvar(Peca t, String fileName, WordprocessingMLPackage template) throws Exception {
        t.setArquivo(fileName);
        File f = new File("temp.docx");
        template.save(f);
        FileInputStream stream = new FileInputStream(f);
        byte[] byt = new byte[(int) f.length()];
        for (int i = 0; i < f.length(); i++) {
            byt[i] = (byte) stream.read();
        }
        super.salvar(t);
        ArquivoUtil.gravaArquivo(t.getId().toString(), fileName, byt);
    }

    public void atualizar(Peca t, String fileName, WordprocessingMLPackage template) throws Exception {
        t.setArquivo(fileName);
        File f = new File("temp.docx");
        template.save(f);
        FileInputStream stream = new FileInputStream(f);
        byte[] byt = new byte[(int) f.length()];
        for (int i = 0; i < f.length(); i++) {
            byt[i] = (byte) stream.read();
        }
        super.atualizar(t);
        ArquivoUtil.gravaArquivo(t.getId().toString(), fileName, byt);
        ArquivoUtil.gravaArquivo(t.getId().toString(), fileName, byt);
    }

    public StreamedContent getDownload(Peca p) throws PecaFileException, FileNotFoundException {
        List<File> files = ArquivoUtil.aquivos(p.getId().toString());
        if (files.isEmpty()) {
            throw new PecaFileException();
        }
        File f = files.get(0);
        return new DefaultStreamedContent(new FileInputStream(f), "docx", p.getArquivo());
    }

    public StreamedContent getModeloDownload(Peca peca, Object entidade) throws PecaFileException, FileNotFoundException, Docx4JException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchFieldException, ClassNotFoundException {
        InputStream stream = getDownload(peca).getStream();
        return DocumentoConverter.converterArquivo(stream, entidade, peca);
    }

    public List<CampoPersonalizado> getListaTags(String classe) throws ClassNotFoundException, NoSuchFieldException {
        return DocumentoConverter.getListaCampos(Class.forName(classe), "");
    }

    public List validaArquivoDocx(UploadedFile f, String grupo) throws NoSuchFieldException {
        try {
            try {
                return DocumentoConverter.getListPartesDocument(f.getInputstream());
            } catch (Docx4JException | FileNotFoundException ex) {
                Logger.getLogger(PecaController.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        } catch (IOException ex) {
            Logger.getLogger(PecaController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public List getPartes(WordprocessingMLPackage f) throws NoSuchFieldException {
        try {
            
            return DocumentoConverter.getAllElementFromObject(f.getMainDocumentPart(), Text.class);
        } catch (Exception ex) {
            Logger.getLogger(PecaController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public WordprocessingMLPackage getTemplate(UploadedFile f) throws NoSuchFieldException {
        try {
            try {
                return DocumentoConverter.getTemplate(f.getInputstream());
            } catch (Docx4JException | FileNotFoundException ex) {
                Logger.getLogger(PecaController.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        } catch (IOException ex) {
            Logger.getLogger(PecaController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
