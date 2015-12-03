/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.financeiro.controller;

import br.com.atus.financeiro.dao.CaixaColaboradorDAO;
import br.com.atus.financeiro.dto.CaixaColaboradorParcelasDTO;
import br.com.atus.financeiro.modelo.CaixaColaborador;
import br.com.atus.financeiro.modelo.ParcelasReceber;
import br.com.atus.financeiro.modelo.Recibo;
import br.com.atus.interfaces.Controller;
import br.com.atus.cadastro.modelo.Colaborador;
import br.com.atus.cadastro.modelo.Usuario;
import java.io.Serializable;
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
public class CaixaColaboradorController extends Controller<CaixaColaborador, Long> implements Serializable{

    @Inject
    private CaixaColaboradorDAO dao;
    
    @PostConstruct
    @Override
    protected void inicializaDAO() {
        setDAO(dao);
    }
    
    public void addCaixaColaborador(Recibo recibo) throws Exception{
        CaixaColaborador cc = new CaixaColaborador();
        cc.setRecibo(recibo);
        cc.setColaborador(recibo.getColaborador());
        cc.setProcesso(recibo.getProcesso());
        dao.salvar(cc);
      
    }
    
    public void pagarColaborador(List<CaixaColaborador> ccs,Usuario usuario) throws Exception{
        for (CaixaColaborador cc : ccs) {
            cc.setDataDeRecebimento(new Date());
            cc.setUsuarioQuePagou(usuario);
            dao.atualizar(cc);
        }
    }
    
    public List<CaixaColaborador> consultaPagamentosAbertosDo(Colaborador colaborador){
        return dao.consultaValoresAReceberDo(colaborador);
    }
    public List<CaixaColaborador> consultaPagamentosRealizadosDo(Colaborador colaborador){
        return dao.consultaValoresPagosrDo(colaborador);
    }
    public List<CaixaColaborador> consultaValoresPagosrOrdenadoPorProcessoDo(Colaborador colaborador){
        return dao.consultaValoresPagosrOrdenadoPorProcessoDo(colaborador);
    }
    public List<CaixaColaborador> consultaValoresAbertosOrdenadosPorProcessoDo(Colaborador colaborador){
        return dao.consultaValoresAbertosOrdenadosPorProcessoDo(colaborador);
    }
    
    
    public List<CaixaColaboradorParcelasDTO> consultaCaixaColaboradorParcelasDTOs(CaixaColaborador cc,List<ParcelasReceber> parcelas){
        List<CaixaColaboradorParcelasDTO> listaDTOs = new ArrayList<>();
        for (ParcelasReceber pr : parcelas) {
            CaixaColaboradorParcelasDTO dto = new CaixaColaboradorParcelasDTO();
            
            dto.setColaborador(cc.getColaborador());
            dto.setDataPagamentoColaborador(cc.getDataDeRecebimento());
            dto.setParcelasReceber(pr);
            dto.setProcesso(pr.getContaReceber().getProcesso());
            dto.setRecibo(cc.getRecibo());
            dto.setUsuarioQuePagou(cc.getUsuarioQuePagou());
            
            
            listaDTOs.add(dto);
        }
        
        return listaDTOs;
    }
}
