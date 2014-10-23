/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.modelo;

import br.com.atus.util.peca.PecaColetor;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Data de criação 23/07/2013
 *
 * @author Ari
 */
@Entity
@Table(name = "unidade_federativa", schema = "cadastro")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class UnidadeFederativa implements Serializable {

    @Id
    @Column(name = "und_fed_id", nullable = false)
    private Integer id;
    @Column(name = "und_fed_nome")
    @PecaColetor
    private String nome;
    @Column(name = "und_fed_abreviacao", unique = true)
    @PecaColetor
    private String abreviacao;

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

    public String getAbreviacao() {
        return abreviacao;
    }

    public void setAbreviacao(String abreviacao) {
        this.abreviacao = abreviacao;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.id);
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
        final UnidadeFederativa other = (UnidadeFederativa) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "UnidadeFederativa{" + "id=" + id + ", nome=" + nome + ", abreviacao=" + abreviacao + '}';
    }
}
