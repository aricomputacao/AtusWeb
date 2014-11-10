/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.managedbean;

import br.com.atus.controller.ProcessoController;
import br.com.atus.dto.ProcessoAtrasadoDTO;
import java.io.Serializable;
import java.util.List;
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
public class PainelHomeMB implements Serializable {

    @Inject
    private NavegacaoMB navegacaoMB;
    @EJB
    private ProcessoController processoController;
    private List<ProcessoAtrasadoDTO> listaAtrasadoUsuario;
    private List<ProcessoAtrasadoDTO> listaAtrasadoGeral;
    
    @PostConstruct
    private void init(){
        listaAtrasadoGeral = processoController.processoAtrasadoGeral();
        listaAtrasadoUsuario = processoController.processoAtrasadoUsuario(navegacaoMB.getUsuarioLogado());
    }

    public List<ProcessoAtrasadoDTO> getListaAtrasadoUsuario() {
        return listaAtrasadoUsuario;
    }

    public void setListaAtrasadoUsuario(List<ProcessoAtrasadoDTO> listaAtrasadoUsuario) {
        this.listaAtrasadoUsuario = listaAtrasadoUsuario;
    }

    public List<ProcessoAtrasadoDTO> getListaAtrasadoGeral() {
        return listaAtrasadoGeral;
    }

    public void setListaAtrasadoGeral(List<ProcessoAtrasadoDTO> listaAtrasadoGeral) {
        this.listaAtrasadoGeral = listaAtrasadoGeral;
    }
    
    
    

}
