/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.managedbean;

import br.com.atus.controller.MovimentacaoController;
import br.com.atus.controller.ProcessoController;
import br.com.atus.modelo.Movimentacao;
import br.com.atus.modelo.Processo;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * Bean utilizado para rodas alguns metodos para correções ou algo necessario
 *
 * @author Ari
 */
@ViewScoped
@ManagedBean
public class UtilitariosMB {

    @EJB
    private ProcessoController processoController;
    @EJB
    private MovimentacaoController movimentacaoController;

    public void setarFaseProcesso() throws Exception {
        List<Processo> processos = new ArrayList<>();
        processos = processoController.listarFaseNula();
        for (Processo p : processos) {
            Movimentacao m = new Movimentacao();
            m = movimentacaoController.ultimaMovimentacao(p);
            if (m != null) {
                p.setFase(m.getFaseNova());
                processoController.atualizar(p);
            }

        }

    }

}
