/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.atus.managedbean;

import br.com.atus.controller.ProcessoController;
import br.com.atus.modelo.ParteInteressada;
import br.com.atus.modelo.Processo;
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
public class ProcessoMB extends BeanGenerico<Processo> implements Serializable{

    @Inject
    private NavegacaoMB navegacaoMB;
    @EJB
    private ProcessoController controller;
    private Processo processo;
    private ParteInteressada parteInteressada;
    private List<Processo> listaProcessos;
    private int i;
    
    @PostConstruct
    public void init(){
        processo = (Processo) navegacaoMB.getRegistroMapa("processo", new Processo());
        listaProcessos = new ArrayList<>();
        i = 0;
    }
    
    public void salvar() {
        try {
            controller.atualizar(processo);
            init();
            MenssagemUtil.addMessageInfo(NavegacaoMB.getMsg("salvar", MenssagemUtil.MENSAGENS));

        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("falha", MenssagemUtil.MENSAGENS), ex, "Advogado");
            Logger.getLogger(ProcessoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void listar() {
        try {
            if (getValorBusca() == null || getValorBusca().equals("")) {
                listaProcessos = controller.listarTodos("nome");
            } else {
                listaProcessos = controller.listarLike("nome", getValorBusca());

            }
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("consulta.vazia", MenssagemUtil.MENSAGENS));
            Logger.getLogger(ProcessoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void excluir(Processo ee) {
        try {
            ee = controller.gerenciar(ee.getId());
            controller.excluir(ee);
            listaProcessos.remove(ee);
            MenssagemUtil.addMessageInfo(NavegacaoMB.getMsg("excluir", MenssagemUtil.MENSAGENS));

        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("excluir.falha", MenssagemUtil.MENSAGENS));
            Logger.getLogger(ProcessoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void imprimir() {
        if (!listaProcessos.isEmpty()) {
            Map<String, Object> m = new HashMap<>();
            byte[] rel = new AssistentedeRelatorio().relatorioemByte(listaProcessos, m, "WEB-INF/relatorios/rel_especie_eventos.jasper", "Relatório de Especies de Eventos");
            RelatorioSession.setBytesRelatorioInSession(rel);
        }

    }
    
    public ProcessoMB() {
        super(Processo.class);
    }

    public Processo getProcesso() {
        return processo;
    }

    public void setProcesso(Processo processo) {
        this.processo = processo;
    }

    public List<Processo> getListaProcessos() {
        return listaProcessos;
    }

    public void setListaProcessos(List<Processo> listaProcessos) {
        this.listaProcessos = listaProcessos;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public ParteInteressada getParteInteressada() {
        return parteInteressada;
    }

    public void setParteInteressada(ParteInteressada parteInteressada) {
        this.parteInteressada = parteInteressada;
    }
    
    
    
}
