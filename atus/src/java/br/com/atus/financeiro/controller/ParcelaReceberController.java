/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.financeiro.controller;

import br.com.atus.financeiro.dao.ParcelaReceberDAO;
import br.com.atus.financeiro.dto.ContaReceberParcelasDTO;
import br.com.atus.financeiro.modelo.ContaReceber;
import br.com.atus.financeiro.modelo.ParcelasReceber;
import br.com.atus.financeiro.modelo.Recibo;
import br.com.atus.interfaces.Controller;
import br.com.atus.util.NumeroPorExtenso;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
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
    @EJB
    private ReciboController reciboController;
    private List<ParcelasReceber> listaDeParcelasPagas;

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

    public void fazerPagamento(ContaReceberParcelasDTO dto, ParcelasReceber pr, BigDecimal valorPago,Recibo recibo) throws Exception {
        BigDecimal restante = BigDecimal.ZERO;
        listaDeParcelasPagas = new ArrayList<>();
        
        //faz o pagamento da parcela selecionada e retorna se tiver valor restante
        restante = fazerPagamentoDeParcelaEspecifica(dto, pr, valorPago);
        while (restante.compareTo(BigDecimal.ZERO) > 0) {
            //Pega parcela que ainda está aberta
            ParcelasReceber ultimaParcela = retornaUltimaParcelaAberta(dto.getParcelasRecebers());
            ultimaParcela.setObservcao(pr.getObservcao());
            restante = fazerPagamentoDeParcelaEspecifica(dto, ultimaParcela, restante);
        }
       reciboController.addRecibo(listaDeParcelasPagas,recibo);

    }

    private BigDecimal fazerPagamentoDeParcelaEspecifica(ContaReceberParcelasDTO dto, ParcelasReceber pr, BigDecimal valorPago) throws Exception {
        BigDecimal vlRestante = new BigDecimal(BigInteger.ZERO);
        BigDecimal vlNovo = new BigDecimal(BigInteger.ZERO);
        ParcelasReceber pr1 = new ParcelasReceber();
        dto.getParcelasRecebers().remove(pr);

        //Se o valor da parcela for mais que o valor pago
        if (pr.getValorParcela().compareTo(valorPago) > 0) {

            //Paga outra parcelaca que ainda esteja aberta pra saber se a parcela em questão é a ultima
            pr1 = retornaUltimaParcelaAberta(dto.getParcelasRecebers());
            //se pr1 for a ultima criar uma parcela com o valor restante
            if (pr1.getId() == null) {

                pr1.setContaReceber(pr.getContaReceber());
                pr1.setDataPagamento(new Date());
                pr1.setNumeroDaParcela(dao.pegarNumeroDaUltimaParcelaDo(pr.getContaReceber()) + 1);
                pr1.setValorPago(valorPago);
                pr1.setValorParcela(valorPago);
                pr1.setVencimento(pr.getVencimento());

                pr1 = dao.atualizarGerenciar(pr1);

                //Add parcela paga
                listaDeParcelasPagas.add(pr1);

                //Seta o valor da parcela em questão subtraindo o valor pago
                pr.setValorParcela(pr.getValorParcela().subtract(valorPago));
                vlRestante = vlRestante.subtract(valorPago);

                //se pr1 não for a ultima processa o valor da parcela em questão e e cria outra com o valor restante    
            } else {
                vlNovo = vlRestante.add(pr1.getValorParcela());
                pr1.setValorParcela(vlNovo);

                pr1 = dao.atualizarGerenciar(pr1);

                if (valorPago.compareTo(pr.getValorParcela()) >= 0) {
                    pr.setDataPagamento(new Date());
                    pr.setValorPago(valorPago);

                    vlRestante = valorPago.subtract(pr.getValorParcela());
                    //Add parcela paga
                    listaDeParcelasPagas.add(pr);

                } else {
                    ParcelasReceber pr2 = new ParcelasReceber();
                    pr2.setValorParcela(pr.getValorParcela().subtract(valorPago));

                    pr2.setContaReceber(pr.getContaReceber());
                    pr2.setNumeroDaParcela(dao.pegarNumeroDaUltimaParcelaDo(pr.getContaReceber()) + 1);
                    pr2.setObservcao("Parcela criado com o valor restante de um pagamento");
                    pr2.setVencimento(pr.getVencimento());

                    pr.setDataPagamento(new Date());
                    pr.setValorPago(valorPago);
                    pr.setValorParcela(valorPago);

                    //Add parcela paga
                    listaDeParcelasPagas.add(pr);

                    dao.salvar(pr2);

                    vlRestante = BigDecimal.ZERO;
                }

            }

            dto.getParcelasRecebers().add(pr1);

            dao.atualizar(pr);

        } else if (pr.getValorParcela().compareTo(valorPago) < 0) {
            //Aumenta o valor restante na ultima parcela
            vlRestante = valorPago.subtract(pr.getValorParcela());

            pr.setDataPagamento(new Date());
            pr.setValorPago(pr.getValorParcela());

            dao.atualizar(pr);
            //Add parcela paga
            listaDeParcelasPagas.add(pr);

        } else {
            //quita a parcela
            pr.setDataPagamento(new Date());
            pr.setValorPago(valorPago);

            dao.atualizar(pr);
            //Add parcela paga
            listaDeParcelasPagas.add(pr);

        }
        dto.getParcelasRecebers().add(pr);
        return vlRestante;
    }

    private ParcelasReceber retornaUltimaParcelaAberta(List<ParcelasReceber> prs) {
        for (int i = prs.size(); i > 0; i--) {
            if (prs.get(i - 1).getDataPagamento() == null) {
                return prs.remove(i - 1);
            }
        }
        return new ParcelasReceber();
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

    public void estornarPagamento(ParcelasReceber pr) throws Exception {
     
        pr.setDataPagamento(null);
        pr.setObservcao(null);
        pr.setValorParcela(pr.getValorPago());
        pr.setValorPago(BigDecimal.ZERO);

        dao.atualizar(pr);
    }

    private String numeroPorExtenso(BigDecimal numero) {
        NumeroPorExtenso n = NumeroPorExtenso.getDefaultInstance();
        n = new NumeroPorExtenso(true, true, true);
        return n.converteMoeda(numero);

    }

    public List<ParcelasReceber> getListaDeParcelasPagas() {
        return Collections.unmodifiableList(listaDeParcelasPagas);
    }

}
