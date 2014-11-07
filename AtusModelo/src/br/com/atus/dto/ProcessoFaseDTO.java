/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.dto;

import br.com.atus.modelo.Fase;
import br.com.atus.modelo.Movimentacao;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author ari
 */

public class ProcessoFaseDTO implements Serializable {

    private Fase fase;
    private Movimentacao movimentacao;

    public Fase getFase() {
        return fase;
    }

    public void setFase(Fase fase) {
        this.fase = fase;
    }

  

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.fase);
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
        final ProcessoFaseDTO other = (ProcessoFaseDTO) obj;
        if (!Objects.equals(this.fase, other.fase)) {
            return false;
        }
        return true;
    }
    
    
    

}
