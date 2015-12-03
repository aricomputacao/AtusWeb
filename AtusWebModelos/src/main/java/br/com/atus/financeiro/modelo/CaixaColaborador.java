/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.financeiro.modelo;

import br.com.atus.cadastro.modelo.Colaborador;
import br.com.atus.processo.modelo.Processo;
import br.com.atus.cadastro.modelo.Usuario;
import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 *
 * @author ari
 */
@Entity
@Table(name = "caixa_colaborador",schema = "financeiro")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CaixaColaborador implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cxc_id",nullable = false)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "rec_id",referencedColumnName = "rec_id",nullable = false)
    private Recibo recibo;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name = "col_id",referencedColumnName = "col_id",nullable = false)
    private Colaborador colaborador;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "cxc_data_recebimento")
    private Date dataDeRecebimento;
    
    @ManyToOne
    @JoinColumn(name = "usr_id",referencedColumnName = "usr_id")
    private Usuario usuarioQuePagou;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name = "pro_id",referencedColumnName = "pro_id",nullable = false)
    private Processo processo;

    public Processo getProcesso() {
        return processo;
    }

    public void setProcesso(Processo processo) {
        this.processo = processo;
    }
    
    
    
  

    
    public BigDecimal getValorDoRecibo(){
        return recibo.getValorTotal();
    }
    
    public BigDecimal getPercentualDoColaborador(){
        return recibo.getPercentualColaborador();
    }
    
    public BigDecimal getValorDoColaborador(){
        return recibo.getValorDoColaborador();
    }
    
   
    
    public String getNomeDoCliente(){
       return recibo.getNomeCliente();
    }
    
   
    
    public Date getDataDoPagamento(){
        return recibo.getDataDePagamento();
    }
    
    public List<ParcelasReceber> getListaDeParcelas(){
        return recibo.getListaDeParcelasReceber();
    }
    
    public Long getNumeroDoRecibo(){
        return recibo.getId();
    }
    
    public String getRecebidoPor(){
        return recibo.getNomeDoAdvogadoQueRecebeu();
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Colaborador getColaborador() {
        return colaborador;
    }

    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
    }

   

    public Date getDataDeRecebimento() {
        return dataDeRecebimento;
    }

    public void setDataDeRecebimento(Date dataDeRecebimento) {
        this.dataDeRecebimento = dataDeRecebimento;
    }

   

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.id);
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
        final CaixaColaborador other = (CaixaColaborador) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
}
