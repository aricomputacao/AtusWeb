/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.controller;

import br.com.atus.dao.PecaDAO;
import br.com.atus.modelo.Peca;
import br.com.atus.util.ArquivoUtil;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.ejb.Stateless;
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
    protected void inicializaDAO() {
        setDAO(dao);
    }

    public void salvar(Peca t, UploadedFile file) throws Exception {
        t.setArquivo(file.getFileName());
        super.salvar(t);
        // Salvar o aquivo aqui
        ArquivoUtil.gravaArquivo("pecas/documentos", file.getFileName(), file.getContents());
    }

}
