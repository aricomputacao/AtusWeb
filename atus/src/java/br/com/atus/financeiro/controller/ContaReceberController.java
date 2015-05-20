/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.financeiro.controller;

import br.com.atus.financeiro.dao.ContaReceberDAO;
import br.com.atus.financeiro.dto.ContaReceberParcelasDTO;
import br.com.atus.financeiro.modelo.ContaReceber;
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
        dao.salvar(contaReceber);
        parcelaReceberController.addListaDeParcelas(contaReceber);
    }

    public List<ContaReceberParcelasDTO> consultarTodasContasReceberAbertasDo(String cliente) {
        ContaReceberParcelasDTO dto;
        List<ContaReceber> listaContaRecebers = dao.consultarTodasContasReceberDo(cliente);
        List<ContaReceberParcelasDTO> listaDTO = new ArrayList<>();
        for (ContaReceber cr : listaContaRecebers) {
            dto = new ContaReceberParcelasDTO(cr, parcelaReceberController.consultaTodasParcelasDo(cr));
            listaDTO.add(dto);
        }

        return listaDTO;
    }

    

}
