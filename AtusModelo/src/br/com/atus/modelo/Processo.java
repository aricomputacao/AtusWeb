/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author gilmario
 */
@Entity
@Table(name = "processo", schema = "processo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Processo implements Serializable {

    @Id
    @Column(name = "pro_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
   
    @ManyToOne
    @JoinColumn(name = "end_id", referencedColumnName = "end_id", nullable = false)
    @NotNull
    private Enderecamento enderecamento;
   
    @ManyToOne
    @JoinColumn(name = "col_id", referencedColumnName = "col_id")
    private Colaborador colaborador;
    
    @OneToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private List<Adversario> adversarios;
    
    @OneToMany( fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private List<ParteInteressada> parteInteressadas;
    
    @Column(name = "pro_numero", length = 20)
    @Length(max = 20)
    private String numero;
    
    @ManyToOne
    @JoinColumn(name = "tpc_id", referencedColumnName = "tpc_id", nullable = false)
    @NotNull
    private TipoContrato tipoContrato;
    
    @ManyToOne
    @JoinColumn(name = "fas_id", referencedColumnName = "fas_id", nullable = false)
    @NotNull
    private Fase fase;
    
    @NotNull
    @Column(name = "pro_data_cadastro", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataCadastro;
   
    @ManyToOne
    @JoinColumn(name = "usr_id", referencedColumnName = "usr_id")
    private Usuario usuarioCadastro;
   
    @ManyToOne
    @JoinColumn(name = "mat_id", referencedColumnName = "mat_id", nullable = false)
    @NotNull
    private Materia materia;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name = "obp_id",referencedColumnName = "obp_id",nullable = false)
    private ObjetoProcesso objetoProcesso;
    
    @ManyToOne
    @JoinColumn(name = "jut_id", referencedColumnName = "jut_id", nullable = false)
    @NotNull
    private JuizoTribunal juizoTribunal;
    
    @Column(name = "pro_valor", nullable = false)
    @NotNull
    private BigDecimal valor;
    
    @ManyToOne
    @JoinColumn(name = "adv_id", referencedColumnName = "adv_id", nullable = false)
    @NotNull
    private Advogado advogado;
   
    @Length(max = 100000)
    @Column(name = "pro_observacoes", nullable = false, length = 100000)
    @NotBlank
    private String observacoes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Enderecamento getEnderecamento() {
        return enderecamento;
    }

    public void setEnderecamento(Enderecamento enderecamento) {
        this.enderecamento = enderecamento;
    }

    public Colaborador getColaborador() {
        return colaborador;
    }

    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
    }



    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }



    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Usuario getUsuarioCadastro() {
        return usuarioCadastro;
    }

    public void setUsuarioCadastro(Usuario usuarioCadastro) {
        this.usuarioCadastro = usuarioCadastro;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

   

    public JuizoTribunal getJuizoTribunal() {
        return juizoTribunal;
    }

    public void setJuizoTribunal(JuizoTribunal juizoTribunal) {
        this.juizoTribunal = juizoTribunal;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Advogado getAdvogado() {
        return advogado;
    }

    public void setAdvogado(Advogado advogado) {
        this.advogado = advogado;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public List<Adversario> getAdversarios() {
        return adversarios;
    }

    public void setAdversarios(List<Adversario> adversarios) {
        this.adversarios = adversarios;
    }

    public List<ParteInteressada> getParteInteressadas() {
        return parteInteressadas;
    }

    public void setParteInteressadas(List<ParteInteressada> parteInteressadas) {
        this.parteInteressadas = parteInteressadas;
    }

    public TipoContrato getTipoContrato() {
        return tipoContrato;
    }

    public void setTipoContrato(TipoContrato tipoContrato) {
        this.tipoContrato = tipoContrato;
    }

    public ObjetoProcesso getObjetoProcesso() {
        return objetoProcesso;
    }

    public void setObjetoProcesso(ObjetoProcesso objetoProcesso) {
        this.objetoProcesso = objetoProcesso;
    }

    public Fase getFase() {
        return fase;
    }

    public void setFase(Fase fase) {
        this.fase = fase;
    }

    
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.id);
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
        final Processo other = (Processo) obj;
        return Objects.equals(this.id, other.id);
    }

}
