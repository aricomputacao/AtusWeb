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
import br.com.atus.util.FormatadorDeNumeros;
import br.com.atus.util.MetodosUtilitarios;
import br.com.atus.util.managedbean.NavegacaoMB;
import java.io.Serializable;
import java.math.BigDecimal;
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
    private BigDecimal totalRecibos = BigDecimal.ZERO;
    private BigDecimal totalColaborador = BigDecimal.ZERO;
    private BigDecimal totalRepasseSocio = BigDecimal.ZERO;
    private BigDecimal totalAdvogado = BigDecimal.ZERO;

    @PostConstruct
    public void init() {
        try {
            recibo = (Recibo) navegacaoMB.getRegistroMapa("recibo", new Recibo());
            listaDeRecibos = new ArrayList<>();
            listaDeAdvogados = advogadoController.consultarTodos("nome");
            if (navegacaoMB.getUsuarioLogado().getPerfil().equals(Perfil.ADVOGADO)) {
                advogado = advogadoController.carregar(navegacaoMB.getUsuarioLogado().getReferencia());
            }else{
                advogado = new Advogado();
            }
        } catch (Exception ex) {
            Logger.getLogger(ReciboMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void calcularValoresDoRecibo(){
        List<BigDecimal> valores = reciboController.calcularValoresDoRecibo(listaDeRecibos);
        totalRecibos = valores.get(0);
        totalColaborador = valores.get(1);
        totalRepasseSocio = valores.get(2);
        totalAdvogado = valores.get(3);
        
    }
    

    public void consultarRecibosNaoConferidos() {
        listaDeRecibos = reciboController.consultarRecibosAbertos(advogado);
        calcularValoresDoRecibo();
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

    public String getTotalRecibos() {
        return FormatadorDeNumeros.converterBigDecimalEmStrng(totalRecibos);
    }

    public String getTotalColaborador() {
        return FormatadorDeNumeros.converterBigDecimalEmStrng(totalColaborador);
    }

    public String getTotalRepasseSocio() {
        return FormatadorDeNumeros.converterBigDecimalEmStrng(totalRepasseSocio);
    }

    public String getTotalAdvogado() {
        return FormatadorDeNumeros.converterBigDecimalEmStrng(totalAdvogado);
    }
    
    

}
