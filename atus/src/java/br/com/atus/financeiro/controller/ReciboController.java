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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author ari
 */
@Stateless
public class ReciboController extends Controller<Recibo, Long> implements Serializable{

    @EJB
    private ReciboDAO dao;
    @EJB
    private ParcelaReceberController parcelaReceberController;
    
    @PostConstruct
    @Override
    protected void inicializaDAO() {
        setDAO(dao);
    }
    
    public Recibo addRecibo(List<ParcelasReceber> parcelas) throws Exception{
        Recibo r = new Recibo();
        r.setListaDeParcelasReceber(new ArrayList<ParcelasReceber>());
        ParcelasReceber pr;
        for (ParcelasReceber p : parcelas) {
            pr = new ParcelasReceber();
            pr = parcelaReceberController.gerenciar(p.getId());
            r.addParcela(pr);
        }
        
        dao.salvar(r);
        return r;
    }
}
