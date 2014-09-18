/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.atus.managedbean;

import br.com.atus.controller.ProfissaoController;
import br.com.atus.modelo.Profissao;
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
 * @author Ari
 */
@ManagedBean
@ViewScoped
public class ProfissaoMB extends BeanGenerico<Profissao> implements Serializable{

    @Inject
    private NavegacaoMB navegacaoMB;
    @EJB
    private ProfissaoController controller;
    private List<Profissao> listaProfissaos;
    private Profissao profissao;
    
    public ProfissaoMB() {
        super(Profissao.class);
    }
    
    @PostConstruct
    public void init(){
        profissao = (Profissao) navegacaoMB.getRegistroMapa("profissao", new Profissao());
        listaProfissaos = new ArrayList<>();
    }
    
     public void salvar() {
        try {
            controller.atualizar(profissao);
            init();
            MenssagemUtil.addMessageInfo(NavegacaoMB.getMsg("salvar", MenssagemUtil.MENSAGENS));

        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("falha", MenssagemUtil.MENSAGENS), ex, "RamoJudiciario");
            Logger.getLogger(ProfissaoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void listar() {
        try {
            if (getValorBusca() == null || getValorBusca().equals("")) {
                listaProfissaos = controller.listarTodos("nome");
            } else {
                listaProfissaos = controller.listarLike("nome", getValorBusca());
                MenssagemUtil.addMessageWarn(NavegacaoMB.getMsg("consulta.vazia", MenssagemUtil.MENSAGENS));

            }
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("consulta.vazia", MenssagemUtil.MENSAGENS));
            Logger.getLogger(EspecieEventoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void excluir(Profissao ee) {
        try {
            ee = controller.gerenciar(ee.getId());
            controller.excluir(ee);
            listaProfissaos.remove(ee);
            MenssagemUtil.addMessageInfo(NavegacaoMB.getMsg("excluir", MenssagemUtil.MENSAGENS));

        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("excluir.falha", MenssagemUtil.MENSAGENS));
            Logger.getLogger(ProfissaoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void imprimir() {
        if (!listaProfissaos.isEmpty()) {
            Map<String, Object> m = new HashMap<>();
            byte[] rel = new AssistentedeRelatorio().relatorioemByte(listaProfissaos, m, "WEB-INF/relatorios/rel_especie_eventos.jasper", "Relatório de Especies de Eventos");
            RelatorioSession.setBytesRelatorioInSession(rel);
        }

    }


    public List<Profissao> getListaProfissaos() {
        return listaProfissaos;
    }

    public void setListaProfissaos(List<Profissao> listaProfissaos) {
        this.listaProfissaos = listaProfissaos;
    }

    public Profissao getProfissao() {
        return profissao;
    }

    public void setProfissao(Profissao profissao) {
        this.profissao = profissao;
    }
    
    
    
}
