/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.atus.cadastro.managedbean;

import br.com.atus.cadastro.controller.ObjetoProcessoController;
import br.com.atus.util.managedbean.BeanGenerico;
import br.com.atus.util.managedbean.NavegacaoMB;
import br.com.atus.processo.modelo.ObjetoProcesso;
import br.com.atus.util.MenssagemUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
public class ObjetoProcessoMB extends BeanGenerico<ObjetoProcesso> implements Serializable{

    @Inject
    private NavegacaoMB navegacaoMB;
    @EJB
    private ObjetoProcessoController controller;
    private List<ObjetoProcesso> listaObjetoProcessos;
    private ObjetoProcesso objetoProcesso;
    
    public ObjetoProcessoMB() {
        super(ObjetoProcesso.class);
    }
    
    @PostConstruct
    public void init(){
        try {
            objetoProcesso = (ObjetoProcesso) navegacaoMB.getRegistroMapa("objeto_processo", new ObjetoProcesso());
            listaObjetoProcessos = new ArrayList<>();
        } catch (Exception ex) {
            Logger.getLogger(ObjetoProcessoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

       public void salvar() {
        try {
            controller.salvarouAtualizar(objetoProcesso);
            init();
            MenssagemUtil.addMessageInfo(NavegacaoMB.getMsg("salvar", MenssagemUtil.MENSAGENS));

        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("falha", MenssagemUtil.MENSAGENS), ex, "RamoJudiciario");
            Logger.getLogger(ObjetoProcessoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void listar() {
        try {
            if (getValorBusca() == null || getValorBusca().equals("")) {
                listaObjetoProcessos = controller.consultarTodos("nome");
            } else {
                listaObjetoProcessos = controller.consultarLike("nome", getValorBusca());
                MenssagemUtil.addMessageWarn(NavegacaoMB.getMsg("consulta.vazia", MenssagemUtil.MENSAGENS));

            }
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("consulta.vazia", MenssagemUtil.MENSAGENS));
            Logger.getLogger(ObjetoProcessoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void excluir(ObjetoProcesso ee) {
        try {
            ee = controller.gerenciar(ee.getId());
            controller.excluir(ee);
            listaObjetoProcessos.remove(ee);
            MenssagemUtil.addMessageInfo(NavegacaoMB.getMsg("excluir", MenssagemUtil.MENSAGENS));

        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("excluir.falha", MenssagemUtil.MENSAGENS));
            Logger.getLogger(ObjetoProcessoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<ObjetoProcesso> getListaObjetoProcessos() {
        return listaObjetoProcessos;
    }

    public void setListaObjetoProcessos(List<ObjetoProcesso> listaObjetoProcessos) {
        this.listaObjetoProcessos = listaObjetoProcessos;
    }

    public ObjetoProcesso getObjetoProcesso() {
        return objetoProcesso;
    }

    public void setObjetoProcesso(ObjetoProcesso objetoProcesso) {
        this.objetoProcesso = objetoProcesso;
    }
    
    
}
