/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.financeiro.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 *
 * @author Ari
 */
@Entity
@Table(name = "parcela_receber", schema = "financeiro", uniqueConstraints = @UniqueConstraint(columnNames = {"ctr_id", "par_numero"}))
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ParcelasReceber implements Comparable<ParcelasReceber>, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "par_id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "ctr_id", nullable = false, referencedColumnName = "ctr_id")
    private ContaReceber contaReceber;

  
    @NotNull
    @Min(value = 0)
    @Column(name = "par_valor_parcela", nullable = false)
    private BigDecimal valorParcela;

    @Min(value = 0)
    @Column(name = "par_valor_recebido")
    private BigDecimal valorPago;

    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "par_vencimento", nullable = false)
    private Date vencimento;

    @Temporal(TemporalType.DATE)
    @Column(name = "par_data_pagamento")
    private Date dataPagamento;

    @NotNull
    @Column(name = "par_numero", nullable = false)
    private int numeroDaParcela;

    @Column(name = "par_observacao", length = 1024)
    private String observcao;

  

   
    //finalizar parcela
    public BigDecimal getValorDonoDoProcesso() {
        BigDecimal percent = this.contaReceber.getCooptacao().getPercentDono().divide(BigDecimal.valueOf(100)).setScale(2,RoundingMode.DOWN);
        return this.valorPago.multiply(percent).setScale(2,RoundingMode.DOWN);
    }

    public BigDecimal getValorSocioDoProcesso() {
        BigDecimal percent = this.contaReceber.getCooptacao().getPercentSocio().divide(BigDecimal.valueOf(100)).setScale(2,RoundingMode.DOWN);
        return valorPago.multiply(percent).setScale(2,RoundingMode.DOWN);
    }

    public BigDecimal getValorDoColaborador() {
        BigDecimal percent = this.contaReceber.getPercentualColaborador().divide(BigDecimal.valueOf(100)).setScale(2,RoundingMode.DOWN);
        return this.valorPago.multiply(percent).setScale(2,RoundingMode.DOWN);
    }

    public String getNomeDoCliente() {
        return this.contaReceber.getNomeDoCliente();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ContaReceber getContaReceber() {
        return contaReceber;
    }

    public void setContaReceber(ContaReceber contaReceber) {
        this.contaReceber = contaReceber;
    }

   

    public BigDecimal getValorParcela() {
        return valorParcela;
    }

    public void setValorParcela(BigDecimal valorParcela) {
        this.valorParcela = valorParcela;
    }

    public BigDecimal getValorPago() {
        return valorPago;
    }

    public void setValorPago(BigDecimal valorPago) {
        this.valorPago = valorPago;
    }

    public Date getVencimento() {
        return vencimento;
    }

    public void setVencimento(Date vencimento) {
        this.vencimento = vencimento;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public int getNumeroDaParcela() {
        return numeroDaParcela;
    }

    public void setNumeroDaParcela(int numeroDaParcela) {
        this.numeroDaParcela = numeroDaParcela;
    }

    public String getObservcao() {
        return observcao;
    }

    public void setObservcao(String observcao) {
        this.observcao = observcao;
    }

 

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.id);
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
        final ParcelasReceber other = (ParcelasReceber) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(ParcelasReceber o) {
        if (this.numeroDaParcela > o.numeroDaParcela) {
            return 1;
        } else {
            return -1;
        }
    }

}
