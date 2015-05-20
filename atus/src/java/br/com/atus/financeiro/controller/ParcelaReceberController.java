/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.financeiro.controller;

import br.com.atus.financeiro.dao.ParcelaReceberDAO;
import br.com.atus.financeiro.modelo.ContaReceber;
import br.com.atus.financeiro.modelo.ParcelasReceber;
import br.com.atus.interfaces.Controller;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author ari
 */
@Stateless
public class ParcelaReceberController extends Controller<ParcelasReceber, Long> implements Serializable {

    @EJB
    private ParcelaReceberDAO dao;

    @Override
    @PostConstruct
    protected void inicializaDAO() {
        setDAO(dao);
    }

    public void addListaDeParcelas(ContaReceber cr) throws Exception {
        ParcelasReceber receber;

        List<BigDecimal> listaDeValores = totalParcelas(cr.getValor(), cr.getQuantidadeParcelas());
        List<Date> listaDeVencimentos = geraVencimentos(cr.getDataCadastro(), cr.getQuantidadeParcelas());
        for (int i = 1; i <= cr.getQuantidadeParcelas(); i++) {
            receber = new ParcelasReceber();
            receber.setContaReceber(cr);
            receber.setNumeroDaParcela(i);
            receber.setVencimento(listaDeVencimentos.get(i - 1));

            receber.setValorParcela(listaDeValores.get(i - 1));
            dao.salvar(receber);
        }
    }

    public List<ParcelasReceber> consultaParcelasAbertasDo(ContaReceber cr) {
        return dao.consultaParcelasAbertasDo(cr);
    }

    public List<ParcelasReceber> consultaParcelasVencidasDo(ContaReceber cr) {
        return dao.consultaParcelasVencidasDo(cr);
    }

    public List<ParcelasReceber> consultaTodasParcelasDo(ContaReceber cr) {
        return dao.consultaTodasParcelasDo(cr);
    }

    private static List<Date> geraVencimentos(Date dataInicial, Integer quantidadeParcelas) throws Exception {
        List<Date> listaVencimento = new ArrayList<>();
        listaVencimento.add(dataInicial);
        Calendar c = Calendar.getInstance();
        c.setTime(dataInicial);
        for (int i = 1; i < quantidadeParcelas; i++) {
            c.add(Calendar.MONTH, 1);
            listaVencimento.add(c.getTime());
        }
        return listaVencimento;

    }

    private static List<BigDecimal> totalParcelas(BigDecimal total, Integer quantidadeParcelas) throws Exception {
        List<BigDecimal> parcelas = new ArrayList<>();
        for (int i = 1; i <= quantidadeParcelas; i++) {
            BigDecimal div = new BigDecimal(quantidadeParcelas);
            BigDecimal valorParcela = total.divide(div, 2, RoundingMode.CEILING);
            BigDecimal valorParcial = valorParcela.multiply(div.subtract(new BigDecimal(1)));
            BigDecimal ultimaParcela = total.subtract(valorParcial);
            if (i == quantidadeParcelas) {
                parcelas.add(ultimaParcela);
            } else {
                parcelas.add(valorParcela);
            }
        }
        return parcelas;
    }
}
