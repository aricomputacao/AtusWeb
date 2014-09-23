/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.atus.managedbean;

import br.com.atus.controller.ColaboradorController;
import br.com.atus.enumerated.Sexo;
import br.com.atus.modelo.Colaborador;
import br.com.atus.modelo.Pessoa;
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
public class ColaboradorMB extends BeanGenerico<Colaborador> implements Serializable{

    @Inject
    private NavegacaoMB navegacaoMB;
    @EJB
    private ColaboradorController controller;
    private List<Colaborador> listaColaboradors;
    private Colaborador colaborador;
    
    public ColaboradorMB() {
        super(Colaborador.class);
    }
    
    @PostConstruct
    public void init(){
        colaborador = (Colaborador) navegacaoMB.getRegistroMapa("colaborardor", new Colaborador());
        if (colaborador.getId() == null) {
            colaborador.setPessoa(new Pessoa());
            
        } 
        listaColaboradors = new ArrayList<>();
    }
    
       public void salvar() {
        try {
            controller.atualizar(colaborador);
            init();
            MenssagemUtil.addMessageInfo(NavegacaoMB.getMsg("salvar", MenssagemUtil.MENSAGENS));

        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("falha", MenssagemUtil.MENSAGENS), ex, "Advogado");
            Logger.getLogger(ColaboradorMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void listar() {
        try {
            if (getValorBusca() == null || getValorBusca().equals("")) {
                listaColaboradors = controller.listarTodos("nome");
            } else {
                listaColaboradors = controller.listarLike("nome", getValorBusca());

            }
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("consulta.vazia", MenssagemUtil.MENSAGENS));
            Logger.getLogger(ColaboradorMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void excluir(Colaborador ee) {
        try {
            ee = controller.gerenciar(ee.getId());
            controller.excluir(ee);
            listaColaboradors.remove(ee);
            MenssagemUtil.addMessageInfo(NavegacaoMB.getMsg("excluir", MenssagemUtil.MENSAGENS));

        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("excluir.falha", MenssagemUtil.MENSAGENS));
            Logger.getLogger(ColaboradorMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void imprimir() {
        if (!listaColaboradors.isEmpty()) {
            Map<String, Object> m = new HashMap<>();
            byte[] rel = new AssistentedeRelatorio().relatorioemByte(listaColaboradors, m, "WEB-INF/relatorios/rel_colaboradores.jasper", "Relatório de Colaboradores");
            RelatorioSession.setBytesRelatorioInSession(rel);
        }

    }

    public Sexo[] listaSexo() {
        return Sexo.values();
    }
    
    public List<Colaborador> getListaColaboradors() {
        return listaColaboradors;
    }

    public void setListaColaboradors(List<Colaborador> listaColaboradors) {
        this.listaColaboradors = listaColaboradors;
    }

    public Colaborador getColaborador() {
        return colaborador;
    }

    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
    }
    
}
