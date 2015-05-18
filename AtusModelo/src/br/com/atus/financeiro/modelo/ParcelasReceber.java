/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.financeiro.modelo;

import br.com.atus.modelo.Advogado;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
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
@Table(name = "parcela_receber", schema = "financeiro",uniqueConstraints = @UniqueConstraint(columnNames = {"ctr_id","par_numero"}))
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ParcelasReceber implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "par_id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "ctr_id", nullable = false, referencedColumnName = "ctr_id")
    private ContaReceber contaReceber;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "adv_id", nullable = false, referencedColumnName = "adv_id")
    private Advogado advogadoQueRecebeu;

    @NotNull
    @Min(value = 0)
    @Column(name = "par_valor_parcela", nullable = false)
    private BigDecimal valorParcela;

    @NotNull
    @Min(value = 0)
    @Column(name = "par_valor_recebido", nullable = false)
    private BigDecimal valorPago;

    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "par_vencimento", nullable = false)
    private Calendar vencimento;

    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "par_data_pagamento", nullable = false)
    private Calendar dataPagamento;
    
    @NotNull
    @Column(name = "par_numero", nullable = false)
    private int numeroDaParcela;
    
    
    

    //finalizar parcela
    public BigDecimal getValorDonoDoProcesso() {
        BigDecimal percent = this.contaReceber.getCooptacao().getPercentDono().divide(BigDecimal.valueOf(100)).setScale(2, RoundingMode.UNNECESSARY);
        return this.valorPago.multiply(percent).subtract(getValorDoColaborador());
    }

    public BigDecimal getValorSocioDoProcesso() {
        BigDecimal percent = this.contaReceber.getCooptacao().getPercentSocio().divide(BigDecimal.valueOf(100)).setScale(2, RoundingMode.UNNECESSARY);
        return valorPago.multiply(percent).subtract(getValorDoColaborador());
    }

    public BigDecimal getValorDoColaborador() {
        BigDecimal percent = this.contaReceber.getPercentualColaborador().divide(BigDecimal.valueOf(100)).setScale(2, RoundingMode.UNNECESSARY);
        return this.valorPago.multiply(percent);
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

    public Advogado getAdvogadoQueRecebeu() {
        return advogadoQueRecebeu;
    }

    public void setAdvogadoQueRecebeu(Advogado advogadoQueRecebeu) {
        this.advogadoQueRecebeu = advogadoQueRecebeu;
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

    public Calendar getVencimento() {
        return vencimento;
    }

    public void setVencimento(Calendar vencimento) {
        this.vencimento = vencimento;
    }

    public Calendar getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Calendar dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public int getNumeroDaParcela() {
        return numeroDaParcela;
    }

    public void setNumeroDaParcela(int numeroDaParcela) {
        this.numeroDaParcela = numeroDaParcela;
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
    
    
}
