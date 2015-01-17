/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.dto;

import br.com.atus.modelo.Processo;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * CREATE OR REPLACE VIEW processo.processo_atrasado_relatorio_view ( pro_id) AS
 * SELECT mp.pro_id FROM processo.movimentacao_processo mp JOIN
 * processo.movimentacao mo ON mp.ult_mov = mo.mov_id AND mo.pro_id = mp.pro_id
 * JOIN processo.fase fa ON fa.fas_id = mp.fas_id WHERE (( SELECT
 * 'now'::text::date - fa.fas_prazo )) > mo.mov_data ORDER BY mp.fas_id;
 *
 * @author Ari
 */
@Entity
@Table(name = "processo_atrasado_relatorio_view", schema = "processo")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class ProcessosAtrasadoRelatorioDTO implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "pro_id", referencedColumnName = "pro_id")
    private Processo processo;

    public Processo getProcesso() {
        return processo;
    }

    public void setProcesso(Processo processo) {
        this.processo = processo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.processo);
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
        final ProcessosAtrasadoRelatorioDTO other = (ProcessosAtrasadoRelatorioDTO) obj;
        if (!Objects.equals(this.processo, other.processo)) {
            return false;
        }
        return true;
    }

}
