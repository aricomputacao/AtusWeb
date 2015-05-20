/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.financeiro.dto;

import br.com.atus.financeiro.modelo.ContaReceber;
import br.com.atus.financeiro.modelo.ParcelasReceber;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author ari
 */
public class ContaReceberParcelasDTO implements Serializable {

    private ContaReceber contaReceber;
    private List<ParcelasReceber> parcelasRecebers;

    public ContaReceberParcelasDTO(ContaReceber contaReceber, List<ParcelasReceber> parcelasRecebers) {
        this.contaReceber = contaReceber;
        this.parcelasRecebers = parcelasRecebers;
    }

    public ContaReceberParcelasDTO() {
    }

    public ContaReceber getContaReceber() {
        return contaReceber;
    }

    public void setContaReceber(ContaReceber contaReceber) {
        this.contaReceber = contaReceber;
    }

    public List<ParcelasReceber> getParcelasRecebers() {
        return parcelasRecebers;
    }

    public void setParcelasRecebers(List<ParcelasReceber> parcelasRecebers) {
        this.parcelasRecebers = parcelasRecebers;
    }

    public BigDecimal getTotalPago() {
        BigDecimal vlPg = new BigDecimal(BigInteger.ZERO);
        for (ParcelasReceber pa : parcelasRecebers) {
            if (pa.getDataPagamento() != null) {
             vlPg =    vlPg.add(pa.getValorPago());
            }
        }
        return vlPg;
    }

    public BigDecimal getTotalAberto() {
        BigDecimal vlPg = new BigDecimal(BigInteger.ZERO);
        for (ParcelasReceber pa : parcelasRecebers) {
            if (pa.getDataPagamento() == null) {
             vlPg =    vlPg.add(pa.getValorParcela());
            }
        }
        return vlPg;
    }

    public BigDecimal getTotalVencido() {
        BigDecimal vlPg = new BigDecimal(BigInteger.ZERO);
        for (ParcelasReceber pa : parcelasRecebers) {
            if (pa.getDataPagamento() == null && pa.getVencimento().before(new Date())) {
             vlPg =    vlPg.add(pa.getValorParcela());
            }
        }
        return vlPg;
    }

    public BigDecimal getTotal() {
        BigDecimal vlPg = new BigDecimal(BigInteger.ZERO);
        for (ParcelasReceber pa : parcelasRecebers) {
          vlPg =  vlPg.add(pa.getValorParcela());
        }
        return vlPg;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.contaReceber);
        hash = 37 * hash + Objects.hashCode(this.parcelasRecebers);
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
        final ContaReceberParcelasDTO other = (ContaReceberParcelasDTO) obj;
        if (!Objects.equals(this.contaReceber, other.contaReceber)) {
            return false;
        }
        if (!Objects.equals(this.parcelasRecebers, other.parcelasRecebers)) {
            return false;
        }
        return true;
    }

}
