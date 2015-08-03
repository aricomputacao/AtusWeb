/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.financeiro.managedbean;

import br.com.atus.financeiro.controller.CaixaColaboradorController;
import br.com.atus.financeiro.modelo.CaixaColaborador;
import br.com.atus.financeiro.modelo.Recibo;
import br.com.atus.modelo.Colaborador;
import br.com.atus.util.AssistentedeRelatorio;
import br.com.atus.util.MenssagemUtil;
import br.com.atus.util.NumeroPorExtenso;
import br.com.atus.util.RelatorioSession;
import br.com.atus.util.managedbean.BeanGenerico;
import br.com.atus.util.managedbean.NavegacaoMB;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private Recibo recibo;
    private Colaborador colaborador;
    private BigDecimal totalEmAberto;
    private BigDecimal totalSelecionado;

    public CaixaColaboradorMB() {
        super(CaixaColaborador.class);
    }

    @PostConstruct
    public void init() {
        listaCaixaColaboradorEmAberto = new ArrayList<>();
        listaCaixaColaboradorSelecionadas = new ArrayList<>();
        colaborador = new Colaborador();
        totalEmAberto = BigDecimal.ZERO;
        totalSelecionado = BigDecimal.ZERO;
    }

    public void selecionarPagamento(CaixaColaborador cc) {
        listaCaixaColaboradorSelecionadas.add(cc);
        listaCaixaColaboradorEmAberto.remove(cc);
        totalSelecionado = totalSelecionado.add(cc.getValorDoColaborador()).setScale(2,RoundingMode.HALF_EVEN);
        totalEmAberto = totalEmAberto.subtract(cc.getValorDoColaborador()).setScale(2,RoundingMode.HALF_EVEN);

    }

    public void removerSelecionado(CaixaColaborador cc) {
        listaCaixaColaboradorEmAberto.add(cc);
        listaCaixaColaboradorSelecionadas.remove(cc);
        totalSelecionado = totalSelecionado.subtract(cc.getValorDoColaborador()).setScale(2,RoundingMode.HALF_EVEN);
        totalEmAberto = totalEmAberto.add(cc.getValorDoColaborador()).setScale(2,RoundingMode.HALF_EVEN);
    }

    public void consultaPagamentosAbertos() {
        listaCaixaColaboradorEmAberto = caixaColaboradorController.consultaPagamentosAbertosDo(colaborador);
        listaCaixaColaboradorSelecionadas.clear();
        totalEmAberto= BigDecimal.ZERO;
        totalSelecionado = BigDecimal.ZERO;
        for (CaixaColaborador ls : listaCaixaColaboradorEmAberto) {
            totalEmAberto = totalEmAberto.add(ls.getValorDoColaborador()).setScale(2,RoundingMode.HALF_EVEN);
        }
    }

    public void pagarColaborador() {
        try {
            caixaColaboradorController.pagarColaborador(listaCaixaColaboradorSelecionadas, navegacaoMB.getUsuarioLogado());
            imprimirRecibo();
            consultaPagamentosAbertos();
            MenssagemUtil.addMessageInfo(NavegacaoMB.getMsg("pagamento_sucesso", MenssagemUtil.MENSAGENS));

        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("pagamento_colaborador_falha", MenssagemUtil.MENSAGENS));
            Logger.getLogger(CaixaColaboradorMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void imprimirRecibo() {
        if (!listaCaixaColaboradorSelecionadas.isEmpty()) {
            Map<String, Object> m = new HashMap<>();
            NumeroPorExtenso porExtenso = new  NumeroPorExtenso(true, true, true);
            m.put("usr_pagou", navegacaoMB.getUsuarioLogado().getNome());
            m.put("valor_total", totalSelecionado);
            m.put("data_pagamento", listaCaixaColaboradorSelecionadas.get(0).getDataDoPagamento());
            m.put("valor_extenso", porExtenso.converteMoeda(totalSelecionado));
               byte[] rel = new AssistentedeRelatorio().relatorioemByte(listaCaixaColaboradorSelecionadas, m, "WEB-INF/relatorios/recibo_pag_colaborador.jasper", "Recibo Colaborador");
            RelatorioSession.setBytesRelatorioInSession(rel);
        }

    }
    
    public void consultarParcelas(Recibo r){
        recibo = r;
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

    public BigDecimal getTotalEmAberto() {
        return totalEmAberto;
    }

    public void setTotalEmAberto(BigDecimal totalEmAberto) {
        this.totalEmAberto = totalEmAberto;
    }

    public BigDecimal getTotalSelecionado() {
        return totalSelecionado;
    }

    public void setTotalSelecionado(BigDecimal totalSelecionado) {
        this.totalSelecionado = totalSelecionado;
    }

    public Recibo getRecibo() {
        return recibo;
    }

    public void setRecibo(Recibo recibo) {
        this.recibo = recibo;
    }

    
}
