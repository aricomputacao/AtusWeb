/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.financeiro.modelo;

import br.com.atus.util.NumeroPorExtenso;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 *
 * @author ari
 */
@Entity
@Table(name = "recibo", schema = "financeiro")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class Recibo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rec_id", nullable = false)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(schema = "financeiro", name = "recibo_parcelas", joinColumns = {
        @JoinColumn(name = "rec_id", referencedColumnName = "rec_id")}, inverseJoinColumns = {
        @JoinColumn(name = "par_id", referencedColumnName = "par_id")})
    private List<ParcelasReceber> listaDeParcelasReceber;

    public Recibo() {
    }

    public BigDecimal getValorTotal() {
        BigDecimal vlTot = BigDecimal.ZERO;
        for (ParcelasReceber lst : listaDeParcelasReceber) {
            vlTot = vlTot.add(lst.getValorPago());
        }
        return vlTot;
    }

    public String getValorTotalExtenso() {
        NumeroPorExtenso n = new NumeroPorExtenso(true, true, true);
        return n.converteMoeda(getValorTotal());
    }

    public Long getIdDoProcesso() {
        return listaDeParcelasReceber.get(0).getContaReceber().getProcesso().getId();

    }

    public String getNomeCliente() {
        return listaDeParcelasReceber.get(0).getNomeDoCliente();
    }

    public String getAdivogadoQueRecebeu() {
        return listaDeParcelasReceber.get(0).getAdvogadoQueRecebeu().getNome();
    }

    public Date getDataDePAgamento() {
        return listaDeParcelasReceber.get(0).getDataPagamento();

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void addParcela(ParcelasReceber parcelasReceber) {
        listaDeParcelasReceber.add(parcelasReceber);
    }

    public List<ParcelasReceber> getListaDeParcelasReceber() {
        Collections.sort(listaDeParcelasReceber);
        return Collections.unmodifiableList(listaDeParcelasReceber);
    }

    public void setListaDeParcelasReceber(List<ParcelasReceber> listaDeParcelasReceber) {
        this.listaDeParcelasReceber = listaDeParcelasReceber;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.id);
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
        final Recibo other = (Recibo) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
