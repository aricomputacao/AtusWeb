/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.managedbean;

import br.com.atus.controller.ProcessoController;
import br.com.atus.dto.ProcessosAtrasadoRelatorioDTO;
import br.com.atus.modelo.Cliente;
import br.com.atus.util.AssistentedeRelatorio;
import br.com.atus.util.RelatorioSession;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * Bean para relatorio
 *
 * @author Ari
 */
@ViewScoped
@ManagedBean
public class RelatorioMB extends BeanGenerico<ProcessosAtrasadoRelatorioDTO> implements Serializable {

    @EJB
    private ProcessoController processoController;
    private List<ProcessosAtrasadoRelatorioDTO> listaProcessosAtrasadoRelatorioDTOs;
    private Cliente cliente;

    public RelatorioMB() {
        super(ProcessosAtrasadoRelatorioDTO.class);
    }

    @PostConstruct
    public void init() {
        listaProcessosAtrasadoRelatorioDTOs = new ArrayList<>();
        cliente = new Cliente();
    }

    public void listarProcessosAtrasados() {
        listaProcessosAtrasadoRelatorioDTOs = processoController.listaProcessosAtrasadosRelatorio(cliente);
        cliente = new Cliente();

    }
    
    
     public void imprimirProcessoAtrasados() {
        if (!listaProcessosAtrasadoRelatorioDTOs.isEmpty()) {
            Map<String, Object> m = new HashMap<>();
            byte[] rel = new AssistentedeRelatorio().relatorioemByte(listaProcessosAtrasadoRelatorioDTOs, m, "WEB-INF/relatorios/rel_processos.jasper", "Relatório de Processos Atrasados");
            RelatorioSession.setBytesRelatorioInSession(rel);
        }

    }

    public List<ProcessosAtrasadoRelatorioDTO> getListaProcessosAtrasadoRelatorioDTOs() {
        return listaProcessosAtrasadoRelatorioDTOs;
    }

    public void setListaProcessosAtrasadoRelatorioDTOs(List<ProcessosAtrasadoRelatorioDTO> listaProcessosAtrasadoRelatorioDTOs) {
        this.listaProcessosAtrasadoRelatorioDTOs = listaProcessosAtrasadoRelatorioDTOs;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

}
