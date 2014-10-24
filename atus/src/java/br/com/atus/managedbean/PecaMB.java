/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.managedbean;

import br.com.atus.controller.GrupoPecaController;
import br.com.atus.controller.PecaController;
import br.com.atus.controller.SubGrupoPecaController;
import br.com.atus.exceptions.PecaFileException;
import br.com.atus.modelo.GrupoPeca;
import br.com.atus.modelo.Peca;
import br.com.atus.modelo.Processo;
import br.com.atus.modelo.SubGrupoPeca;
import br.com.atus.util.MenssagemUtil;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author gilmario
 */
@ManagedBean
@ViewScoped
public class PecaMB extends BeanGenerico<Peca> implements Serializable {

    @EJB
    private PecaController controller;
    @EJB
    private SubGrupoPecaController subGrupoPecaController;
    @EJB
    private GrupoPecaController grupoPecaController;
    private UploadedFile file;
    private Peca peca;
    private GrupoPeca grupo;
    @Inject
    private NavegacaoMB navegacaoMB;
    private List<Peca> listaPecas;
    private List<SubGrupoPeca> listaSubGrupoPecas;
    private List<GrupoPeca> listaGrupoPecas;

    @PostConstruct
    public void init() {
        listaPecas = new ArrayList<>();
        peca = (Peca) navegacaoMB.getRegistroMapa("peca", new Peca());
        listaSubGrupoPecas = new ArrayList<>();
        file = null;
        try {
            listaGrupoPecas = grupoPecaController.listarTodos("nome");
        } catch (Exception ex) {
            listaGrupoPecas = new ArrayList<>();
        }
    }

    public PecaMB() {
        super(Peca.class);
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public Peca getPeca() {
        return peca;
    }

    public void setPeca(Peca peca) {
        this.peca = peca;
    }

    public List<Peca> getListaPecas() {
        return listaPecas;
    }

    public void setListaPecas(List<Peca> listaPecas) {
        this.listaPecas = listaPecas;
    }

    public void excluirArquivo() {
        this.file = null;
    }

    public void listar() {
        try {
            listaPecas = controller.listarLike(getCampoBusca(), getValorBusca());
            if (listaPecas.isEmpty()) {
                MenssagemUtil.addMessageInfo("Nenhum resultado encontrado");
            }
        } catch (Exception e) {
            MenssagemUtil.addMessageErro(e);
        }
    }

    public void atualizaListaSubGrupos() {
        if (grupo != null) {
            listaSubGrupoPecas = subGrupoPecaController.listar(grupo);
        } else {
            listaSubGrupoPecas = new ArrayList<>();
        }
    }

    public void excluir(Peca p) {

    }

    public void imprimir() {

    }

    public StreamedContent download(Peca p) {
        try {
            return controller.getDownload(p);
        } catch (PecaFileException | FileNotFoundException e) {
            MenssagemUtil.addMessageErro(e);
        }
        return null;
    }

    public StreamedContent downloadModelo(Peca p) {
        try {
            return controller.getModeloDownload(p, p);
        } catch (PecaFileException | FileNotFoundException | Docx4JException | IllegalArgumentException | IllegalAccessException | InvocationTargetException | NoSuchFieldException | ClassNotFoundException e) {
            MenssagemUtil.addMessageErro(e);
        }
        return null;
    }

    public void salvar() {
        try {
            if (peca.getId() == null) {

                controller.salvar(peca, file);
            } else {
                controller.atualizar(peca, file);
            }
            init();
            MenssagemUtil.addMessageInfo("Registro salvo");
        } catch (Exception e) {
            MenssagemUtil.addMessageErro("Erro ao salvar.");
        }
    }

    public void uploadArquivo(FileUploadEvent event) {
        try {
            if (controller.validaArquivoDocx(event.getFile(), Processo.class.getName())) {
                file = event.getFile();
                MenssagemUtil.addMessageInfo("Arquivo enviado com sucesso!" + file.getFileName());
            } else {
                MenssagemUtil.addMessageWarn("Arquivo Invalido. Possue tags Invalidas.");
            }

        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao fazer upload do arquivo", ex, this.getClass().getName());
        }
    }

    public List<String> getCamposClasse() {
        try {
            return controller.getListaTags(Processo.class.getName());
        } catch (ClassNotFoundException | NoSuchFieldException ex) {
            return new ArrayList<>();
        }
    }

    public List<SubGrupoPeca> getListaSubGrupoPecas() {
        return listaSubGrupoPecas;
    }

    public void setListaSubGrupoPecas(List<SubGrupoPeca> listaSubGrupoPecas) {
        this.listaSubGrupoPecas = listaSubGrupoPecas;
    }

    public List<GrupoPeca> getListaGrupoPecas() {
        return listaGrupoPecas;
    }

    public void setListaGrupoPecas(List<GrupoPeca> listaGrupoPecas) {
        this.listaGrupoPecas = listaGrupoPecas;
    }

    public GrupoPeca getGrupo() {
        return grupo;
    }

    public void setGrupo(GrupoPeca grupo) {
        this.grupo = grupo;
    }

}
