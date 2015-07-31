/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.financeiro.managedbean;

import br.com.atus.financeiro.controller.CaixaColaboradorController;
import br.com.atus.financeiro.modelo.CaixaColaborador;
import br.com.atus.modelo.Colaborador;
import br.com.atus.util.MenssagemUtil;
import br.com.atus.util.managedbean.BeanGenerico;
import br.com.atus.util.managedbean.NavegacaoMB;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author ari
 */
@ManagedBean
@ViewScoped
public class CaixaColaboradorMB extends BeanGenerico<CaixaColaborador> implements Serializable {

    @Inject
    private NavegacaoMB navegacaoMB;
    @Inject
    private CaixaColaboradorController caixaColaboradorController;
    private List<CaixaColaborador> listaCaixaColaboradorEmAberto;
    private List<CaixaColaborador> listaCaixaColaboradorSelecionadas;
    private Colaborador colaborador;

    public CaixaColaboradorMB() {
        super(CaixaColaborador.class);
    }

    @PostConstruct
    public void init() {
        listaCaixaColaboradorEmAberto = new ArrayList<>();
        listaCaixaColaboradorSelecionadas = new ArrayList<>();
        colaborador = new Colaborador();
    }
    
    public void consultaPagamentosAbertos(){
        listaCaixaColaboradorEmAberto = caixaColaboradorController.consultaPagamentosAbertosDo(colaborador);
    }

    public void pagarColaborador() {
        try {
            caixaColaboradorController.pagarColaborador(listaCaixaColaboradorSelecionadas, navegacaoMB.getUsuarioLogado());
            MenssagemUtil.addMessageInfo(NavegacaoMB.getMsg("pagamento_sucesso", MenssagemUtil.MENSAGENS));

        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("pagamento_colaborador_falha", MenssagemUtil.MENSAGENS));
            Logger.getLogger(CaixaColaboradorMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<CaixaColaborador> getListaCaixaColaboradorEmAberto() {
        return listaCaixaColaboradorEmAberto;
    }

    public void setListaCaixaColaboradorEmAberto(List<CaixaColaborador> listaCaixaColaboradorEmAberto) {
        this.listaCaixaColaboradorEmAberto = listaCaixaColaboradorEmAberto;
    }

    public List<CaixaColaborador> getListaCaixaColaboradorSelecionadas() {
        return listaCaixaColaboradorSelecionadas;
    }

    public void setListaCaixaColaboradorSelecionadas(List<CaixaColaborador> listaCaixaColaboradorSelecionadas) {
        this.listaCaixaColaboradorSelecionadas = listaCaixaColaboradorSelecionadas;
    }

    public Colaborador getColaborador() {
        return colaborador;
    }

    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
    }

    
    
}
