/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.atus.cadastro.managedbean;

import br.com.atus.cadastro.controller.OrgaoEmissorController;
import br.com.atus.util.managedbean.BeanGenerico;
import br.com.atus.util.managedbean.NavegacaoMB;
import br.com.atus.cadastro.modelo.OrgaoEmissor;
import br.com.atus.util.MenssagemUtil;
import java.io.Serializable;
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
@ManagedBean
@ViewScoped
public class OrgaoEmissorMB extends BeanGenerico<OrgaoEmissor> implements Serializable{

    @Inject
    private NavegacaoMB navegacaoMB;
    @EJB
    private OrgaoEmissorController controller;
    private List<OrgaoEmissor> listaOrgaoEmissors;
    private OrgaoEmissor orgaoEmissor;
    
    public OrgaoEmissorMB() {
        super(OrgaoEmissor.class);
    }
    
    @PostConstruct
    public void init(){
        try {
            orgaoEmissor = (OrgaoEmissor) navegacaoMB.getRegistroMapa("orgao_emissor",new OrgaoEmissor());
            listaOrgaoEmissors = controller.consultarTodos("nome");
        } catch (Exception ex) {
            Logger.getLogger(OrgaoEmissorMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
       public void salvar() {
        try {
            controller.salvarouAtualizar(orgaoEmissor);
            init();
            MenssagemUtil.addMessageInfo(NavegacaoMB.getMsg("salvar", MenssagemUtil.MENSAGENS));
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("falha", MenssagemUtil.MENSAGENS));
            Logger.getLogger(OrgaoEmissorMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void listar() {
        try {
            if (getValorBusca() == null || getValorBusca().equals("")) {
                listaOrgaoEmissors = controller.consultarTodos("nome");
            } else {
                listaOrgaoEmissors = controller.consultarLike(getCampoBusca(), getValorBusca());
                MenssagemUtil.addMessageWarn(NavegacaoMB.getMsg("consulta.vazia", MenssagemUtil.MENSAGENS));

            }
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("consulta.vazia", MenssagemUtil.MENSAGENS));
            Logger.getLogger(OrgaoEmissorMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void excluir(OrgaoEmissor ee) {
        try {
            ee = controller.gerenciar(ee.getId());
            controller.excluir(ee);
            listaOrgaoEmissors.remove(ee);
            MenssagemUtil.addMessageInfo(NavegacaoMB.getMsg("excluir", MenssagemUtil.MENSAGENS));

        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("excluir.falha", MenssagemUtil.MENSAGENS));
            Logger.getLogger(OrgaoEmissorMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<OrgaoEmissor> getListaOrgaoEmissors() {
        return listaOrgaoEmissors;
    }

    public void setListaOrgaoEmissors(List<OrgaoEmissor> listaOrgaoEmissors) {
        this.listaOrgaoEmissors = listaOrgaoEmissors;
    }

    public OrgaoEmissor getOrgaoEmissor() {
        return orgaoEmissor;
    }

    public void setOrgaoEmissor(OrgaoEmissor orgaoEmissor) {
        this.orgaoEmissor = orgaoEmissor;
    }
    
}
