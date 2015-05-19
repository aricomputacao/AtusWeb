/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.financeiro.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author Ari
 */
@Entity
@Table(name = "cooptacao", schema = "financeiro")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Cooptacao implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coo_id", nullable = false)
    private Long id;

    @NotBlank
    @Column(name = "coo_nome", nullable = false, unique = true)
    private String nome;

    @NotNull
    @Min(value = 0)
    @Column(name = "coo_percent_dono", nullable = false)
    private BigDecimal percentDono;

    @NotNull
    @Min(value = 0)
    @Column(name = "coo_percent_socio", nullable = false)
    private BigDecimal percentSocio;

    @NotNull
    @Column(name = "coop_ativo", nullable = false, columnDefinition = "boolean default  true")
    private boolean ativo;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getPercentDono() {
        return percentDono;
    }

    public void setPercentDono(BigDecimal percentDono) {
        this.percentDono = percentDono;
    }

    public BigDecimal getPercentSocio() {
        return percentSocio;
    }

    public void setPercentSocio(BigDecimal percentSocio) {
        this.percentSocio = percentSocio;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.id);
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
        final Cooptacao other = (Cooptacao) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
