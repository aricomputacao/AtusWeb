/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.processo.modelo;

import br.com.atus.cadastro.modelo.Usuario;
import br.com.atus.cadastro.modelo.EspecieEvento;
import java.io.Serializable;
import java.util.Date;
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
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author ari
 */
@Entity
@Table(name = "evento", schema = "processo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Evento implements Serializable, Comparable<Evento> {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "eve_id",nullable = false)
    private Long id;
    
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "eve_data",nullable = false)
    private Date data;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name = "usr_id",referencedColumnName = "usr_id",nullable = false)
    private Usuario usuario;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name = "ese_id",referencedColumnName = "ese_id",nullable = false)
    private EspecieEvento especieEvento;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name = "pro_id",referencedColumnName = "pro_id",nullable = false)
    private Processo processo;
    
    @NotBlank
    @Column(name = "eve_nome",nullable = false)
    private String nome;
    
    
    @Column(name = "eve_observacao",nullable = true,length = 1024)
    private String observacao;
    
    @Column(name = "eve_local",length = 1024)
    private String local;

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

 

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public EspecieEvento getEspecieEvento() {
        return especieEvento;
    }

    public void setEspecieEvento(EspecieEvento especieEvento) {
        this.especieEvento = especieEvento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Processo getProcesso() {
        return processo;
    }

    public void setProcesso(Processo processo) {
        this.processo = processo;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 5;
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
        final Evento other = (Evento) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
      @Override
    public int compareTo(Evento o) {
        if (this.getData().before(o.getData())) {
            return -1;
        }
        if (this.getData().after(o.getData())) {
            return 1;
        }
        return 0;

    }
}
