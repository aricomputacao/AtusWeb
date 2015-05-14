/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.managedbean;

import br.com.atus.controller.AdvogadoController;
import br.com.atus.controller.ClienteController;
import br.com.atus.controller.ColaboradorController;
import br.com.atus.controller.PermissaoController;
import br.com.atus.controller.UsuarioController;
import br.com.atus.enumerated.Perfil;
import br.com.atus.modelo.Advogado;
import br.com.atus.modelo.Cliente;
import br.com.atus.modelo.Colaborador;
import br.com.atus.modelo.Permissao;
import br.com.atus.modelo.Usuario;
import br.com.atus.util.ContextoAplicacao;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Ari
 */
@Named
@SessionScoped
public class NavegacaoMB implements Serializable {

    @EJB
    private UsuarioController usuarioController;
    @EJB
    private AdvogadoController advogadoController;
    @EJB
    private ClienteController clienteController;
    @EJB
    private ColaboradorController colaboradorController;
    @EJB
    private PermissaoController permissaoController;
    private final Map<String, Object> map;
    private final Map<String, Permissao> menu;
    private List<Permissao> listaPermissaos;

    private String pagina;

    //-----variaveis para controlar os btn do menu do panel-------//
    private boolean novo;
    private boolean consultar;
    private boolean salvar;
    private boolean imprimir;
    private boolean limpar;
    //-----------------------------------------------------------//
    private Usuario usuarioLogado;
    private Advogado advogado;
    private Cliente cliente;
    private Colaborador colaborador;
    /**
     * Variaveis para renderizas consultar ou cadastro true = cadastro false =
     * consultar
     */
    private boolean renderPainelCadastro;
    //Variavel para checar se o usuario é comum ou colaborador
    private boolean ehUsuarioDoEscritorio;

    @Inject
    private void init() {
        try {
        
            usuarioLogado = usuarioController.usuarioLogin(ContextoAplicacao.getContexto().getRemoteUser());
            ehUsuarioDoEscritorio = usuarioController.ehUsuarioDoEscritorio(usuarioLogado);
            listaPermissaos = permissaoController.listar(usuarioLogado);
            popularMenu();
            if (usuarioLogado.getPerfil().equals(Perfil.ADVOGADO)) {
                advogado = advogadoController.carregar(usuarioLogado.getReferencia().intValue());

            }
            if (usuarioLogado.getPerfil().equals(Perfil.CLIENTE)) {
                cliente = clienteController.carregar(usuarioLogado.getReferencia());
            }
            if (usuarioLogado.getPerfil().equals(Perfil.COLABORADOR)) {
                colaborador = colaboradorController.carregar(usuarioLogado.getReferencia());
            }
        } catch (Exception ex) {
            Logger.getLogger(NavegacaoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void popularMenu() {
        for (Permissao p : listaPermissaos) {
            menu.put(p.getTarefa().getNome(), p);
        }
    }

    public boolean permissaoExiste(String tarNome) {
        if (menu.containsKey(tarNome.substring(4))) {
            return true;
        } else {
            return false;
        }
    }

    public boolean permissaoModuloCadastro() {
        for (Permissao p : listaPermissaos) {
            if (p.getTarefa().getModulo().getMnemonico().equals("01")) {
                return true;
            }
        }
        return false;
    }

    public boolean permissaoModuloProcesso() {
        for (Permissao p : listaPermissaos) {
            if (p.getTarefa().getModulo().getMnemonico().equals("02")) {
                return true;
            }
        }
        return false;
    }

    public boolean permissaoModuloSeguranca() {
        for (Permissao p : listaPermissaos) {
            if (p.getTarefa().getModulo().getMnemonico().equals("03")) {
                return true;
            }
        }
        return false;
    }

    public boolean permissaoModuloRelatorio() {
        for (Permissao p : listaPermissaos) {
            if (p.getTarefa().getModulo().getMnemonico().equals("04")) {
                return true;
            }
        }
        return false;
    }

    public boolean permissaoModuloUtilitarios() {
        for (Permissao p : listaPermissaos) {
            if (p.getTarefa().getModulo().getMnemonico().equals("05")) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param tarNome
     * @return
     */
    public boolean permissaoIncluir(String tarNome) {
        if (menu.containsKey(tarNome.substring(4))) {
            return menu.get(tarNome.substring(4)).isIncluir();
        } else {
            return false;
        }
    }

    public boolean permissaoConsultar(String tarNome) {
        if (menu.containsKey(tarNome.substring(4))) {
            return menu.get(tarNome.substring(4)).isConsultar();
        } else {
            return false;
        }
    }

    public boolean permissaoEditar(String tarNome) {
        if (menu.containsKey(tarNome.substring(4))) {
            return menu.get(tarNome.substring(4)).isEditar();
        } else {
            return false;
        }
    }

    public boolean permissaoExcluir(String tarNome) {
        if (menu.containsKey(tarNome.substring(4))) {
            return menu.get(tarNome.substring(4)).isExcluir();
        } else {
            return false;
        }
    }

    public NavegacaoMB() {
        map = new HashMap<>();
        menu = new HashMap<>();
    }

    /**
     * Renderiza os btn do painel
     */
    public void cadastro() {
        renderPainelCadastro = true;
        novo = false;
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
     * Esse metodo redireciona e passa um parametro para o bean de destino no
     * caso um objeto de um determinado modelo a ser editado
     *
     * @param pag
     * @param key - chave de um valor a ser adicionado na sessão
     * @param valor - valor a ser adiocionado na sessão
     */
    public void redirecionarConsulta(String pag, String key, Object valor) {
        try {
            map.put(key, valor);
        } catch (Exception e) {
            Logger.getLogger(NavegacaoMB.class.getName()).log(Level.SEVERE, null, e);
        }
        redirecionarConsulta(pag);
    }
    
    /**
     * Retorna a mensagem do bundle
     *
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
     * Retorna a mensagem do bundle alterando a tag de parametro
     *
     *
     * @param messageId
     * @param arquivo
     * @param parametro
     * @return
     */
    public static String getMsgParametro(String messageId, String arquivo, String parametro) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String msg = "";
        Locale locale = facesContext.getViewRoot().getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle(arquivo, locale);
        try {
            msg = bundle.getString(messageId);
            msg = msg.replace("@", parametro);
            return msg;

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
                case ("pro"):
                    modulo = "02";
                    break;
                case ("seg"):
                    modulo = "03";
                    break;
                case ("rel"):
                    modulo = "04";
                    break;
                case ("uti"):
                    modulo = "05";
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
                case ("pro"):
                    modulo = "02";
                    break;
                case ("seg"):
                    modulo = "03";
                    break;
                case ("rel"):
                    modulo = "04";
                    break;
                case ("uti"):
                    modulo = "05";
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

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public void setUsuarioLogado(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }

    public Advogado getAdvogado() {
        return advogado;
    }

    public void setAdvogado(Advogado advogado) {
        this.advogado = advogado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Colaborador getColaborador() {
        return colaborador;
    }

    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
    }

    public boolean isEhUsuarioDoEscritorio() {
        return ehUsuarioDoEscritorio;
    }

   

}
