/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.managedbean;

import br.com.atus.controller.CidadeController;
import br.com.atus.controller.ColaboradorController;
import br.com.atus.controller.UnidadeFederativaController;
import br.com.atus.enumerated.Sexo;
import br.com.atus.enumerated.TipoPessoa;
import br.com.atus.modelo.Cidade;
import br.com.atus.modelo.Colaborador;
import br.com.atus.modelo.Pessoa;
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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author ari
 */
@ManagedBean
@ViewScoped
public class ColaboradorMB extends BeanGenerico<Colaborador> implements Serializable {

    @Inject
    private NavegacaoMB navegacaoMB;
    @EJB
    private ColaboradorController controller;
    @EJB
    private CidadeController cidadeController;
    private List<Colaborador> listaColaboradors;
    private Colaborador colaborador;
    private UnidadeFederativa uf;
    private List<Cidade> listaCidades;

    public ColaboradorMB() {
        super(Colaborador.class);
    }

    @PostConstruct
    public void init() {
        try {
            listaColaboradors = controller.listarTodos("pessoa.nome");
            listaCidades = new ArrayList<>();
            uf = new UnidadeFederativa();
            listaCidades = cidadeController.listarTodos("nome");
            colaborador = (Colaborador) navegacaoMB.getRegistroMapa("colaborador", new Colaborador());
            if (colaborador.getId() == null) {
                colaborador.setPessoa(new Pessoa());
                colaborador.getPessoa().setTipoPessoa(TipoPessoa.PF);
//                colaborador.getPessoa().getCidade().setUnidadeFederativa(new UnidadeFederativa());

            } else {
                uf = colaborador.getPessoa().getCidade().getUnidadeFederativa();
            }
        } catch (Exception ex) {
            Logger.getLogger(ColaboradorMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void salvar() {
        try {
            controller.atualizar(colaborador);
            init();
            MenssagemUtil.addMessageInfo(NavegacaoMB.getMsg("salvar", MenssagemUtil.MENSAGENS));

        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("falha", MenssagemUtil.MENSAGENS), ex, "Advogado");
            Logger.getLogger(ColaboradorMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void listar() {
        try {
            if (getValorBusca() == null || getValorBusca().equals("")) {
                listaColaboradors = controller.listarTodos("nome");
            } else {
                listaColaboradors = controller.listarLike("nome", getValorBusca());

            }
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("consulta.vazia", MenssagemUtil.MENSAGENS));
            Logger.getLogger(ColaboradorMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void excluir(Colaborador ee) {
        try {
            ee = controller.gerenciar(ee.getId());
            controller.excluir(ee);
            listaColaboradors.remove(ee);
            MenssagemUtil.addMessageInfo(NavegacaoMB.getMsg("excluir", MenssagemUtil.MENSAGENS));

        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("excluir.falha", MenssagemUtil.MENSAGENS));
            Logger.getLogger(ColaboradorMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void listarCidadesUf() {
        listaCidades = cidadeController.listaPorUf(uf);
    }

    public void imprimir() {
        if (!listaColaboradors.isEmpty()) {
            Map<String, Object> m = new HashMap<>();
            byte[] rel = new AssistentedeRelatorio().relatorioemByte(listaColaboradors, m, "WEB-INF/relatorios/rel_colaboradores.jasper", "Relatório de Colaboradores");
            RelatorioSession.setBytesRelatorioInSession(rel);
        }

    }

    public Sexo[] listaSexo() {
        return Sexo.values();
    }

    public List<Colaborador> getListaColaboradors() {
        return listaColaboradors;
    }

    public void setListaColaboradors(List<Colaborador> listaColaboradors) {
        this.listaColaboradors = listaColaboradors;
    }

    public Colaborador getColaborador() {
        return colaborador;
    }

    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
    }

    public UnidadeFederativa getUf() {
        return uf;
    }

    public void setUf(UnidadeFederativa uf) {
        this.uf = uf;
    }

    public List<Cidade> getListaCidades() {
        return listaCidades;
    }

    public void setListaCidades(List<Cidade> listaCidades) {
        this.listaCidades = listaCidades;
    }

}
