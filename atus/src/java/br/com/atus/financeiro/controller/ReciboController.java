/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.financeiro.controller;

import br.com.atus.financeiro.dao.ReciboDAO;
import br.com.atus.financeiro.modelo.ParcelasReceber;
import br.com.atus.financeiro.modelo.Recibo;
import br.com.atus.interfaces.Controller;
import br.com.atus.modelo.Advogado;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
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
public class ReciboController extends Controller<Recibo, Long> implements Serializable {

    @EJB
    private ReciboDAO dao;
    @EJB
    private ParcelaReceberController parcelaReceberController;

    @PostConstruct
    @Override
    protected void inicializaDAO() {
        setDAO(dao);
    }

    public void addRecibo(List<ParcelasReceber> parcelas, Recibo r) throws Exception {

        r.setListaDeParcelasReceber(new ArrayList<ParcelasReceber>());
        r.setConfirmacaoRecebimento(false);
        r.setPrestadoConta(false);
       

        ParcelasReceber pr;
        for (ParcelasReceber p : parcelas) {
            pr = new ParcelasReceber();
            pr = parcelaReceberController.gerenciar(p.getId());
            r.addParcela(pr);

        }

        dao.salvar(r);
    }

    public List<Recibo> consultarRecibosAbertos(Advogado advogado) {
        return dao.consultarRecibosAbertos(advogado);
    }

    public List<BigDecimal> calcularValoresDoRecibo(List<Recibo> recibos) {
        List<BigDecimal> decimals = new ArrayList<>();
        BigDecimal totalRecibos = BigDecimal.ZERO;
        BigDecimal totalColaborador = BigDecimal.ZERO;
        BigDecimal totalRepasseSocio = BigDecimal.ZERO;
        BigDecimal totalAdvogado = BigDecimal.ZERO;
        
        for (Recibo r : recibos) {
           totalRecibos = totalRecibos.add(r.getValorTotal());
           totalColaborador = totalColaborador.add(r.getValorDoColaborador());
           totalRepasseSocio = totalRepasseSocio.add(r.getValorSocioDoProcesso());
           totalAdvogado = totalAdvogado.add(r.getValorDonoDoProcesso());
        }
        
        
        decimals.add(0,totalRecibos.setScale(2));
        decimals.add(1,totalColaborador.setScale(2));
        decimals.add(2,totalRepasseSocio.setScale(2));
        decimals.add(3,totalAdvogado.setScale(2));
        
        return decimals;
    }
}
