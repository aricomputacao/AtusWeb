/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.dto;

import br.com.atus.processo.modelo.Movimentacao;
import br.com.atus.processo.modelo.Processo;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * Clase utilizada no relatorio de processos por colaborador
 *
 * @author Ari
 */
public class ProcessoUltimaMovimentacaoDTO implements Comparable<ProcessoUltimaMovimentacaoDTO>, Serializable {

    private Processo processo;
    private Movimentacao movimentacao;
    private Date prazo;

    public ProcessoUltimaMovimentacaoDTO() {
    }

    public Date getPrazo() {
        Calendar c = Calendar.getInstance();
        if (movimentacao != null) {

            if (processo.getFase() == null) {
                return null;
            } else {
                c.setTime(movimentacao.getDataMovimentacao());
                c.set(Calendar.DAY_OF_MONTH, processo.getFase().getPrazo());
                prazo = new Date(c.getTimeInMillis());
                return prazo;
            }
        } else {
            return null;

        }

    }

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

    @Override
    public int compareTo(ProcessoUltimaMovimentacaoDTO o) {
        if (this.movimentacao != null) {
            if (this.movimentacao.getDataMovimentacao().before(o.getMovimentacao().getDataMovimentacao())) {
                return -1;
            }
            if (this.movimentacao.getDataMovimentacao().after(o.getMovimentacao().getDataMovimentacao())) {
                return 1;
            }
        }

        return 0;

    }

}
