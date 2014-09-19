/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.modelo;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;

/**
 * Classe do Projeto GuardiaoModelos criada em 26/06/2013
 *
 * @author: ari
 */
@Entity
@Table(name = "cidade", schema = "cadastro")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class Cidade implements Serializable {

    @Id
    @Column(name = "cid_id", nullable = false)
    private Integer id;
    @Length(max = 255)
    @NotNull
    @Column(name = "cid_nome", nullable = false)
    private String nome;
    @NotNull
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "und_fed_id", referencedColumnName = "und_fed_id", nullable = false)
    private UnidadeFederativa unidadeFederativa;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public UnidadeFederativa getUnidadeFederativa() {
        return unidadeFederativa;
    }

    public void setUnidadeFederativa(UnidadeFederativa unidadeFederativa) {
        this.unidadeFederativa = unidadeFederativa;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.id);
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
        final Cidade other = (Cidade) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}
