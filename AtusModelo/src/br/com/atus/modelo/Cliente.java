/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.modelo;

import br.com.atus.enumerated.EstadoCivil;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 *
 * @author ari
 */
@Entity
@Table(schema = "cadastro", name = "cliente")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Cliente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Embedded
    private Pessoa pessoa;
    @Column(name = "cli_documento", length = 20)
    private String cpfCpnj;
    @Column(name = "cli_rg", length = 20)
    private String rg;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "tit_id",referencedColumnName = "tit_id",nullable = false)
    private TipoTratamento tipoTratamento;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "nac_id",referencedColumnName = "nac_id",nullable = false)
    private Nacionalidade nacionalidade;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name = "pro_id",referencedColumnName = "pro_id",nullable = false)
    private Profissao profissao;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    private EstadoCivil estadoCivil;

    public TipoTratamento getTipoTratamento() {
        return tipoTratamento;
    }

    public void setTipoTratamento(TipoTratamento tipoTratamento) {
        this.tipoTratamento = tipoTratamento;
    }

    public Nacionalidade getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(Nacionalidade nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public EstadoCivil getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(EstadoCivil estadoCivil) {
        this.estadoCivil = estadoCivil;
    }
    
    

    public String getCpfCpnj() {
        return cpfCpnj;
    }

    public void setCpfCpnj(String cpfCpnj) {
        this.cpfCpnj = cpfCpnj;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }
    
    

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Profissao getProfissao() {
        return profissao;
    }

    public void setProfissao(Profissao profissao) {
        this.profissao = profissao;
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
        final Cliente other = (Cliente) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
}
