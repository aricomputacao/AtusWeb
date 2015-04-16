/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.managedbean;

import br.com.atus.controller.OrgaoController;
import br.com.atus.modelo.Orgao;
import br.com.atus.util.AssistentedeRelatorio;
import br.com.atus.util.MenssagemUtil;
import br.com.atus.util.RelatorioSession;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class OrgaoMB extends BeanGenerico<Orgao> implements Serializable {

    @Inject
    private NavegacaoMB navegacaoMB;
    @EJB
    private OrgaoController controller;
    private List<Orgao> listaOrgaos;
    private Orgao orgao;

    public OrgaoMB() {
        super(Orgao.class);
    }

    @PostConstruct
    public void init() {
        orgao = (Orgao) navegacaoMB.getRegistroMapa("orgao", new Orgao());
        listaOrgaos = new ArrayList<>();
    }

    public void salvar() {
        try {
            controller.salvarouAtualizar(orgao);
            init();
            MenssagemUtil.addMessageInfo(NavegacaoMB.getMsg("salvar", MenssagemUtil.MENSAGENS));
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("falha", MenssagemUtil.MENSAGENS));
            Logger.getLogger(OrgaoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void listar() {
        try {
            if (getValorBusca() == null || getValorBusca().equals("")) {
                listaOrgaos = controller.consultarTodos("nome");
            } else {
                listaOrgaos = controller.consultarLike(getCampoBusca(), getValorBusca());
                MenssagemUtil.addMessageWarn(NavegacaoMB.getMsg("consulta.vazia", MenssagemUtil.MENSAGENS));

            }
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("consulta.vazia", MenssagemUtil.MENSAGENS));
            Logger.getLogger(OrgaoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void excluir(Orgao ee) {
        try {
            ee = controller.gerenciar(ee.getId());
            controller.excluir(ee);
            listaOrgaos.remove(ee);
            MenssagemUtil.addMessageInfo(NavegacaoMB.getMsg("excluir", MenssagemUtil.MENSAGENS));

        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("excluir.falha", MenssagemUtil.MENSAGENS));
            Logger.getLogger(OrgaoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void imprimir() {
        if (!listaOrgaos.isEmpty()) {
            Map<String, Object> m = new HashMap<>();
            byte[] rel = new AssistentedeRelatorio().relatorioemByte(listaOrgaos, m, "WEB-INF/relatorios/rel_orgao.jasper", "Relatório de Ógãos");
            RelatorioSession.setBytesRelatorioInSession(rel);
        }

    }

    public List<Orgao> getListaOrgaos() {
        return listaOrgaos;
    }

    public void setListaOrgaos(List<Orgao> listaOrgaos) {
        this.listaOrgaos = listaOrgaos;
    }

    public Orgao getOrgao() {
        return orgao;
    }

    public void setOrgao(Orgao orgao) {
        this.orgao = orgao;
    }

}
