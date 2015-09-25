/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.cadastro.managedbean;

import br.com.atus.cadastro.controller.TipoTratamentoController;
import br.com.atus.util.managedbean.BeanGenerico;
import br.com.atus.util.managedbean.NavegacaoMB;
import br.com.atus.cadastro.modelo.TipoTratamento;
import br.com.atus.util.MenssagemUtil;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author ari
 */
@ManagedBean
@ViewScoped
public class TipoTratamentoMB extends BeanGenerico<TipoTratamento> implements Serializable {

    
    @EJB
    private TipoTratamentoController controller;
    private TipoTratamento tipoTratamento;
    private List<TipoTratamento> listaTipoTratamentos;

    public TipoTratamentoMB() {
        super(TipoTratamento.class);
    }

    @PostConstruct
    public void init() {
        try {
            tipoTratamento = new TipoTratamento();
            listaTipoTratamentos = controller.consultarTodos("nome");
        } catch (Exception ex) {
            Logger.getLogger(TipoTratamentoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void salvar() {
        try {
            controller.salvarouAtualizar(tipoTratamento);
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
                listaTipoTratamentos = controller.consultarTodos("nome");
            } else {
                listaTipoTratamentos = controller.consultarLike("nome", getValorBusca());
                MenssagemUtil.addMessageWarn(NavegacaoMB.getMsg("consulta.vazia", MenssagemUtil.MENSAGENS));

            }
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("consulta.vazia", MenssagemUtil.MENSAGENS));
            Logger.getLogger(EspecieEventoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public TipoTratamento getTipoTratamento() {
        return tipoTratamento;
    }

    public void setTipoTratamento(TipoTratamento tipoTratamento) {
        this.tipoTratamento = tipoTratamento;
    }

    public List<TipoTratamento> getListaTipoTratamentos() {
        return listaTipoTratamentos;
    }

    public void setListaTipoTratamentos(List<TipoTratamento> listaTipoTratamentos) {
        this.listaTipoTratamentos = listaTipoTratamentos;
    }

}
