/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.cadastro.modelo;

import br.com.atus.processo.modelo.Evento;
import java.io.Serializable;
import java.util.Date;
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
 * @author Ari
 */
@Entity
@Table(name = "agenda", schema = "cadastro")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Agenda implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "age_id", nullable = false)
    private Long id;

    @NotBlank
    @Column(name = "age_titulo", nullable = false, length = 255)
    private String titulo;

    @NotBlank
    @Column(name = "age_descricao", nullable = false, length = 1024)
    private String descricao;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "age_data_ini", nullable = false)
    private Date dataInicio;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "age_data_fim", nullable = false)
    private Date dataFim;

    @Column(name = "age_dia_todo", nullable = false, columnDefinition = "boolean default false")
    private boolean diaTodo;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "ese_id", referencedColumnName = "ese_id", nullable = false)
    private EspecieEvento especieEvento;
    
    @ManyToOne
    @JoinColumn(name = "eve_id", referencedColumnName = "eve_id")
    private Evento evento;
    
    @ManyToOne
    @JoinColumn(name = "usr_id", referencedColumnName = "usr_id")
    private Usuario usuario;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    
    

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }
    
    

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

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

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public boolean isDiaTodo() {
        return diaTodo;
    }

    public void setDiaTodo(boolean diaTodo) {
        this.diaTodo = diaTodo;
    }

    public EspecieEvento getEspecieEvento() {
        return especieEvento;
    }

    public void setEspecieEvento(EspecieEvento especieEvento) {
        this.especieEvento = especieEvento;
    }

}
