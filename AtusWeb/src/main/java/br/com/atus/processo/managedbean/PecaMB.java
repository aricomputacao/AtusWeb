/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.processo.managedbean;

import br.com.atus.cadastro.controller.GrupoPecaController;
import br.com.atus.processo.controller.PecaController;
import br.com.atus.cadastro.controller.SubGrupoPecaController;
import br.com.atus.util.exceptions.PecaFileException;
import br.com.atus.util.managedbean.BeanGenerico;
import br.com.atus.util.managedbean.NavegacaoMB;
import br.com.atus.processo.modelo.GrupoPeca;
import br.com.atus.cadastro.modelo.Peca;
import br.com.atus.processo.modelo.Processo;
import br.com.atus.processo.modelo.SubGrupoPeca;
import br.com.atus.util.MenssagemUtil;
import br.com.atus.util.peca.CampoPersonalizado;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.Text;
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
    private List<Text> textos;
    private List<Text> itensSel;
    private Text tag;
    private WordprocessingMLPackage template;

    public PecaMB() {
        super(Peca.class);
    }

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
            listaGrupoPecas = grupoPecaController.consultarTodos("nome");
        } catch (Exception ex) {
            listaGrupoPecas = new ArrayList<>();
        }

    }

    public void apagarListaDeTextos() {
        for (int i = 0; i < itensSel.size(); i++) {
            textos.remove(i);
            itensSel.remove(i);
            i = 0;
        }

        itensSel.clear();

    }

    public void apagarTexto(Text t) {
        tag = t;
        tag.setValue("");
        textos.remove(tag);
    }

    public void corrigirTexto(Text t) {
        tag = new Text();
        tag.setValue(t.getValue().replace("{", "").replace("}", "").replace("$", "").trim());
        String prefixo = "${";
        String sufixo = "}";
        tag.setValue(prefixo.concat(tag.getValue()).concat(sufixo).trim());
        t.setValue(tag.getValue());

    }

    public void uploadArquivo(FileUploadEvent event) {
        try {

            file = event.getFile();
            template = controller.getTemplate(file);

            textos = (List<Text>) controller.getPartes(template);

            for (int i = 0; i < textos.size(); i++) {
                if (textos.get(i).getValue().trim().equals("$") || textos.get(i).getValue().trim().equals("{") || textos.get(i).getValue().trim().equals("}")) {
                    textos.get(i).setValue("");
                    textos.remove(i);
                    i = 0;
                }
            }

            MenssagemUtil.addMessageInfo("Arquivo enviado com sucesso!" + file.getFileName());
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao fazer upload do arquivo", ex, this.getClass().getName());
        }
    }

    public void excluirArquivo() {
        this.file = null;
    }

    public void listar() {
        try {
            listaPecas = controller.consultarLike(getCampoBusca(), getValorBusca());
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
        try {
            controller.excluir(p);
            listar();
            MenssagemUtil.addMessageInfo("Peça excluída com sucesso.");
        } catch (Exception ex) {
            Logger.getLogger(PecaMB.class.getName()).log(Level.SEVERE, null, ex);
            MenssagemUtil.addMessageErro(ex);
        }
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
                controller.salvar(peca, file.getFileName(), template);
            } else {
                controller.atualizar(peca, file.getFileName(), template);
            }
            init();
            MenssagemUtil.addMessageInfo("Registro salvo");
        } catch (Exception e) {
            MenssagemUtil.addMessageErro("Erro ao salvar.");
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
                    } else if (cc.contains("clie")) {
                        lisCliente.add(c);
                    } else {
                        lisProcesso.add(c);
                    }
                } else {
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

    public List<Text> getTextos() {
        return textos;
    }

    public void setTextos(List<Text> textos) {
        this.textos = textos;
    }

    public Text getTag() {
        return tag;
    }

    public void setTag(Text tag) {
        this.tag = tag;
    }

    public List<Text> getItensSel() {
        return itensSel;
    }

    public void setItensSel(List<Text> itensSel) {
        this.itensSel = itensSel;
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

}
