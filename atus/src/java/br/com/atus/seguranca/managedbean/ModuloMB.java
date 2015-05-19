/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.seguranca.managedbean;

import br.com.atus.cadastro.managedbean.ClienteMB;
import br.com.atus.util.managedbean.BeanGenerico;
import br.com.atus.util.managedbean.NavegacaoMB;
import br.com.atus.seguranca.controller.ModuloController;
import br.com.atus.modelo.Modulo;
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
 * @author gilmario
 */
@ViewScoped
@ManagedBean
public class ModuloMB extends BeanGenerico<Modulo> implements Serializable {

    @EJB
    private ModuloController controller;
    private Modulo modulo;
    private List<Modulo> lista;
    @Inject
    private NavegacaoMB navegacaoMB;

    public ModuloMB() {
        super(Modulo.class);
    }

    @PostConstruct
    public void init() {
        modulo = (Modulo) navegacaoMB.getRegistroMapa("modulo", new Modulo());
        lista = new ArrayList<>();
    }

    public void salvar() {
        try {
            if (modulo.getId() == null) {
                controller.salvar(modulo);
            } else {
                controller.atualizar(modulo);
            }
            init();
            MenssagemUtil.addMessageInfo(NavegacaoMB.getMsg("salvar", MenssagemUtil.MENSAGENS));
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("falha", MenssagemUtil.MENSAGENS), ex, "Modulo");
            Logger.getLogger(ClienteMB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void listar() {
        try {
            lista = controller.consultarLike(getCampoBusca(), getValorBusca());
            if (lista.isEmpty()) {
                MenssagemUtil.addMessageInfo(NavegacaoMB.getMsg("consulta.vazia", MenssagemUtil.MENSAGENS));
            }
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("consulta.vazia", MenssagemUtil.MENSAGENS));
            Logger.getLogger(ClienteMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Modulo getModulo() {
        return modulo;
    }

    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }

    public List<Modulo> getLista() {
        return lista;
    }

    public void setLista(List<Modulo> lista) {
        this.lista = lista;
    }

}
