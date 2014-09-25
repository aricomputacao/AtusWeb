/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.managedbean;

import br.com.atus.controller.AtendimentoController;
import br.com.atus.modelo.Atendimento;
import br.com.atus.util.AssistentedeRelatorio;
import br.com.atus.util.MenssagemUtil;
import br.com.atus.util.RelatorioSession;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;

/**
 *
 * @author ari
 */
@ManagedBean
@ApplicationScoped
public class AtendimentoMB extends BeanGenerico<Atendimento> implements Serializable {

    @Inject
    private NavegacaoMB navegacaoMB;
    @EJB
    private AtendimentoController controller;
    private List<Atendimento> listaAtendimentos;
    private Atendimento atendimento;

    public AtendimentoMB() {
        super(Atendimento.class);
    }

    @PostConstruct
    public void init(){
        try {
            atendimento = (Atendimento) navegacaoMB.getRegistroMapa("atendimento",new Atendimento());
            listaAtendimentos = controller.listarTodos("id");
        } catch (Exception ex) {
            Logger.getLogger(AtendimentoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void salvar() {
        try {
            controller.salvarouAtualizar(atendimento);
            init();
            MenssagemUtil.addMessageInfo(NavegacaoMB.getMsg("salvar", MenssagemUtil.MENSAGENS));
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("falha", MenssagemUtil.MENSAGENS));
            Logger.getLogger(AtendimentoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void listar() {
        try {
            if (getValorBusca() == null || getValorBusca().equals("")) {
                listaAtendimentos = controller.listarTodos("nome");
            } else {
                listaAtendimentos = controller.listarLike(getCampoBusca(), getValorBusca());
                MenssagemUtil.addMessageWarn(NavegacaoMB.getMsg("consulta.vazia", MenssagemUtil.MENSAGENS));

            }
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("consulta.vazia", MenssagemUtil.MENSAGENS));
            Logger.getLogger(AtendimentoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void excluir(Atendimento ee) {
        try {
            ee = controller.gerenciar(ee.getId());
            controller.excluir(ee);
            listaAtendimentos.remove(ee);
            MenssagemUtil.addMessageInfo(NavegacaoMB.getMsg("excluir", MenssagemUtil.MENSAGENS));

        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("excluir.falha", MenssagemUtil.MENSAGENS));
            Logger.getLogger(AtendimentoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void imprimir() {
        if (!listaAtendimentos.isEmpty()) {
            Map<String, Object> m = new HashMap<>();
            byte[] rel = new AssistentedeRelatorio().relatorioemByte(listaAtendimentos, m, "WEB-INF/relatorios/rel_orgao.jasper", "Relatório de Ógãos");
            RelatorioSession.setBytesRelatorioInSession(rel);
        }

    }

    public List<Atendimento> getListaAtendimentos() {
        return listaAtendimentos;
    }

    public void setListaAtendimentos(List<Atendimento> listaAtendimentos) {
        this.listaAtendimentos = listaAtendimentos;
    }

    public Atendimento getAtendimento() {
        return atendimento;
    }

    public void setAtendimento(Atendimento atendimento) {
        this.atendimento = atendimento;
    }

}
