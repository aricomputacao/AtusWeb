/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.cadastro.managedbean;

import br.com.atus.cadastro.controller.AtendimentoController;
import br.com.atus.cadastro.controller.UsuarioController;
import br.com.atus.util.managedbean.BeanGenerico;
import br.com.atus.util.managedbean.NavegacaoMB;
import br.com.atus.modelo.Atendimento;
import br.com.atus.modelo.Usuario;
import br.com.atus.util.AssistentedeRelatorio;
import br.com.atus.util.MenssagemUtil;
import br.com.atus.util.RelatorioSession;
import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.PostLoad;

/**
 *
 * @author ari
 */
@ManagedBean
@ViewScoped
public class AtendimentoMB extends BeanGenerico<Atendimento> implements Serializable {

    @Inject
    private NavegacaoMB navegacaoMB;
    @EJB
    private AtendimentoController controller;
    @EJB
    private UsuarioController usuarioController;
    private List<Atendimento> listaAtendimentos;
    private List<Atendimento> listaAtendimentosFrente;
    private List<Atendimento> listaAtendimentosFundo;
    private List<Usuario> listaUsuarios;

    private Atendimento atendimento;

    public AtendimentoMB() {
        super(Atendimento.class);
    }

    @PostConstruct
    public void init() {
        try {
            atendimento = (Atendimento) navegacaoMB.getRegistroMapa("atendimento", new Atendimento());
            listaAtendimentosFrente = controller.listarAtendFrente();
            listaAtendimentosFundo = controller.listarAtendFundo(navegacaoMB.getUsuarioLogado());
            listaUsuarios = usuarioController.consultarTodos("login");
            if (atendimento.getId() == null) {
                atendimento.setDataAbertura(new Date());

            }
        } catch (Exception ex) {
            Logger.getLogger(AtendimentoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void atualizarFrenteFundo() {
        listaAtendimentosFrente = controller.listarAtendFrente();
        listaAtendimentosFundo = controller.listarAtendFundo(navegacaoMB.getUsuarioLogado());
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

    public void finalizarAtendimento() {
        try {
            atendimento.setDataSaida(new Date());
            atendimento.setDesistencia(false);
            atendimento.setUsuarioAtendeu(navegacaoMB.getUsuarioLogado());
            controller.salvarouAtualizar(atendimento);
            MenssagemUtil.addMessageInfo(NavegacaoMB.getMsg("atendimento_sucesso", MenssagemUtil.MENSAGENS));

        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("atendimento_falha", MenssagemUtil.MENSAGENS));
            Logger.getLogger(AtendimentoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cancelarAtendimento() {
        try {
            atendimento.setDesistencia(true);
            atendimento.setDataSaida(new Date());
            controller.salvarouAtualizar(atendimento);
            MenssagemUtil.addMessageInfo(NavegacaoMB.getMsg("atendimento_sucesso", MenssagemUtil.MENSAGENS));

        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("atendimento_falha", MenssagemUtil.MENSAGENS));
            Logger.getLogger(AtendimentoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void listar() {
        try {
            if (getValorBusca() == null || getValorBusca().equals("")) {
                listaAtendimentos = controller.consultarTodos("nome");
            } else {
                listaAtendimentos = controller.consultarLike(getCampoBusca(), getValorBusca());
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

    public List<Atendimento> getListaAtendimentosFrente() {
        return listaAtendimentosFrente;
    }

    public void setListaAtendimentosFrente(List<Atendimento> listaAtendimentosFrente) {
        this.listaAtendimentosFrente = listaAtendimentosFrente;
    }

    public List<Atendimento> getListaAtendimentosFundo() {
        return listaAtendimentosFundo;
    }

    public void setListaAtendimentosFundo(List<Atendimento> listaAtendimentosFundo) {
        this.listaAtendimentosFundo = listaAtendimentosFundo;
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

}
