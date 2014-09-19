/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.managedbean;

import br.com.atus.controller.CEPWebService;
import br.com.atus.controller.CidadeController;
import br.com.atus.controller.ClienteController;
import br.com.atus.controller.NacionalidadeController;
import br.com.atus.controller.ProfissaoController;
import br.com.atus.controller.TipoTratamentoController;
import br.com.atus.controller.UnidadeFederativaController;
import br.com.atus.enumerated.EstadoCivil;
import br.com.atus.enumerated.Sexo;
import br.com.atus.enumerated.TipoPessoa;
import br.com.atus.modelo.Cidade;
import br.com.atus.modelo.Cliente;
import br.com.atus.modelo.Nacionalidade;
import br.com.atus.modelo.Pessoa;
import br.com.atus.modelo.Profissao;
import br.com.atus.modelo.TipoTratamento;
import br.com.atus.modelo.UnidadeFederativa;
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
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 *
 * @author ari
 */
@ManagedBean
@ViewScoped
public class ClienteMB extends BeanGenerico<Cliente> implements Serializable {

    @Inject
    private NavegacaoMB navegacaoMB;
    @EJB
    private ClienteController controller;
    @EJB
    private UnidadeFederativaController unidadeFederativaController;
    @EJB
    private CidadeController cidadeController;
    @EJB
    private NacionalidadeController nacionalidadeController;
    @EJB
    private TipoTratamentoController tipoTratamentoController;
    @EJB
    private ProfissaoController profissaoController;
    private List<UnidadeFederativa> listaUnidadeFederativas;
    private List<Cidade> listaCidades;
    private List<Cliente> listaClientes;
    private List<Nacionalidade> listaNacionalidades;
    private List<TipoTratamento> listaTratamentos;
    private List<Profissao> listaProfissaos;
    private Cliente cliente;
    private UnidadeFederativa uf;
    private String cep;

    public ClienteMB() {
        super(Cliente.class);
    }

    @PostConstruct
    public void init() {
        try {
            cliente = (Cliente) navegacaoMB.getRegistroMapa("cliente", new Cliente());
            if (cliente.getId() == null) {
                cliente.setPessoa(new Pessoa());
            } else {
                uf = cliente.getPessoa().getCidade().getUnidadeFederativa();
                listaCidades = cidadeController.listaPorUf(uf);

            }
            uf = new UnidadeFederativa();
            listaClientes = new ArrayList<>();

            listaNacionalidades = nacionalidadeController.listarTodos("nome");
            listaTratamentos = tipoTratamentoController.listarTodos("nome");
            listaProfissaos = profissaoController.listarTodos("nome");
            listaUnidadeFederativas = unidadeFederativaController.listarTodos("nome");

        } catch (Exception ex) {
            Logger.getLogger(ClienteMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void encontraCEP(String cep, String ufe, String cid, String tpLog, String log, String bai) {
        cliente.getPessoa().setCep(cep);
        uf = unidadeFederativaController.buscaAbreviacao(ufe.trim());
        listaCidades = cidadeController.listaPorUf(uf);
        cliente.getPessoa().setCidade(cidadeController.buscarUfNome(uf, cid));
        cliente.getPessoa().setLogradouro(tpLog.concat(" ".concat(log)));
        cliente.getPessoa().setBairro(bai);

    }

    public void setarProf(Profissao p) {
        cliente.setProfissao(p);
    }

    public void setarCliente(Cliente c) {
        cliente = c;
    }

    public void salvar() {
        try {
            controller.atualizar(cliente);
            init();
            MenssagemUtil.addMessageInfo(NavegacaoMB.getMsg("salvar", MenssagemUtil.MENSAGENS));

        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("falha", MenssagemUtil.MENSAGENS), ex, "Cliente");
            Logger.getLogger(ClienteMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void listar() {
        try {
            if (getValorBusca() == null || getValorBusca().equals("")) {
                listaClientes = controller.listarTodos("nome");
            } else {
                listaClientes = controller.listarLike("nome", getValorBusca());

            }
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("consulta.vazia", MenssagemUtil.MENSAGENS));
            Logger.getLogger(ClienteMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void excluir(Cliente ee) {
        try {
            ee = controller.gerenciar(ee.getId());
            controller.excluir(ee);
            listaClientes.remove(ee);
            MenssagemUtil.addMessageInfo(NavegacaoMB.getMsg("excluir", MenssagemUtil.MENSAGENS));

        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("excluir.falha", MenssagemUtil.MENSAGENS));
            Logger.getLogger(ClienteMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void listarCidade() {
        listaCidades = cidadeController.listaPorUf(uf);
    }

    public void setarPJ() {
        cliente.getPessoa().setTipoPessoa(TipoPessoa.PJ);
    }

    public void setarPF() {
        cliente.getPessoa().setTipoPessoa(TipoPessoa.PF);
    }

    public TipoPessoa pessoaFisica() {
        return TipoPessoa.PF;
    }

    public void imprimir() {
        if (!listaClientes.isEmpty()) {
            Map<String, Object> m = new HashMap<>();
            byte[] rel = new AssistentedeRelatorio().relatorioemByte(listaClientes, m, "WEB-INF/relatorios/rel_cliente.jasper", "Relatório de Clientes");
            RelatorioSession.setBytesRelatorioInSession(rel);
        }

    }

    public Sexo[] listaSexo() {
        return Sexo.values();
    }

    public EstadoCivil[] listaEstadoCivil() {
        return EstadoCivil.values();
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

    public List<Cliente> getListaClientes() {
        return listaClientes;
    }

    public void setListaClientes(List<Cliente> listaClientes) {
        this.listaClientes = listaClientes;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Nacionalidade> getListaNacionalidades() {
        return listaNacionalidades;
    }

    public void setListaNacionalidades(List<Nacionalidade> listaNacionalidades) {
        this.listaNacionalidades = listaNacionalidades;
    }

    public List<TipoTratamento> getListaTratamentos() {
        return listaTratamentos;
    }

    public void setListaTratamentos(List<TipoTratamento> listaTratamentos) {
        this.listaTratamentos = listaTratamentos;
    }

    public List<Profissao> getListaProfissaos() {
        return listaProfissaos;
    }

    public void setListaProfissaos(List<Profissao> listaProfissaos) {
        this.listaProfissaos = listaProfissaos;
    }

    public UnidadeFederativa getUf() {
        return uf;
    }

    public void setUf(UnidadeFederativa uf) {
        this.uf = uf;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

}