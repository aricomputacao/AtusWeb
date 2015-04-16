/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.managedbean;

import br.com.atus.controller.ContratoController;
import br.com.atus.modelo.Contrato;
import br.com.atus.modelo.Peca;
import br.com.atus.util.MenssagemUtil;
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
 * @author gilmario
 */
@ManagedBean
@ViewScoped
public class ContratoMB extends BeanGenerico<Contrato> implements Serializable {

    @EJB
    private ContratoController controller;
    private Contrato contrato;
    @Inject
    private NavegacaoMB navegacaoMB;
    private List<Contrato> listaContratos;

    @PostConstruct
    public void init() {
        listaContratos = new ArrayList<>();
        contrato = (Contrato) navegacaoMB.getRegistroMapa("contrato", new Contrato());
    }

    public ContratoMB() {
        super(Contrato.class);
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }

    public List<Contrato> getListaContratos() {
        return listaContratos;
    }

    public void setListaContratos(List<Contrato> listaContratos) {
        this.listaContratos = listaContratos;
    }

    public void listar() {
        try {
            listaContratos = controller.consultarLike(getCampoBusca(), getValorBusca());
            if (listaContratos.isEmpty()) {
                MenssagemUtil.addMessageInfo("Nenhum resultado encontrado");
            }
        } catch (Exception e) {
            MenssagemUtil.addMessageErro(e);
        }
    }

    public void excluir(Contrato c) {

    }

    public void imprimir() {

    }

    public void salvar() {
        try {
            if (contrato.getId() == null) {

                controller.salvar(contrato);
            } else {
                controller.atualizar(contrato);

            }
            init();
            MenssagemUtil.addMessageInfo("Registro salvo");
        } catch (Exception e) {
            MenssagemUtil.addMessageErro("Erro ao salvar.");
        }
    }

}
