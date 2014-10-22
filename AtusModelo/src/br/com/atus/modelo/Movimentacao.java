/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
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
 * @author Ari
 */
@Entity
@Table(schema = "processo", name = "movimentacao")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class Movimentacao implements Serializable {

    @Id
    @Column(name = "mov_id", nullable = false)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "mov_data", nullable = false)
    private Date dataMovimentacao;
    
    @ManyToOne
    @JoinColumn(name ="fas_antiga",referencedColumnName = "fas_id")
    private Fase faseAntiga;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name ="fas_nova",referencedColumnName = "fas_id",nullable = false )
    private Fase faseNova;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name ="pro_id",referencedColumnName = "pro_id",nullable = false )
    private Processo processo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDataMovimentacao() {
        return dataMovimentacao;
    }

    public void setDataMovimentacao(Date dataMovimentacao) {
        this.dataMovimentacao = dataMovimentacao;
    }

    public Fase getFaseAntiga() {
        return faseAntiga;
    }

    public void setFaseAntiga(Fase faseAntiga) {
        this.faseAntiga = faseAntiga;
    }

    public Fase getFaseNova() {
        return faseNova;
    }

    public void setFaseNova(Fase faseNova) {
        this.faseNova = faseNova;
    }

    public Processo getProcesso() {
        return processo;
    }

    public void setProcesso(Processo processo) {
        this.processo = processo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.id);
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
        final Movimentacao other = (Movimentacao) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
