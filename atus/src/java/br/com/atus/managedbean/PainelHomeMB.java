/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.managedbean;

import br.com.atus.controller.ProcessoController;
import br.com.atus.dto.ProcessoAtrasadoDTO;
import br.com.atus.dto.ProcessoGrupoDiaAtrasadoDTO;
import br.com.atus.modelo.Fase;
import br.com.atus.modelo.Processo;
import java.io.Serializable;
import java.util.ArrayList;
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
    private List<ProcessoGrupoDiaAtrasadoDTO> listaGrupoDiaAtrasadoGeralDTOs;
    private List<ProcessoGrupoDiaAtrasadoDTO> listaGrupoDiaAtrasadoSetorDTOs;
    private List<Processo> listaProcessos;

    @PostConstruct
    private void init() {
        listaGrupoDiaAtrasadoGeralDTOs = processoController.processoGrupoDiaAtrasadoGeral();
        listaGrupoDiaAtrasadoSetorDTOs = processoController.processoGrupoDiaAtrasadoSetor(navegacaoMB.getUsuarioLogado());
        listaProcessos = new ArrayList<>();
    }

    
    public void listarProcessoFase(Fase f){
        listaProcessos = processoController.listarPorFase(f);
    }
    
    public List<ProcessoGrupoDiaAtrasadoDTO> getListaGrupoDiaAtrasadoGeralDTOs() {
        return listaGrupoDiaAtrasadoGeralDTOs;
    }

    public void setListaGrupoDiaAtrasadoGeralDTOs(List<ProcessoGrupoDiaAtrasadoDTO> listaGrupoDiaAtrasadoGeralDTOs) {
        this.listaGrupoDiaAtrasadoGeralDTOs = listaGrupoDiaAtrasadoGeralDTOs;
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

    public List<ProcessoGrupoDiaAtrasadoDTO> getListaGrupoDiaAtrasadoSetorDTOs() {
        return listaGrupoDiaAtrasadoSetorDTOs;
    }

    public void setListaGrupoDiaAtrasadoSetorDTOs(List<ProcessoGrupoDiaAtrasadoDTO> listaGrupoDiaAtrasadoSetorDTOs) {
        this.listaGrupoDiaAtrasadoSetorDTOs = listaGrupoDiaAtrasadoSetorDTOs;
    }

    public List<Processo> getListaProcessos() {
        return listaProcessos;
    }

    public void setListaProcessos(List<Processo> listaProcessos) {
        this.listaProcessos = listaProcessos;
    }

}
