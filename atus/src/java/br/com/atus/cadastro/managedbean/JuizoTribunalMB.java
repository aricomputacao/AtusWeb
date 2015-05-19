/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.cadastro.managedbean;

import br.com.atus.cadastro.controller.JuizoTribunalController;
import br.com.atus.util.managedbean.BeanGenerico;
import br.com.atus.util.managedbean.NavegacaoMB;
import br.com.atus.modelo.JuizoTribunal;
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
public class JuizoTribunalMB extends BeanGenerico<JuizoTribunal> implements Serializable {

    @Inject
    private NavegacaoMB navegacaoMB;
    @EJB
    private JuizoTribunalController controller;
    private List<JuizoTribunal> listaJuizoTribunals;
    private JuizoTribunal juizoTribunal;

    public JuizoTribunalMB() {
        super(JuizoTribunal.class);
    }

    @PostConstruct
    public void init() {
        try {
            juizoTribunal = (JuizoTribunal) navegacaoMB.getRegistroMapa("juizo_tribunal", new JuizoTribunal());
            listaJuizoTribunals = controller.consultarTodos("nome");
        } catch (Exception ex) {
            Logger.getLogger(JuizoTribunalMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void salvar() {
        try {
            controller.salvarouAtualizar(juizoTribunal);
            init();
            MenssagemUtil.addMessageInfo(NavegacaoMB.getMsg("salvar", MenssagemUtil.MENSAGENS));
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("falha", MenssagemUtil.MENSAGENS));
            Logger.getLogger(JuizoTribunalMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void listar() {
        try {
            if (getValorBusca() == null || getValorBusca().equals("")) {
                listaJuizoTribunals = controller.consultarTodos("nome");
            } else {
                listaJuizoTribunals = controller.consultarLike(getCampoBusca(), getValorBusca());

            }
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("consulta.vazia", MenssagemUtil.MENSAGENS));
            Logger.getLogger(JuizoTribunalMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void excluir(JuizoTribunal ee) {
        try {
            ee = controller.gerenciar(ee.getId());
            controller.excluir(ee);
            listaJuizoTribunals.remove(ee);
            MenssagemUtil.addMessageInfo(NavegacaoMB.getMsg("excluir", MenssagemUtil.MENSAGENS));

        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("excluir.falha", MenssagemUtil.MENSAGENS));
            Logger.getLogger(JuizoTribunalMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void imprimir() {
        if (!listaJuizoTribunals.isEmpty()) {
            Map<String, Object> m = new HashMap<>();
            byte[] rel = new AssistentedeRelatorio().relatorioemByte(listaJuizoTribunals, m, "WEB-INF/relatorios/rel_juizo_tribunal.jasper", "Relatório de Juizos e Tribunais");
            RelatorioSession.setBytesRelatorioInSession(rel);
        }

    }

    public List<JuizoTribunal> getListaJuizoTribunals() {
        return listaJuizoTribunals;
    }

    public void setListaJuizoTribunals(List<JuizoTribunal> listaJuizoTribunals) {
        this.listaJuizoTribunals = listaJuizoTribunals;
    }

    public JuizoTribunal getJuizoTribunal() {
        return juizoTribunal;
    }

    public void setJuizoTribunal(JuizoTribunal juizoTribunal) {
        this.juizoTribunal = juizoTribunal;
    }

}
