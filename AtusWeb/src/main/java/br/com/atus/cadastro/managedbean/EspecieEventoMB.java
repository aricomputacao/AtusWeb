    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.cadastro.managedbean;

import br.com.atus.cadastro.controller.EspecieEventoController;
import br.com.atus.util.managedbean.BeanGenerico;
import br.com.atus.util.managedbean.NavegacaoMB;
import br.com.atus.cadastro.modelo.EspecieEvento;
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
public class EspecieEventoMB extends BeanGenerico<EspecieEvento> implements Serializable {

    @Inject
    private NavegacaoMB navegacaoMB;
    @EJB
    private EspecieEventoController controller;
    private List<EspecieEvento> listaEspecieEventos;

    private EspecieEvento especieEvento;

    public EspecieEventoMB() {
        super(EspecieEvento.class);

    }

    @PostConstruct
    public void init() {
        try {
            listaEspecieEventos = controller.consultarTodosOrdenadoPorNome();
            especieEvento = (EspecieEvento) navegacaoMB.getRegistroMapa("especie_evento", new EspecieEvento());
        } catch (Exception ex) {
            Logger.getLogger(EspecieEventoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void salvar() {
        try {
            controller.atualizar(especieEvento);
            init();
            MenssagemUtil.addMessageInfo(NavegacaoMB.getMsg("salvar", MenssagemUtil.MENSAGENS));

        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("falha", MenssagemUtil.MENSAGENS), ex, "RamoJudiciario");
            Logger.getLogger(EspecieEventoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void listar() {
        try {
            if (getValorBusca() == null || getValorBusca().equals("")) {
                listaEspecieEventos = controller.consultarTodos("nome");
            } else {
                listaEspecieEventos = controller.consultarLike("nome", getValorBusca());
                MenssagemUtil.addMessageWarn(NavegacaoMB.getMsg("consulta.vazia", MenssagemUtil.MENSAGENS));

            }
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("consulta.vazia", MenssagemUtil.MENSAGENS));
            Logger.getLogger(EspecieEventoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void excluir(EspecieEvento ee) {
        try {
            ee = controller.gerenciar(ee.getId());
            controller.excluir(ee);
            listaEspecieEventos.remove(ee);
            MenssagemUtil.addMessageInfo(NavegacaoMB.getMsg("excluir", MenssagemUtil.MENSAGENS));

        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("excluir.falha", MenssagemUtil.MENSAGENS));
            Logger.getLogger(EspecieEventoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void imprimir() {
        if (!listaEspecieEventos.isEmpty()) {
            Map<String, Object> m = new HashMap<>();
            byte[] rel = new AssistentedeRelatorio().relatorioemByte(listaEspecieEventos, m, "WEB-INF/relatorios/rel_especie_eventos.jasper", "Relatório de Especies de Eventos");
            RelatorioSession.setBytesRelatorioInSession(rel);
        }

    }

    public List<EspecieEvento> getListaEspecieEventos() {
        return listaEspecieEventos;
    }

    public void setListaEspecieEventos(List<EspecieEvento> listaEspecieEventos) {
        this.listaEspecieEventos = listaEspecieEventos;
    }

    public EspecieEvento getEspecieEvento() {
        return especieEvento;
    }

    public void setEspecieEvento(EspecieEvento especieEvento) {
        this.especieEvento = especieEvento;
    }

    

}
