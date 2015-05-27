/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.financeiro.managedbean;

import br.com.atus.cadastro.controller.AdvogadoController;
import br.com.atus.enumerated.Perfil;
import br.com.atus.financeiro.controller.ReciboController;
import br.com.atus.financeiro.modelo.Recibo;
import br.com.atus.modelo.Advogado;
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
public class ReciboMB implements Serializable {

    @Inject
    private NavegacaoMB navegacaoMB;
    @Inject
    private ReciboController reciboController;
    @Inject
    private AdvogadoController advogadoController;
    private Recibo recibo;
    private Advogado advogado;
    private List<Recibo> listaDeRecibos;
    private List<Advogado> listaDeAdvogados;

    @PostConstruct
    public void init() {
        try {
            recibo = (Recibo) navegacaoMB.getRegistroMapa("recibo", new Recibo());
            listaDeRecibos = new ArrayList<>();
            listaDeAdvogados = advogadoController.consultarTodos("nome");
            if (navegacaoMB.getUsuarioLogado().getPerfil().equals(Perfil.ADVOGADO)) {
                advogado = advogadoController.gerenciar( Integer.getInteger(navegacaoMB.getUsuarioLogado().getReferencia().toString()));
            }else{
                advogado = new Advogado();
            }
        } catch (Exception ex) {
            Logger.getLogger(ReciboMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void consultarRecibosNaoConferidos() {
        listaDeRecibos = reciboController.consultarRecibosAbertos(advogado);
    }

    public List<Recibo> getListaDeRecibos() {
        return listaDeRecibos;
    }

    public List<Advogado> getListaDeAdvogados() {
        return listaDeAdvogados;
    }

    public Recibo getRecibo() {
        return recibo;
    }

    public void setRecibo(Recibo recibo) {
        this.recibo = recibo;
    }

    public Advogado getAdvogado() {
        return advogado;
    }

    public void setAdvogado(Advogado advogado) {
        this.advogado = advogado;
    }
    
    

}
