/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.managedbean;

import br.com.atus.controller.CidadeController;
import br.com.atus.controller.UnidadeFederativaController;
import br.com.atus.modelo.Cidade;
import br.com.atus.modelo.Pessoa;
import br.com.atus.modelo.UnidadeFederativa;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Ari
 */
@ManagedBean
@ViewScoped
public class UnidadeFederativaMB extends BeanGenerico<UnidadeFederativa> implements Serializable {

    @EJB
    private UnidadeFederativaController controller;
    @EJB
    private CidadeController cidadeController;
    private List<UnidadeFederativa> listaUnidadeFederativas;
    private List<Cidade> listaCidades;
    private UnidadeFederativa federativa;

    public UnidadeFederativaMB() {
        super(UnidadeFederativa.class);
    }

    @PostConstruct
    public void init() {
        try {
            federativa = new UnidadeFederativa();
            listaUnidadeFederativas = controller.consultarTodos("nome");
            listaCidades = cidadeController.consultarTodos("nome");
        } catch (Exception ex) {
            Logger.getLogger(UnidadeFederativaMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void listarCidadeUf(UnidadeFederativa uf) {
        listaCidades = cidadeController.listaPorUf(uf);
    }

    public void setarCidadeEstado(Pessoa p){
        try {
            init();
            listaUnidadeFederativas = controller.consultarTodos("nome");
            federativa = p.getCidade().getUnidadeFederativa();
            listaCidades = cidadeController.listaPorUf(federativa);
            
        } catch (Exception ex) {
            Logger.getLogger(UnidadeFederativaMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void encontraCEP(String cep, String ufe, String cid, String tpLog, String log, String bai, Pessoa p,UnidadeFederativa uf) {
        p.setCep(cep);
        uf = controller.buscaAbreviacao(ufe.trim());
        p.setCidade(cidadeController.buscarUfNome(uf, cid));
        p.setLogradouro(tpLog.concat(" ".concat(log)));
        p.setBairro(bai);

    }

    public List<UnidadeFederativa> getListaUnidadeFederativas() {
        return listaUnidadeFederativas;
    }

    public void setListaUnidadeFederativas(List<UnidadeFederativa> listaUnidadeFederativas) {
        this.listaUnidadeFederativas = listaUnidadeFederativas;
    }

    public List<Cidade> getListaCidades() {
        return listaCidades;
    }

    public void setListaCidades(List<Cidade> listaCidades) {
        this.listaCidades = listaCidades;
    }

    public UnidadeFederativa getFederativa() {
        return federativa;
    }

    public void setFederativa(UnidadeFederativa federativa) {
        this.federativa = federativa;
    }

}
