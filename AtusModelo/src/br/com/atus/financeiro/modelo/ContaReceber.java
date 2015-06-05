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
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
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

/**
 *
 * @author Ari
 */
@Entity
@Table(name = "conta_receber", schema = "financeiro")
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
    private Date dataCadastro;

    @NotNull
    @Min(value = 0)
    @Column(name = "ctr_valor", nullable = false)
    private BigDecimal valor;
    
    
    @NotNull
    @Min(value = 1)
    @Column(name = "ctr_qtd_parcelas", nullable = false)
    private int quantidadeParcelas;

    
  
    
    
    @Column(name = "ctr_observacao")
    private String observacao;
    
   

    public BigDecimal getValorDonoDoProcesso() {
        BigDecimal percent = cooptacao.getPercentDono().divide(BigDecimal.valueOf(100)).setScale(2,RoundingMode.DOWN);
        return this.getValor().multiply(percent).setScale(2,RoundingMode.DOWN);
    }

    public BigDecimal getValorSocioDoProcesso() {
        BigDecimal percent = cooptacao.getPercentSocio().divide(BigDecimal.valueOf(100)).setScale(2,RoundingMode.DOWN);
        return this.getValor().multiply(percent).setScale(2,RoundingMode.DOWN);
    }
    
    public BigDecimal getValorDoColaborador() {
        BigDecimal percent = this.cooptacao.getPercentColaborador().divide(BigDecimal.valueOf(100)).setScale(2,RoundingMode.DOWN);
        return this.getValor().multiply(percent).setScale(2);
    }

    public BigDecimal getValorRestanteReceber(List<ParcelasReceber> prs){
        BigDecimal vl = new BigDecimal(BigInteger.ZERO);
        for (ParcelasReceber pr : prs) {
            if (pr.getDataPagamento() == null) {
                vl.add(pr.getValorPago());
            }
        }
        return vl.setScale(2,RoundingMode.DOWN);
    }
    
    public String getNomeDoAdvogado(){
      return this.getAdvogado().getNome();
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

  

    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
    }

    public Advogado getAdvogado() {
        return advogado;
    }

    public void setAdvogado(Advogado advogado) {
        this.advogado = advogado;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
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
    
    public String getNomeDoCliente(){
        return this.processo.getNomeDoCliente();
    }
    
    public String getNomeDoColaborador(){
        return this.colaborador.getNome();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 13 * hash + Objects.hashCode(this.id);
        return hash;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
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
