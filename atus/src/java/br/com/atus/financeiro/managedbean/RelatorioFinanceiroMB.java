/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.financeiro.managedbean;

import br.com.atus.financeiro.controller.ParcelaReceberController;
import br.com.atus.financeiro.modelo.ParcelasReceber;
import br.com.atus.util.AssistentedeRelatorio;
import br.com.atus.util.MenssagemUtil;
import br.com.atus.util.RelatorioSession;
import br.com.atus.util.managedbean.NavegacaoMB;
import java.io.Serializable;
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
    private List<ParcelasReceber> listaParcelasRecebers;
    private Date dtIni;
    private Date dtFim;

    @PostConstruct
    public void init(){
        listaParcelasRecebers = new ArrayList<>();
        dtIni = new Date();
        dtFim = new Date();
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

}
