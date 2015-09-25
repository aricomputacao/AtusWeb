/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.util.managedbean;

import br.com.atus.processo.controller.MovimentacaoController;
import br.com.atus.processo.controller.ProcessoController;
import br.com.atus.processo.modelo.Movimentacao;
import br.com.atus.processo.modelo.Processo;
import br.com.atus.util.MenssagemUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        processos = processoController.listarFase();
        for (Processo p : processos) {
            Movimentacao m = new Movimentacao();
            m = movimentacaoController.ultimaMovimentacao(p);
            if (m != null) {
                p.setFase(m.getFaseNova());
                p.setMotivoFase(m.getMotivo());
                processoController.atualizar(p);
            }

        }

    }

    public void addMovimentacaoProcesso() {
        try {
            processoController.criarMovimentacaoParaProcesso();
            MenssagemUtil.addMessageInfo(NavegacaoMB.getMsg("procedimento_sucesso", MenssagemUtil.MENSAGENS));
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("procedimento_falha", MenssagemUtil.MENSAGENS));

            Logger.getLogger(UtilitariosMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
