/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.atus.processo.managedbean;

import br.com.atus.util.managedbean.BeanGenerico;
import br.com.atus.util.managedbean.NavegacaoMB;
import br.com.atus.processo.controller.EnderecamentoController;
import br.com.atus.modelo.Enderecamento;
import br.com.atus.util.AssistentedeRelatorio;
import br.com.atus.util.MenssagemUtil;
import br.com.atus.util.RelatorioSession;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author ari
 */
@ManagedBean
@ViewScoped
public class EnderecamentoMB extends BeanGenerico<Enderecamento> implements Serializable{

    @Inject
    private NavegacaoMB navegacaoMB;
    @EJB
    private EnderecamentoController controller;
    private Enderecamento enderecamento;
    private List<Enderecamento> listaEnderecamentos;
    
    @PostConstruct
    public void init(){
        try {
            enderecamento = (Enderecamento) navegacaoMB.getRegistroMapa("enderecamento", new Enderecamento());
            
            listaEnderecamentos = controller.consultarTodos("nome");
        } catch (Exception ex) {
            Logger.getLogger(EnderecamentoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public EnderecamentoMB() {
        super(Enderecamento.class);
    }
    
       public void salvar() {
        try {
            enderecamento.setNome(enderecamento.getNome().toUpperCase());
            controller.atualizar(enderecamento);
            init();
            MenssagemUtil.addMessageInfo(NavegacaoMB.getMsg("salvar", MenssagemUtil.MENSAGENS));

        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("falha", MenssagemUtil.MENSAGENS), ex, "Advogado");
            Logger.getLogger(EnderecamentoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void listar() {
        try {
            if (getValorBusca() == null || getValorBusca().equals("")) {
                listaEnderecamentos = controller.consultarTodos("nome");
            } else {
                listaEnderecamentos = controller.consultarLike("nome", getValorBusca());

            }
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("consulta.vazia", MenssagemUtil.MENSAGENS));
            Logger.getLogger(EnderecamentoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void excluir(Enderecamento ee) {
        try {
            ee = controller.gerenciar(ee.getId());
            controller.excluir(ee);
            listaEnderecamentos.remove(ee);
            MenssagemUtil.addMessageInfo(NavegacaoMB.getMsg("excluir", MenssagemUtil.MENSAGENS));

        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("excluir.falha", MenssagemUtil.MENSAGENS));
            Logger.getLogger(EnderecamentoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }



    public void imprimir() {
        if (!listaEnderecamentos.isEmpty()) {
            Map<String, Object> m = new HashMap<>();
            byte[] rel = new AssistentedeRelatorio().relatorioemByte(listaEnderecamentos, m, "WEB-INF/relatorios/rel_colaboradores.jasper", "Relatório de Colaboradores");
            RelatorioSession.setBytesRelatorioInSession(rel);
        }

    }

    public Enderecamento getEnderecamento() {
        return enderecamento;
    }

    public void setEnderecamento(Enderecamento enderecamento) {
        this.enderecamento = enderecamento;
    }

    public List<Enderecamento> getListaEnderecamentos() {
        return listaEnderecamentos;
    }

    public void setListaEnderecamentos(List<Enderecamento> listaEnderecamentos) {
        this.listaEnderecamentos = listaEnderecamentos;
    }

    
    
    
}
