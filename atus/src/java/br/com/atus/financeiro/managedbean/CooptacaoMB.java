/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.financeiro.managedbean;

import br.com.atus.financeiro.controller.CooptacaoController;
import br.com.atus.financeiro.modelo.Cooptacao;
import br.com.atus.util.MenssagemUtil;
import br.com.atus.util.managedbean.BeanGenerico;
import br.com.atus.util.managedbean.NavegacaoMB;
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
public class CooptacaoMB extends BeanGenerico<Cooptacao> implements Serializable {

    @Inject
    private NavegacaoMB navegacaoMB;
    @EJB
    private CooptacaoController controller;
    private List<Cooptacao> listaDeCooptacaos;
    private Cooptacao cooptacao;

    @PostConstruct
    public void init() {
        cooptacao = (Cooptacao) navegacaoMB.getRegistroMapa("cooptacao", new Cooptacao());
        listaDeCooptacaos = new ArrayList<>();
    }

    public void salvar() {
        try {
            controller.criticarValorDasPorcentagens(cooptacao);
            controller.atualizar(cooptacao);
            init();
            MenssagemUtil.addMessageInfo(NavegacaoMB.getMsg("salvar", MenssagemUtil.MENSAGENS));

        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("falha", MenssagemUtil.MENSAGENS).concat(" "+ex.getMessage()), ex, "Cooptação");
            Logger.getLogger(CooptacaoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void consultarTodos(){
        try {
            listaDeCooptacaos = controller.consultarLike(getCampoBusca(), getValorBusca());
        } catch (Exception ex) {
            Logger.getLogger(CooptacaoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void excluir(Cooptacao c){
        try {
            cooptacao  = controller.gerenciar(c.getId());
            controller.excluir(cooptacao);
            listaDeCooptacaos.remove(cooptacao);
        } catch (Exception ex) {
            Logger.getLogger(CooptacaoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public CooptacaoMB() {
        super(Cooptacao.class);
    }

    public List<Cooptacao> getListaDeCooptacaos() {
        return listaDeCooptacaos;
    }

    public Cooptacao getCooptacao() {
        return cooptacao;
    }

    public void setCooptacao(Cooptacao cooptacao) {
        this.cooptacao = cooptacao;
    }

}
