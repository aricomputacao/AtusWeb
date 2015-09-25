/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.financeiro.dto;

import br.com.atus.financeiro.modelo.ParcelasReceber;
import br.com.atus.financeiro.modelo.Recibo;
import br.com.atus.cadastro.modelo.Colaborador;
import br.com.atus.processo.modelo.Processo;
import br.com.atus.cadastro.modelo.Usuario;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author ari
 */
public class CaixaColaboradorParcelasDTO implements Serializable{
    
    private Processo processo;
    private ParcelasReceber parcelasReceber;
    private Date dataPagamentoColaborador;
    private Colaborador colaborador;
    private Usuario usuarioQuePagou;
    private Recibo recibo;

    public Processo getProcesso() {
        return processo;
    }

    public void setProcesso(Processo processo) {
        this.processo = processo;
    }

    public ParcelasReceber getParcelasReceber() {
        return parcelasReceber;
    }

    public void setParcelasReceber(ParcelasReceber parcelasReceber) {
        this.parcelasReceber = parcelasReceber;
    }

    public Date getDataPagamentoColaborador() {
        return dataPagamentoColaborador;
    }

    public void setDataPagamentoColaborador(Date dataPagamentoColaborador) {
        this.dataPagamentoColaborador = dataPagamentoColaborador;
    }

    public Colaborador getColaborador() {
        return colaborador;
    }

    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
    }

    public Usuario getUsuarioQuePagou() {
        return usuarioQuePagou;
    }

    public void setUsuarioQuePagou(Usuario usuarioQuePagou) {
        this.usuarioQuePagou = usuarioQuePagou;
    }

    public Recibo getRecibo() {
        return recibo;
    }

    public void setRecibo(Recibo recibo) {
        this.recibo = recibo;
    }
    
  
   

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.processo);
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
        final CaixaColaboradorParcelasDTO other = (CaixaColaboradorParcelasDTO) obj;
        if (!Objects.equals(this.processo, other.processo)) {
            return false;
        }
        return true;
    }
    
    
    
}
