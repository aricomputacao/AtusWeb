/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.cadastro.modelo;

import br.com.atus.processo.modelo.SubGrupoPeca;
import br.com.atus.util.peca.PecaColetor;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author gilmario
 */
@Entity
@Table(schema = "cadastro", name = "peca")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Peca implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(name = "pec_descricao", nullable = false, unique = true)
    @PecaColetor
    private String descricao;
    @NotBlank
    @Column(name = "pec_arquivo", nullable = false)
    private String arquivo;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "sub_grp_id", referencedColumnName = "sub_grp_id", nullable = false)
    private SubGrupoPeca subgrupo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getArquivo() {
        return arquivo;
    }

    public void setArquivo(String arquivo) {
        this.arquivo = arquivo;
    }

    public SubGrupoPeca getSubgrupo() {
        return subgrupo;
    }

    public void setSubgrupo(SubGrupoPeca subgrupo) {
        this.subgrupo = subgrupo;
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
        final Peca other = (Peca) obj;
        return Objects.equals(this.id, other.id);
    }

}
