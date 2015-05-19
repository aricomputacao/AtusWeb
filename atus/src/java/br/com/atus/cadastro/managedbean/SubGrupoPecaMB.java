/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.cadastro.managedbean;

import br.com.atus.cadastro.controller.GrupoPecaController;
import br.com.atus.cadastro.controller.SubGrupoPecaController;
import br.com.atus.util.managedbean.BeanGenerico;
import br.com.atus.util.managedbean.NavegacaoMB;
import br.com.atus.modelo.GrupoPeca;
import br.com.atus.modelo.SubGrupoPeca;
import br.com.atus.util.MenssagemUtil;
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

/**
 *
 * @author ari
 */
@ViewScoped
@ManagedBean
public class SubGrupoPecaMB extends BeanGenerico<SubGrupoPecaMB> implements Serializable {

    @EJB
    private SubGrupoPecaController controller;
    @EJB
    private GrupoPecaController grupoPecaController;
    @Inject
    private NavegacaoMB navegacaoMB;
    private SubGrupoPeca subGrupoPeca;
    private List<SubGrupoPeca> listaSubGrupoPecas;
    private List<GrupoPeca> listaGrupoPecas;

    public SubGrupoPecaMB() {
        super(SubGrupoPecaMB.class);
    }

    @PostConstruct
    public void init() {
        try {
            subGrupoPeca = (SubGrupoPeca) navegacaoMB.getRegistroMapa("sub_grupo_peca", new SubGrupoPeca());
            listaGrupoPecas = grupoPecaController.consultarTodos("nome");
            listaSubGrupoPecas = new ArrayList<>();
        } catch (Exception ex) {
            Logger.getLogger(SubGrupoPecaMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void salvar() {
        try {
            controller.atualizar(subGrupoPeca);
            init();
            MenssagemUtil.addMessageInfo(NavegacaoMB.getMsg("salvar", MenssagemUtil.MENSAGENS));
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("falha", MenssagemUtil.MENSAGENS));
            Logger.getLogger(SubGrupoPecaMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void consultaTodos() {
        try {
            listaSubGrupoPecas = controller.consultarTodos("nome");
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("consulta_erro", MenssagemUtil.MENSAGENS));
            Logger.getLogger(SubGrupoPecaMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void consulta() {
        try {            
            listaSubGrupoPecas = controller.connsultarPor(getCampoBusca(),getValorBusca());
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("consulta_erro", MenssagemUtil.MENSAGENS));
            Logger.getLogger(SubGrupoPecaMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void excluir(SubGrupoPeca gp) {
        try {
            gp = controller.gerenciar(gp.getId());
            controller.excluir(gp);
            listaSubGrupoPecas.remove(gp);
            MenssagemUtil.addMessageInfo(NavegacaoMB.getMsg("excluir", MenssagemUtil.MENSAGENS));
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("excluir_falha", MenssagemUtil.MENSAGENS));
            Logger.getLogger(SubGrupoPecaMB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public SubGrupoPeca getSubGrupoPeca() {
        return subGrupoPeca;
    }

    public void setSubGrupoPeca(SubGrupoPeca subGrupoPeca) {
        this.subGrupoPeca = subGrupoPeca;
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

}
