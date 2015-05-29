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
 * @author ari
 */
@ManagedBean
@ViewScoped
public class PrestacaoDeContaMB implements Serializable {

    @Inject
    private NavegacaoMB navegacaoMB;
    @Inject
    private ReciboController reciboController;
    @Inject
    private AdvogadoController advogadoController;
    private Advogado advogado;
    private List<Recibo> listaDeRecibosConferidos;
    private List<Recibo> listaDeRecibosSelecionados;
    private List<Advogado> listaDeAdvogados;
    private BigDecimal totalRecibos = BigDecimal.ZERO;
    private BigDecimal totalColaborador = BigDecimal.ZERO;
    private BigDecimal totalRepasseSocio = BigDecimal.ZERO;
    private BigDecimal totalAdvogado = BigDecimal.ZERO;
    private BigDecimal totalRepasseAoDono = BigDecimal.ZERO;

    @PostConstruct
    public void init() {
        try {
            listaDeRecibosConferidos = new ArrayList<>();
            listaDeRecibosSelecionados = new ArrayList<>();
            listaDeAdvogados = advogadoController.consultarTodos("nome");
            if (navegacaoMB.getUsuarioLogado().getPerfil().equals(Perfil.ADVOGADO)) {
                advogado = advogadoController.carregar(navegacaoMB.getUsuarioLogado().getReferencia());
                listaDeRecibosConferidos = reciboController.consultaRecibosParaPrestacaoDeConta(advogado);

            } else {
                advogado = new Advogado();
            }
        } catch (Exception ex) {
            Logger.getLogger(PrestacaoDeContaMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void consultaRecibosParaPrestacaoDeconta() {
        listaDeRecibosConferidos = reciboController.consultaRecibosParaPrestacaoDeConta(advogado);
        calcularValoresDoRecibo();
    }

    private void calcularValoresDoRecibo() {
        List<BigDecimal> valores = reciboController.calcularValoresDoRecibo(listaDeRecibosConferidos);
        totalRecibos = valores.get(0);
        totalColaborador = valores.get(1);
        totalRepasseSocio = valores.get(2);
        totalAdvogado = valores.get(3);
        totalRepasseAoDono = valores.get(4);

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

    public String getTotalRepassaAoDono() {
        return FormatadorDeNumeros.converterBigDecimalEmStrng(totalRepasseAoDono);
    }

    public List<Recibo> getListaDeRecibosSelecionados() {
        return listaDeRecibosSelecionados;
    }

    public void setListaDeRecibosSelecionados(List<Recibo> listaDeRecibosSelecionados) {
        this.listaDeRecibosSelecionados = listaDeRecibosSelecionados;
    }

    public List<Recibo> getListaDeRecibosConferidos() {
        return listaDeRecibosConferidos;
    }

    public List<Advogado> getListaDeAdvogados() {
        return listaDeAdvogados;
    }

    public Advogado getAdvogado() {
        return advogado;
    }

    public void setAdvogado(Advogado advogado) {
        this.advogado = advogado;
    }

    public void setListaDeAdvogados(List<Advogado> listaDeAdvogados) {
        this.listaDeAdvogados = listaDeAdvogados;
    }

}
