/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.financeiro.managedbean;

import br.com.atus.financeiro.controller.CaixaColaboradorController;
import br.com.atus.financeiro.controller.ParcelaReceberController;
import br.com.atus.financeiro.dto.CaixaColaboradorParcelasDTO;
import br.com.atus.financeiro.modelo.CaixaColaborador;
import br.com.atus.financeiro.modelo.ParcelasReceber;
import br.com.atus.modelo.Colaborador;
import br.com.atus.util.AssistentedeRelatorio;
import br.com.atus.util.MenssagemUtil;
import br.com.atus.util.RelatorioSession;
import br.com.atus.util.managedbean.NavegacaoMB;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class RelatorioFinanceiroMB implements Serializable {

    @Inject
    private ParcelaReceberController parcelaReceberController;
    @Inject
    private CaixaColaboradorController caixaColaboradorController;
    private List<CaixaColaborador> listaDePagamentosAbertosDoColaborador;
    private List<CaixaColaborador> listaDePagamentosRealizadosDoColaborador;
    private List<ParcelasReceber> listaParcelasRecebers;
    private Colaborador colaborador;
    private Date dtIni;
    private Date dtFim;

    @PostConstruct
    public void init() {
        listaParcelasRecebers = new ArrayList<>();
        dtIni = new Date();
        dtFim = new Date();
        listaDePagamentosAbertosDoColaborador = new ArrayList<>();
        listaDePagamentosRealizadosDoColaborador = new ArrayList<>();
        colaborador = new Colaborador();
    }

    public void consultarParcelasVencidasPorPeriodo() {
        listaParcelasRecebers = parcelaReceberController.consultaParcelasVencidasEntre(dtIni, dtFim);
        if (listaParcelasRecebers.isEmpty()) {
            MenssagemUtil.addMessageWarn(NavegacaoMB.getMsg("consulta.vazia", MenssagemUtil.MENSAGENS));
        }
    }

    public void imprimirParcelasVencidas() {
        if (!listaParcelasRecebers.isEmpty()) {
            Map<String, Object> m = new HashMap<>();
            byte[] rel = new AssistentedeRelatorio().relatorioemByte(listaParcelasRecebers, m, "WEB-INF/relatorios/rel_parcelas.jasper", "Parcelas Vencidas");
            RelatorioSession.setBytesRelatorioInSession(rel);
        }
    }

    public void imprimirPagamentosRealizados() {
        List<CaixaColaboradorParcelasDTO> ccp = new ArrayList<>();
        for (CaixaColaborador pg : listaDePagamentosRealizadosDoColaborador) {
            List<CaixaColaboradorParcelasDTO> ccp2 =  caixaColaboradorController.consultaCaixaColaboradorParcelasDTOs(pg, pg.getListaDeParcelas());
            if (!ccp2.isEmpty()) {
                ccp.addAll(ccp2);
            }           
        }

        if (!ccp.isEmpty()) {
            Map<String, Object> m = new HashMap<>();
            byte[] rel = new AssistentedeRelatorio().relatorioemByte(ccp, m, "WEB-INF/relatorios/rel_caixa_colaborador.jasper", "Pagamentos Realizados");
            RelatorioSession.setBytesRelatorioInSession(rel);
        }
    }
    public void imprimirPagamentosAbertos() {
        List<CaixaColaboradorParcelasDTO> ccp = new ArrayList<>();
        for (CaixaColaborador pg : listaDePagamentosAbertosDoColaborador) {
            List<CaixaColaboradorParcelasDTO> ccp2 =  caixaColaboradorController.consultaCaixaColaboradorParcelasDTOs(pg, pg.getListaDeParcelas());
            if (!ccp2.isEmpty()) {
                ccp.addAll(ccp2);
            }           

        }

        if (!ccp.isEmpty()) {
            Map<String, Object> m = new HashMap<>();
            byte[] rel = new AssistentedeRelatorio().relatorioemByte(ccp, m, "WEB-INF/relatorios/rel_caixa_colaborador.jasper", "Pagamentos Abertos");
            RelatorioSession.setBytesRelatorioInSession(rel);
        }
    }

    public void consultarPagamentosDoColaborador() {
        listaDePagamentosAbertosDoColaborador = caixaColaboradorController.consultaValoresAbertosOrdenadosPorProcessoDo(colaborador);
        listaDePagamentosRealizadosDoColaborador = caixaColaboradorController.consultaValoresPagosrOrdenadoPorProcessoDo(colaborador);
    }

    public List<ParcelasReceber> getListaParcelasRecebers() {
        return listaParcelasRecebers;
    }

    public Date getDtIni() {
        return dtIni;
    }

    public void setDtIni(Date dtIni) {
        this.dtIni = dtIni;
    }

    public Date getDtFim() {
        return dtFim;
    }

    public void setDtFim(Date dtFim) {
        this.dtFim = dtFim;
    }

    public Colaborador getColaborador() {
        return colaborador;
    }

    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
    }

    public List<CaixaColaborador> getListaDePagamentosAbertosDoColaborador() {
        return listaDePagamentosAbertosDoColaborador;
    }

    public void setListaDePagamentosAbertosDoColaborador(List<CaixaColaborador> listaDePagamentosAbertosDoColaborador) {
        this.listaDePagamentosAbertosDoColaborador = listaDePagamentosAbertosDoColaborador;
    }

    public List<CaixaColaborador> getListaDePagamentosRealizadosDoColaborador() {
        return listaDePagamentosRealizadosDoColaborador;
    }

    public void setListaDePagamentosRealizadosDoColaborador(List<CaixaColaborador> listaDePagamentosRealizadosDoColaborador) {
        this.listaDePagamentosRealizadosDoColaborador = listaDePagamentosRealizadosDoColaborador;
    }

}
