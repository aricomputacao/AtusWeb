/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.dto;

import br.com.atus.modelo.Fase;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * CREATE OR REPLACE VIEW processo.grupo_processo_dia_atrasado ( fas_id,
 * qtd_atra, qtd_dia) AS SELECT fa.fas_id, pa.qtd AS qtd_atra, pd.qtd AS qtd_dia
 * FROM processo.fase fa FULL JOIN processo.processo_atrasado pa ON pa.fas_id =
 * fa.fas_id FULL JOIN processo.processo_em_dia pd ON pd.fas_id = fa.fas_id;
 *
 * @author ari
 */
@Entity
@Table(name = "grupo_processo_dia_atrasado", schema = "processo")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class ProcessoGrupoDiaAtrasadoDTO implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "fas_id", referencedColumnName = "fas_id")
    private Fase fase;

    @Column(name = "qtd_atra")
    private Integer qtdAntrasada;

    @Column(name = "qtd_dia")
    private Integer qtdEmDia;

    public ProcessoGrupoDiaAtrasadoDTO() {
        qtdAntrasada = 0;
        qtdEmDia = 0;

    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.fase);
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
        final ProcessoGrupoDiaAtrasadoDTO other = (ProcessoGrupoDiaAtrasadoDTO) obj;
        if (!Objects.equals(this.fase, other.fase)) {
            return false;
        }
        return true;
    }

    public Fase getFase() {
        return fase;
    }

    public void setFase(Fase fase) {
        this.fase = fase;
    }

    public Integer getQtdAntrasada() {
        return qtdAntrasada;
    }

    public void setQtdAntrasada(Integer qtdAntrasada) {
        this.qtdAntrasada = qtdAntrasada;
    }

    public Integer getQtdEmDia() {
        return qtdEmDia;
    }

    public void setQtdEmDia(Integer qtdEmDia) {
        this.qtdEmDia = qtdEmDia;
    }

}
