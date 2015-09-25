/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.cadastro.managedbean;

import br.com.atus.cadastro.controller.RamoJudiciarioController;
import br.com.atus.util.managedbean.BeanGenerico;
import br.com.atus.util.managedbean.NavegacaoMB;
import br.com.atus.cadastro.modelo.RamoJudiciario;
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
 * @author Ari
 */
@ManagedBean
@ViewScoped
public class RamoJudiciarioMB extends BeanGenerico<RamoJudiciario> implements Serializable {

    @Inject
    private NavegacaoMB navegacaoMB;
    @EJB
    private RamoJudiciarioController controller;
    private RamoJudiciario ramoJudiciario;
    private List<RamoJudiciario> listaRamoJudiciario;

    @PostConstruct
    public void init() {
        listaRamoJudiciario = new ArrayList<>();
        ramoJudiciario = (RamoJudiciario) navegacaoMB.getRegistroMapa("ramo_judiciario", new RamoJudiciario());
    }

    public void salvar() {
        try {
            controller.atualizar(ramoJudiciario);
            init();
            MenssagemUtil.addMessageInfo(NavegacaoMB.getMsg("salvar", MenssagemUtil.MENSAGENS));
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("falha", MenssagemUtil.MENSAGENS), ex, "RamoJudiciario");
            Logger.getLogger(RamoJudiciarioMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void listar() {
        try {
            if (getValorBusca() == null || getValorBusca().equals("")) {
                listaRamoJudiciario = controller.consultarTodos("nome");
            } else {
                listaRamoJudiciario = controller.consultarLike(getCampoBusca(), getValorBusca());
                MenssagemUtil.addMessageWarn(NavegacaoMB.getMsg("consulta.vazia", MenssagemUtil.MENSAGENS));

            }
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("consulta.vazia", MenssagemUtil.MENSAGENS));
            Logger.getLogger(EspecieEventoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public RamoJudiciario getRamoJudiciario() {
        return ramoJudiciario;
    }

    public void setRamoJudiciario(RamoJudiciario ramoJudiciario) {
        this.ramoJudiciario = ramoJudiciario;
    }

    public List<RamoJudiciario> getListaRamoJudiciario() {
        return listaRamoJudiciario;
    }

    public void setListaRamoJudiciario(List<RamoJudiciario> listaRamoJudiciario) {
        this.listaRamoJudiciario = listaRamoJudiciario;
    }

    public RamoJudiciarioMB() {
        super(RamoJudiciario.class);
    }

}
