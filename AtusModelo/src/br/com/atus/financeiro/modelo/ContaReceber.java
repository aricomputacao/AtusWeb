/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.financeiro.modelo;

import br.com.atus.modelo.Advogado;
import br.com.atus.modelo.Colaborador;
import br.com.atus.modelo.Processo;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
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
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.ManyToAny;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author Ari
 */
@Entity
@Table(name = "conta_receber", schema = "finaneiro")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ContaReceber implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ctr_id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "pro_id", referencedColumnName = "pro_id")
    private Processo processo;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "col_id", referencedColumnName = "col_id")
    private Colaborador colaborador;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "adv_id", referencedColumnName = "adv_id")
    private Advogado advogado;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "coo_id", referencedColumnName = "coo_id", nullable = false)
    private Cooptacao cooptacao;

    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "ctr_data_lancamento", nullable = false)
    private Calendar dataCadastro;

    @NotNull
    @Min(value = 0)
    @Column(name = "ctr_valor", nullable = false)
    private BigDecimal valor;
    
    
    @NotNull
    @Min(value = 1)
    @Column(name = "ctr_qtd_parcelas", nullable = false)
    private int quantidadeParcelas;

    
    @NotNull
    @Min(value = 0)
    @Column(name = "ctr_percent_colaborador", nullable = false)
    private BigDecimal percentualColaborador;
    
    private final List<ParcelasReceber> parcelas;

    public ContaReceber(List<ParcelasReceber> parcelas) {
        this.parcelas = parcelas;
    }

    public ContaReceber() {
        parcelas = new ArrayList<>();
    }

    public BigDecimal getValorDonoDoProcesso() {
        BigDecimal percent = cooptacao.getPercentDono().divide(BigDecimal.valueOf(100)).setScale(2, RoundingMode.UNNECESSARY);
        return this.getValor().multiply(percent).subtract(getValorDoColaborador());
    }

    public BigDecimal getValorSocioDoProcesso() {
        BigDecimal percent = cooptacao.getPercentSocio().divide(BigDecimal.valueOf(100)).setScale(2, RoundingMode.UNNECESSARY);
        return this.getValor().multiply(percent).subtract(getValorDoColaborador());
    }
    
    public BigDecimal getValorDoColaborador() {
        BigDecimal percent = this.percentualColaborador.divide(BigDecimal.valueOf(100)).setScale(2, RoundingMode.UNNECESSARY);
        return this.getValor().multiply(percent);
    }

    public List getParcelas() {
        // exemplo de imutabilidade; esta lista não pode ser alterada externamente
        return Collections.unmodifiableList(parcelas);
    }

    public Long getId() {
        return id;
    }

    public Processo getProcesso() {
        return processo;
    }

    public void setProcesso(Processo processo) {
        this.processo = processo;
    }

    public Colaborador getColaborador() {
        return colaborador;
    }

    public BigDecimal getPercentualColaborador() {
        return percentualColaborador;
    }

    public void setPercentualColaborador(BigDecimal percentualColaborador) {
        this.percentualColaborador = percentualColaborador;
    }

    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
    }

    public Advogado getAdvogado() {
        return advogado;
    }

    public void setAdvogado(Advogado advogado) {
        this.advogado = advogado;
    }

    public Calendar getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Calendar dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public int getQuantidadeParcelas() {
        return quantidadeParcelas;
    }

    public void setQuantidadeParcelas(int quantidadeParcelas) {
        this.quantidadeParcelas = quantidadeParcelas;
    }

    public Cooptacao getCooptacao() {
        return cooptacao;
    }

    public void setCooptacao(Cooptacao cooptacao) {
        this.cooptacao = cooptacao;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 13 * hash + Objects.hashCode(this.id);
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
        final ContaReceber other = (ContaReceber) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
