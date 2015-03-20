/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.managedbean;

import br.com.atus.controller.GrupoPecaController;
import br.com.atus.modelo.GrupoPeca;
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
public class GrupoPecaMB extends BeanGenerico<GrupoPeca> implements Serializable {

    @EJB
    private GrupoPecaController grupoPecaController;
    @Inject
    private NavegacaoMB navegacaoMB;
    private GrupoPeca grupoPeca;
    private List<GrupoPeca> listaGrupoPecas;

    public GrupoPecaMB() {
        super(GrupoPeca.class);
    }

    @PostConstruct
    public void init() {
        grupoPeca = (GrupoPeca) navegacaoMB.getRegistroMapa("grupo_peca", new GrupoPeca());
        listaGrupoPecas = new ArrayList<>();
    }

    public void salvar() {
        try {
            grupoPecaController.atualizar(grupoPeca);
            init();
            MenssagemUtil.addMessageInfo(NavegacaoMB.getMsg("salvar", MenssagemUtil.MENSAGENS));
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("falha", MenssagemUtil.MENSAGENS));
            Logger.getLogger(GrupoPecaMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void consultaTodos() {
        try {
            listaGrupoPecas = grupoPecaController.listarTodos("nome");
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("consulta_erro", MenssagemUtil.MENSAGENS));
            Logger.getLogger(GrupoPecaMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void excluir(GrupoPeca gp) {
        try {
            gp = grupoPecaController.gerenciar(gp.getId());
            grupoPecaController.excluir(gp);
            listaGrupoPecas.remove(gp);
            MenssagemUtil.addMessageInfo(NavegacaoMB.getMsg("excluir", MenssagemUtil.MENSAGENS));
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("excluir_falha", MenssagemUtil.MENSAGENS));
            Logger.getLogger(GrupoPecaMB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public GrupoPeca getGrupoPeca() {
        return grupoPeca;
    }

    public void setGrupoPeca(GrupoPeca grupoPeca) {
        this.grupoPeca = grupoPeca;
    }

    public List<GrupoPeca> getListaGrupoPecas() {
        return listaGrupoPecas;
    }

    public void setListaGrupoPecas(List<GrupoPeca> listaGrupoPecas) {
        this.listaGrupoPecas = listaGrupoPecas;
    }

}
