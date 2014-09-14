/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.managedbean;

import br.com.atus.controller.MateriaController;
import br.com.atus.modelo.Materia;
import br.com.atus.modelo.Orgao;
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
 * @author Ari
 */
@ManagedBean
@ViewScoped
public class MateriaMB extends BeanGenerico<Materia> implements Serializable {

    @Inject
    private NavegacaoMB navegacaoMB;
    @EJB
    private MateriaController controller;
    private Materia materia;
    private List<Materia> listaMaterias;

    public MateriaMB() {
        super(Materia.class);
    }

    @PostConstruct
    public void init() {
        materia = (Materia) navegacaoMB.getRegistroMapa("materia", new Materia());
        listaMaterias = new ArrayList<>();
    }

    public void salvar() {
        try {
            controller.salvarouAtualizar(materia);
            init();
            MenssagemUtil.addMessageInfo(NavegacaoMB.getMsg("salvar", MenssagemUtil.MENSAGENS));
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("falha", MenssagemUtil.MENSAGENS));
            Logger.getLogger(MateriaMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void listar() {
        try {
            if (getValorBusca() == null || getValorBusca().equals("")) {
                listaMaterias = controller.listarTodos("nome");
            } else {
                listaMaterias = controller.listarLike(getCampoBusca(), getValorBusca());
                MenssagemUtil.addMessageWarn(NavegacaoMB.getMsg("consulta.vazia", MenssagemUtil.MENSAGENS));

            }
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("consulta.vazia", MenssagemUtil.MENSAGENS));
            Logger.getLogger(MateriaMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void excluir(Materia ee) {
        try {
            ee = controller.gerenciar(ee.getId());
            controller.excluir(ee);
            listaMaterias.remove(ee);
            MenssagemUtil.addMessageInfo(NavegacaoMB.getMsg("excluir", MenssagemUtil.MENSAGENS));

        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("excluir.falha", MenssagemUtil.MENSAGENS));
            Logger.getLogger(MateriaMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void imprimir() {
        if (!listaMaterias.isEmpty()) {
            Map<String, Object> m = new HashMap<>();
            byte[] rel = new AssistentedeRelatorio().relatorioemByte(listaMaterias, m, "WEB-INF/relatorios/rel_materia.jasper", "Relatório de Materias");
            RelatorioSession.setBytesRelatorioInSession(rel);
        }

    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public List<Materia> getListaMaterias() {
        return listaMaterias;
    }

    public void setListaMaterias(List<Materia> listaMaterias) {
        this.listaMaterias = listaMaterias;
    }
}
