/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.atus.managedbean;

import br.com.atus.controller.AdvogadoController;
import br.com.atus.controller.UnidadeFederativaController;
import br.com.atus.modelo.Advogado;
import br.com.atus.modelo.UnidadeFederativa;
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
public class AdvogadoMB extends BeanGenerico<Advogado> implements Serializable{

    @Inject
    private NavegacaoMB navegacaoMB;
    @EJB
    private AdvogadoController controller;
    @EJB
    private UnidadeFederativaController unidadeFederativaController;
    private List<Advogado> listaAdvogados;
    private List<UnidadeFederativa> listaUnidadeFederativas;
    private Advogado advogado;    
    
    public AdvogadoMB() {
        super(Advogado.class);
    }
    
    @PostConstruct
    public void init(){
        try {
            advogado = (Advogado) navegacaoMB.getRegistroMapa("advogado", new Advogado());
            listaAdvogados = controller.consultarTodos("pessoa.nome");
            listaUnidadeFederativas = unidadeFederativaController.consultarTodos("nome");
        } catch (Exception ex) {
            Logger.getLogger(AdvogadoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void salvar() {
        try {
            controller.atualizar(advogado);
            init();
            MenssagemUtil.addMessageInfo(NavegacaoMB.getMsg("salvar", MenssagemUtil.MENSAGENS));

        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("falha", MenssagemUtil.MENSAGENS), ex, "Advogado");
            Logger.getLogger(AdvogadoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void listar() {
        try {
            if (getValorBusca() == null || getValorBusca().equals("")) {
                listaAdvogados = controller.consultarTodos("nome");
            } else {
                listaAdvogados = controller.consultarLike("nome", getValorBusca());

            }
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("consulta.vazia", MenssagemUtil.MENSAGENS));
            Logger.getLogger(AdvogadoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void excluir(Advogado ee) {
        try {
            ee = controller.gerenciar(ee.getId());
            controller.excluir(ee);
            listaAdvogados.remove(ee);
            MenssagemUtil.addMessageInfo(NavegacaoMB.getMsg("excluir", MenssagemUtil.MENSAGENS));

        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("excluir.falha", MenssagemUtil.MENSAGENS));
            Logger.getLogger(AdvogadoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void imprimir() {
        if (!listaAdvogados.isEmpty()) {
            Map<String, Object> m = new HashMap<>();
            byte[] rel = new AssistentedeRelatorio().relatorioemByte(listaAdvogados, m, "WEB-INF/relatorios/rel_especie_eventos.jasper", "Relatório de Especies de Eventos");
            RelatorioSession.setBytesRelatorioInSession(rel);
        }

    }
    
    public List<Advogado> getListaAdvogados() {
        return listaAdvogados;
    }

    public void setListaAdvogados(List<Advogado> listaAdvogados) {
        this.listaAdvogados = listaAdvogados;
    }

    public Advogado getAdvogado() {
        return advogado;
    }

    public void setAdvogado(Advogado advogado) {
        this.advogado = advogado;
    }

    public List<UnidadeFederativa> getListaUnidadeFederativas() {
        return listaUnidadeFederativas;
    }

    public void setListaUnidadeFederativas(List<UnidadeFederativa> listaUnidadeFederativas) {
        this.listaUnidadeFederativas = listaUnidadeFederativas;
    }
    
    
    
}
