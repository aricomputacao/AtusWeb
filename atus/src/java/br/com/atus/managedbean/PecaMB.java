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
import br.com.atus.util.peca.CampoPersonalizado;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
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

    @Inject
    private NavegacaoMB navegacaoMB;
    @EJB
    private PecaController controller;
    @EJB
    private SubGrupoPecaController subGrupoPecaController;
    @EJB
    private GrupoPecaController grupoPecaController;
    private UploadedFile file;
    private Peca peca;
    private GrupoPeca grupo;
    private SubGrupoPeca subGrupo;

    private List<Peca> listaPecas;
    private List<SubGrupoPeca> listaSubGrupoPecas;
    private List<GrupoPeca> listaGrupoPecas;
    private List<CampoPersonalizado> lisColaborador = new ArrayList<>();
    private List<CampoPersonalizado> lisProcesso = new ArrayList<>();
    private List<CampoPersonalizado> lisCliente = new ArrayList<>();

    @PostConstruct
    public void init() {
        listaPecas = new ArrayList<>();
        peca = (Peca) navegacaoMB.getRegistroMapa("peca", new Peca());
        listaSubGrupoPecas = new ArrayList<>();
        file = null;
        getCamposClasse();
        if (peca.getId() != null) {
            grupo = peca.getSubgrupo().getGrupoPeca();
            atualizaListaSubGrupos();
        }
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

    public void atualizaListaPecas() {
        if (subGrupo != null) {
            listaPecas = controller.listar(subGrupo);
        } else {
            listaPecas = new ArrayList<>();
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

    public void getCamposClasse() {
        try {
            List<CampoPersonalizado> listaCampoPersonalizados = controller.getListaTags(Processo.class.getName());
            lisCliente.clear();
            lisColaborador.clear();
            lisProcesso.clear();
            for (CampoPersonalizado c : listaCampoPersonalizados) {
                if (c.getNome().length() >= 4) {
                    String cc = c.getNome().substring(0, 4);
                    if (cc.contains("cola")) {
                        lisColaborador.add(c);
                    }
                    if (cc.contains("mate") || cc.contains("tipo") || cc.contains("fase") || cc.contains("valo") || cc.contains("part") || cc.contains("advo") || cc.contains("fato") || cc.contains("nbIn")
                            || cc.contains("ende") || cc.contains("data") || cc.contains("obje") || cc.contains("prov") || cc.contains("nbDe") || cc.contains("inca") || cc.contains("nume") || cc.contains("juiz")
                            || cc.contains("info") || cc.contains("depe")) {
                        lisProcesso.add(c);
                    }
                    if (cc.contains("clie")) {
                        lisCliente.add(c);
                    }
                }else{
                    lisProcesso.add(c);
                }

            }
//            return controller.getListaTags(Processo.class.getName());
        } catch (ClassNotFoundException | NoSuchFieldException ex) {
            MenssagemUtil.addMessageErro("Falha ao listar", ex, this.getClass().getName());

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

    public SubGrupoPeca getSubGrupo() {
        return subGrupo;
    }

    public void setSubGrupo(SubGrupoPeca subGrupo) {
        this.subGrupo = subGrupo;
    }

    public List<CampoPersonalizado> getLisColaborador() {
        return lisColaborador;
    }

    public void setLisColaborador(List<CampoPersonalizado> lisColaborador) {
        this.lisColaborador = lisColaborador;
    }

    public List<CampoPersonalizado> getLisProcesso() {
        return lisProcesso;
    }

    public void setLisProcesso(List<CampoPersonalizado> lisProcesso) {
        this.lisProcesso = lisProcesso;
    }

    public List<CampoPersonalizado> getLisCliente() {
        return lisCliente;
    }

    public void setLisCliente(List<CampoPersonalizado> lisCliente) {
        this.lisCliente = lisCliente;
    }

}
