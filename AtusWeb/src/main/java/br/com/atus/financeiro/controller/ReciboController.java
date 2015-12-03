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
import br.com.atus.cadastro.modelo.Advogado;
import br.com.atus.cadastro.modelo.Usuario;
import br.com.atus.util.exceptions.PrestacaoDeContaExceptio;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author ari
 */
@Stateless
public class ReciboController extends Controller<Recibo, Long> implements Serializable {

    @Inject
    private ReciboDAO dao;
    @Inject
    private ParcelaReceberController parcelaReceberController;
    @Inject
    private CaixaColaboradorController caixaColaboradorController;

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
        caixaColaboradorController.addCaixaColaborador(r);
        
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
        BigDecimal totalRepasseAoDono = BigDecimal.ZERO;

        for (Recibo r : recibos) {
            totalRecibos = totalRecibos.add(r.getValorTotal());
            totalColaborador = totalColaborador.add(r.getValorDoColaborador());
            totalRepasseSocio = totalRepasseSocio.add(r.getValorSocioDoProcesso());
            if (!r.getAdvogadoQueRecebeu().equals(r.getAdvogadoDonoProcesso())) {
                totalRepasseAoDono = totalRepasseAoDono.add(r.getValorRepasseDonoDoProcesso());
            } else {
                totalAdvogado = totalAdvogado.add(r.getValorDonoDoProcesso());
            }
        }

        decimals.add(0, totalRecibos.setScale(2,RoundingMode.HALF_EVEN));
        decimals.add(1, totalColaborador.setScale(2,RoundingMode.HALF_EVEN));
        decimals.add(2, totalRepasseSocio.setScale(2,RoundingMode.HALF_EVEN));
        decimals.add(3, totalAdvogado.setScale(2,RoundingMode.HALF_EVEN));
        decimals.add(4, totalRepasseAoDono.setScale(2,RoundingMode.HALF_EVEN));

        return decimals;
    }

    public void confirmarRecebimentos(List<Recibo> listaDeRecibosSelecionados, Usuario usuarioLogado) throws Exception {
        for (Recibo rec : listaDeRecibosSelecionados) {
            rec.setConfirmacaoRecebimento(true);
            rec.setDataConfirmacao(new Date());
            rec.setUsuarioQueConfirmouRecibo(usuarioLogado);
            rec.setPrestadoConta(false);

            dao.atualizar(rec);
        }
    }

    public List<Recibo> consultaRecibosParaPrestacaoDeConta(Advogado advogado) {
        return dao.consultarRecibosParaPrestarContas(advogado);
    }

    public void prestacaoDeContas(List<Recibo> listaDeRecibosSelecionados,Usuario usuario) throws PrestacaoDeContaExceptio,Exception {
        for (Recibo r : listaDeRecibosSelecionados) {
            r.setPrestadoConta(true);
            r.setDataPrestacao(new Date());
            r.setUsuarioQuePrestouConta(usuario);
            dao.atualizar(r);
        }
    }

}
