/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.dto;

import br.com.atus.processo.modelo.Fase;
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
 * CREATE VIEW processo.processo_em_dia_view ( fas_id, qtd) AS SELECT fa.fas_id,
 * count(mp.pro_id) AS qtd FROM processo.movimentacao_processo_view mp JOIN
 * processo.movimentacao mo ON mp.ult_mov = mo.mov_id AND mo.pro_id = mp.pro_id
 * JOIN processo.fase fa ON fa.fas_id = mp.fas_id WHERE (( SELECT
 * 'now'::text::date - fa.fas_prazo )) <= mo.mov_data GROUP BY fa.fas_id 
 * 
 * @author
 * Ari
 */
@Entity
@Table(name = "processo_em_dia_view", schema = "processo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProcessoEmDiaDTO implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "fas_id", referencedColumnName = "fas_id")
    private Fase fase;

    @Column(name = "qtd")
    private Integer quantidade;

    public Fase getFase() {
        return fase;
    }

    public void setFase(Fase fase) {
        this.fase = fase;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + Objects.hashCode(this.fase);
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
        final ProcessoEmDiaDTO other = (ProcessoEmDiaDTO) obj;
        if (!Objects.equals(this.fase, other.fase)) {
            return false;
        }
        return true;
    }


    @Override
    public String toString() {
        return "Processos Atrasados {" + "Fase: " + fase + ", Quantidade: " + quantidade + '}';
    }
}
