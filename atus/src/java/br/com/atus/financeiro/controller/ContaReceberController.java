/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.financeiro.controller;

import br.com.atus.financeiro.dao.ContaReceberDAO;
import br.com.atus.financeiro.dto.ContaReceberParcelasDTO;
import br.com.atus.financeiro.modelo.ContaReceber;
import br.com.atus.financeiro.modelo.ParcelasReceber;
import br.com.atus.interfaces.Controller;
import br.com.atus.util.MenssagemUtil;
import br.com.atus.util.managedbean.NavegacaoMB;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
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
public class ContaReceberController extends Controller<ContaReceber, Long> implements Serializable {

    @EJB
    private ContaReceberDAO dao;
    @EJB
    private ParcelaReceberController parcelaReceberController;

    @PostConstruct
    @Override
    protected void inicializaDAO() {
        setDAO(dao);
    }

    public void addContasReceber(ContaReceber contaReceber) throws Exception {
        validarPorcentagens(contaReceber);
        dao.salvar(contaReceber);
        parcelaReceberController.addListaDeParcelas(contaReceber);
    }

    
    public void validarPorcentagens(ContaReceber cr) throws Exception{
        BigDecimal bd = cr.getPercentualColaborador().add(cr.getCooptacao().getPercentDono()).add(cr.getCooptacao().getPercentSocio());
        if (bd.compareTo(new BigDecimal(100)) > 0) {
            throw new Exception(NavegacaoMB.getMsg("falha_soma_cooptacao", MenssagemUtil.MENSAGENS));
        }
    }
    
    public List<ContaReceberParcelasDTO> consultarTodasContasReceberAbertasDo(String cliente) {
        ContaReceberParcelasDTO dto;
        List<ContaReceber> listaContaRecebers = dao.consultarTodasContasReceberDo(cliente);
        List<ContaReceberParcelasDTO> listaDTO = new ArrayList<>();
        for (ContaReceber cr : listaContaRecebers) {
            List<ParcelasReceber> listaDeParcelasReceber = parcelaReceberController.consultaTodasParcelasDo(cr);
            Collections.sort(listaDeParcelasReceber);
            dto = new ContaReceberParcelasDTO(cr,  listaDeParcelasReceber);
            listaDTO.add(dto);
        }

        return listaDTO;
    }

   

}
