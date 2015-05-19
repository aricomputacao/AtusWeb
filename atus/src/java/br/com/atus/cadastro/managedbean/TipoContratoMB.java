/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.cadastro.managedbean;

import br.com.atus.cadastro.controller.TipoContratoController;
import br.com.atus.util.managedbean.BeanGenerico;
import br.com.atus.util.managedbean.NavegacaoMB;
import br.com.atus.modelo.TipoContrato;
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
public class TipoContratoMB extends BeanGenerico<TipoContratoMB> implements Serializable {

    @Inject
    private NavegacaoMB navegacaoMB;
    @EJB
    private TipoContratoController controller;
    private List<TipoContrato> listaTipoContrados;
    private TipoContrato tipoContrado;

    public TipoContratoMB() {
        super(TipoContratoMB.class);
    }

    @PostConstruct
    public void init() {
        try {
            tipoContrado = (TipoContrato) navegacaoMB.getRegistroMapa("tipo_contrato", new TipoContrato());
            listaTipoContrados = controller.consultarTodos("nome");
        } catch (Exception ex) {
            Logger.getLogger(TipoContratoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void salvar() {
        try {
            controller.salvarouAtualizar(tipoContrado);
            init();
            MenssagemUtil.addMessageInfo(NavegacaoMB.getMsg("salvar", MenssagemUtil.MENSAGENS));
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("falha", MenssagemUtil.MENSAGENS), ex, "TipoContrato");
            Logger.getLogger(TipoContratoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void listar() {
        try {
            if (getValorBusca() == null || getValorBusca().equals("")) {
                listaTipoContrados = controller.consultarTodos("nome");
            } else {
                listaTipoContrados = controller.consultarLike("nome", getValorBusca());
                MenssagemUtil.addMessageWarn(NavegacaoMB.getMsg("consulta.vazia", MenssagemUtil.MENSAGENS));

            }
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("consulta.vazia", MenssagemUtil.MENSAGENS));
            Logger.getLogger(EspecieEventoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void excluir(TipoContrato ee) {
        try {
            ee = controller.gerenciar(ee.getId());
            controller.excluir(ee);
            listaTipoContrados.remove(ee);
            MenssagemUtil.addMessageInfo(NavegacaoMB.getMsg("excluir", MenssagemUtil.MENSAGENS));

        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("excluir.falha", MenssagemUtil.MENSAGENS));
            Logger.getLogger(EspecieEventoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void imprimir() {
        if (!listaTipoContrados.isEmpty()) {
            Map<String, Object> m = new HashMap<>();
            byte[] rel = new AssistentedeRelatorio().relatorioemByte(listaTipoContrados, m, "WEB-INF/relatorios/rel_tipo_contrato.jasper", "Relatório de Tipos de Contratos");
            RelatorioSession.setBytesRelatorioInSession(rel);
        }

    }

    public List<TipoContrato> getListaTipoContrados() {
        return listaTipoContrados;
    }

    public void setListaTipoContrados(List<TipoContrato> listaTipoContrados) {
        this.listaTipoContrados = listaTipoContrados;
    }

    public TipoContrato getTipoContrado() {
        return tipoContrado;
    }

    public void setTipoContrado(TipoContrato tipoContrado) {
        this.tipoContrado = tipoContrado;
    }

}
