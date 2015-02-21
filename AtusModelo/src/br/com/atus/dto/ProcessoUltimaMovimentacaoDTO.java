/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.dto;

import br.com.atus.modelo.Movimentacao;
import br.com.atus.modelo.Processo;
import java.io.Serializable;
import java.util.Objects;

/**
 *Clase utilizada no relatorio de processos por colaborador
 * @author Ari
 */

public class ProcessoUltimaMovimentacaoDTO implements Serializable{
    private Processo processo;
    private Movimentacao movimentacao;

    public Processo getProcesso() {
        return processo;
    }

    public void setProcesso(Processo processo) {
        this.processo = processo;
    }

    public Movimentacao getMovimentacao() {
        return movimentacao;
    }

    public void setMovimentacao(Movimentacao movimentacao) {
        this.movimentacao = movimentacao;
    }

    public ProcessoUltimaMovimentacaoDTO(Processo processo, Movimentacao movimentacao) {
        this.processo = processo;
        this.movimentacao = movimentacao;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.processo);
        hash = 41 * hash + Objects.hashCode(this.movimentacao);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ProcessoUltimaMovimentacaoDTO other = (ProcessoUltimaMovimentacaoDTO) obj;
        if (!Objects.equals(this.processo, other.processo)) {
            return false;
        }
        if (!Objects.equals(this.movimentacao, other.movimentacao)) {
            return false;
        }
        return true;
    }
    
    
}
