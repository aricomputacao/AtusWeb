/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.financeiro.managedbean;

import br.com.atus.cadastro.controller.AdvogadoController;
import br.com.atus.cadastro.controller.ColaboradorController;
import br.com.atus.financeiro.controller.ContaReceberController;
import br.com.atus.financeiro.controller.CooptacaoController;
import br.com.atus.financeiro.modelo.ContaReceber;
import br.com.atus.financeiro.modelo.Cooptacao;
import br.com.atus.modelo.Advogado;
import br.com.atus.modelo.Colaborador;
import br.com.atus.modelo.Processo;
import br.com.atus.util.MenssagemUtil;
import br.com.atus.util.managedbean.BeanGenerico;
import br.com.atus.util.managedbean.NavegacaoMB;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
public class ContaReceberMB extends BeanGenerico<ContaReceber> implements Serializable {
    
    @Inject
    private NavegacaoMB navegacaoMB;
    @EJB
    private ContaReceberController controller;
    @EJB
    private AdvogadoController advogadoController;
    @EJB
    private CooptacaoController cooptacaoController;
    @EJB
    private ColaboradorController colaboradorController;
    private List<ContaReceber> listaContaReceber;
    private ContaReceber contaReceber;
    private List<Advogado> listaDeAdvogados;
    private List<Processo> listaDeProcessos;
    private List<Colaborador> listaDeColaboradores;
    private List<Cooptacao> listaDeCooptacao;
    
    @PostConstruct
    public void init() {
        try {
            contaReceber = (ContaReceber) navegacaoMB.getRegistroMapa("conta_receber", new ContaReceber());
            listaContaReceber = new ArrayList<>();
            listaDeAdvogados = advogadoController.consultarTodos("nome");
            listaDeCooptacao = cooptacaoController.consultarTodos("nome");
            listaDeProcessos = new ArrayList<>();
            listaDeColaboradores = colaboradorController.consultarTodos("nome");
            if (contaReceber.getId() != null) {
                contaReceber.setDataCadastro(new Date());
                contaReceber.setQuantidadeParcelas(1);
            }
        } catch (Exception ex) {
            Logger.getLogger(ContaReceberMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void salvar() {
        try {
            controller.salvar(contaReceber);
            init();
            MenssagemUtil.addMessageInfo(NavegacaoMB.getMsg("salvar", MenssagemUtil.MENSAGENS));
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("falha", MenssagemUtil.MENSAGENS));
            Logger.getLogger(ContaReceberMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ContaReceberMB() {
        super(ContaReceber.class);
    }
    
    public List<ContaReceber> getListaContaReceber() {
        return listaContaReceber;
    }
    
    public void setListaContaReceber(List<ContaReceber> listaContaReceber) {
        this.listaContaReceber = listaContaReceber;
    }
    
    public List<Advogado> getListaDeAdvogados() {
        return listaDeAdvogados;
    }
    
    public void setListaDeAdvogados(List<Advogado> listaDeAdvogados) {
        this.listaDeAdvogados = listaDeAdvogados;
    }
    
    public List<Processo> getListaDeProcessos() {
        return listaDeProcessos;
    }
    
    public void setListaDeProcessos(List<Processo> listaDeProcessos) {
        this.listaDeProcessos = listaDeProcessos;
    }
    
    public List<Colaborador> getListaDeColaboradores() {
        return listaDeColaboradores;
    }
    
    public void setListaDeColaboradores(List<Colaborador> listaDeColaboradores) {
        this.listaDeColaboradores = listaDeColaboradores;
    }
    
    public ContaReceber getContaReceber() {
        return contaReceber;
    }
    
    public void setContaReceber(ContaReceber contaReceber) {
        this.contaReceber = contaReceber;
    }
    
    public List<Cooptacao> getListaDeCooptacao() {
        return listaDeCooptacao;
    }
    
}
