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
@Table(name = "atendimento", schema = "cadastro")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Atendimento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ate_id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "usr_id_dono", referencedColumnName = "usr_id", nullable = false)
    private Usuario usuarioAtendimento;

    @ManyToOne
    @JoinColumn(name = "usr_id_atendeu", referencedColumnName = "usr_id")
    private Usuario usuarioAtendeu;

    @NotNull
    @Column(name = "ate_visitante", nullable = false)
    private String visitante;

    @Column(name = "ate_visi_tel")
    private String telefone;

    @Column(name = "ate_assunto", length = 1024)
    private String assunto;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ate_dt_entrada", nullable = false)
    private Date dataAbertura;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ate_dt_saida", nullable = false)
    private Date dataSaida;
    
    @Column(name = "ate_desistencia")
    private boolean desistencia;  

    
    @Column(name = "ate_desistencia_motivo", length = 1024)
    private String desistenciaMotivo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuarioAtendimento() {
        return usuarioAtendimento;
    }

    public void setUsuarioAtendimento(Usuario usuarioAtendimento) {
        this.usuarioAtendimento = usuarioAtendimento;
    }

    public Usuario getUsuarioAtendeu() {
        return usuarioAtendeu;
    }

    public void setUsuarioAtendeu(Usuario usuarioAtendeu) {
        this.usuarioAtendeu = usuarioAtendeu;
    }

    public String getVisitante() {
        return visitante;
    }

    public void setVisitante(String visitante) {
        this.visitante = visitante;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public Date getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(Date dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public Date getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(Date dataSaida) {
        this.dataSaida = dataSaida;
    }

    public boolean isDesistencia() {
        return desistencia;
    }

    public void setDesistencia(boolean desistencia) {
        this.desistencia = desistencia;
    }

    public String getDesistenciaMotivo() {
        return desistenciaMotivo;
    }

    public void setDesistenciaMotivo(String desistenciaMotivo) {
        this.desistenciaMotivo = desistenciaMotivo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.id);
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
        final Atendimento other = (Atendimento) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    

}
