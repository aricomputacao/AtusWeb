/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.managedbean;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Ari
 */
@Named
@SessionScoped
public class NavegacaoMB implements Serializable {

    private final Map<String, Object> map;
    private String pagina;
    private String mnemonicoSistema;

    public NavegacaoMB() {
        map = new HashMap<>();
    }

    /**
     * Esse metodo redireciona e passa um parametro para o bean de destino no
     * caso um objeto de um determinado modelo a ser editado
     *
     * @param pag
     * @param key - chave de um valor a ser adicionado na sessão
     * @param valor - valor a ser adiocionado na sessão
     */
    public void redirecionar(String pag, String key, Object valor) {
        try {
            map.put(key, valor);
        } catch (Exception e) {
            Logger.getLogger(NavegacaoMB.class.getName()).log(Level.SEVERE, null, e);
        }
        redirecionar(pag);
    }

    /**
     * Esse método redireciona para uma página especifica
     *
     * @param mnemonico
     */
    public void redirecionar(String pag) {
        try {
            String modulo = "";

            switch (pag.substring(0, 3)) {
                case ("cad"):
                    modulo = "01";
                    break;

            }
            System.out.println(pag);

            System.out.println(pag.substring(0, 3));

            System.out.println(getNomeSistema().concat("/") + modulo.concat("/") + pag.substring(4));
            redirecionarPagina(getNomeSistema().concat("/") + modulo.concat("/") + pag.substring(4) + ".xhtml");
        } catch (Exception e) {
            Logger.getLogger(NavegacaoMB.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void redirecionarPagina(String pag) {

        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(pag);

//        return pag.concat("?faces-redirect=true");
        } catch (IOException ex) {
            Logger.getLogger(NavegacaoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Pagar o nome do sistema
     *
     * @return
     */
    public String getNomeSistema() {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
    }

    /**
     * Geters e Setters das propriedades sem nenhuma alteração
     *
     * @param key
     * @param def
     * @return
     */
    public Object getRegistroMapa(String key, Object def) {
        if (map.containsKey(key)) {
            return map.remove(key);
        } else {
            return def;
        }

    }

    /**
     * Geters e Setters das propriedades sem nenhuma alteração
     *
     * @return
     */
    public String getPagina() {
        return pagina;
    }

    /**
     * Geters e Setters das propriedades sem nenhuma alteração
     *
     * @param pagina
     */
    public void setPagina(String pagina) {
        this.pagina = pagina;
    }

    /**
     *
     */
    public void index() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(getNomeSistema());
        } catch (IOException ex) {
            Logger.getLogger(NavegacaoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void logout() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(getNomeSistema() + "/j_spring_security_logout");

        } catch (IOException ex) {
            Logger.getLogger(NavegacaoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
