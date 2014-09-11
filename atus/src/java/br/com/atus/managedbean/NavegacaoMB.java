/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.managedbean;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
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
    //-----variaveis para controlar os btn do menu do panel-------//
    private boolean novo;
    private boolean consultar;
    private boolean salvar;
    private boolean imprimir;
    private boolean limpar;
    //-----------------------------------------------------------//

    /**
     * Variaveis para renderizas consultar ou cadastro true = cadastro false =
 consultar
     */
    private boolean renderPainelCadastro;

    public NavegacaoMB() {
        map = new HashMap<>();
    }

    /**
     * Renderiza os btn do painel
     */
    public void cadastro() {
        renderPainelCadastro = true;
        novo = true;
        consultar = true;
        salvar = true;
        imprimir = false;
        limpar = true;

    }

    /**
     * Renderiza os btn do painel
     */
    public void consulta() {
        renderPainelCadastro = false;
        novo = true;
        consultar = false;
        salvar = false;
        imprimir = true;
        limpar = true;

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
        redirecionarCadastro(pag);
    }

    /**
     * Retorna a mensagem do bundle
     *
     * @param messageId
     * @param arquivo
     * @return
     */
    public static String getMsg(String messageId, String arquivo) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String msg = "";
        Locale locale = facesContext.getViewRoot().getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle(arquivo, locale);
        try {
            msg = bundle.getString(messageId);
        } catch (Exception e) {
        }
        return msg;
    }
    
    /**
     * Esse método redireciona para uma página especifica
     *
     * @param pag
     */
    public void redirecionarCadastro(String pag) {
        try {
            String modulo = "";

            switch (pag.substring(0, 3)) {
                case ("cad"):
                    modulo = "01";
                    break;

            }
            //renderiza atela de cadastro
            cadastro();
            redirecionarPagina(getNomeSistema().concat("/") + modulo.concat("/") + pag.substring(4) + ".xhtml");
        } catch (Exception e) {
            Logger.getLogger(NavegacaoMB.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * Esse método redireciona para uma página especifica
     *
     * @param pag
     */
    public void redirecionarConsulta(String pag) {
        try {
            String modulo = "";

            switch (pag.substring(0, 3)) {
                case ("cad"):
                    modulo = "01";
                    break;

            }
            //renderiza atela de cadastro
            consulta();
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

    public boolean isNovo() {
        return novo;
    }

    public void setNovo(boolean novo) {
        this.novo = novo;
    }

    public boolean isConsultar() {
        return consultar;
    }

    public void setConsultar(boolean consulta) {
        this.consultar = consulta;
    }

    public boolean isSalvar() {
        return salvar;
    }

    public void setSalvar(boolean salvar) {
        this.salvar = salvar;
    }

    public boolean isImprimir() {
        return imprimir;
    }

    public void setImprimir(boolean imprimir) {
        this.imprimir = imprimir;
    }

    public boolean isLimpar() {
        return limpar;
    }

    public void setLimpar(boolean limpar) {
        this.limpar = limpar;
    }

    public boolean isRenderPainelCadastro() {
        return renderPainelCadastro;
    }

    public void setRenderPainelCadastro(boolean renderPainelCadastro) {
        this.renderPainelCadastro = renderPainelCadastro;
    }
    
    
}
