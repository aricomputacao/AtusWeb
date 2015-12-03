/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.cadastro.modelo;

import br.com.atus.enumerated.EstadoCivil;
import br.com.atus.enumerated.Sexo;
import br.com.atus.enumerated.TipoPessoa;
import br.com.atus.util.CpfCnpjUtil;
import br.com.atus.util.peca.PecaColetor;
import br.com.atus.util.peca.TipoMascara;
import java.io.Serializable;
import java.util.Date;
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
    @Column(name = "cli_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Embedded
    @PecaColetor(isEntidade = true)
    private Pessoa pessoa;

    @Column(name = "cli_documento", length = 20)
    @PecaColetor(tipo = TipoMascara.CPF_CNPJ)
    private String cpfCpnj;
    @Column(name = "cli_rg", length = 20)
    @PecaColetor
    private String rg;

    @Column(name = "cli_pis_pasep", length = 30)
    @PecaColetor
    private String pisPasep;

    @ManyToOne
    @JoinColumn(name = "tit_id", referencedColumnName = "tit_id")
    private TipoTratamento tipoTratamento;

    @ManyToOne
    @JoinColumn(name = "nac_id", referencedColumnName = "nac_id")
    @PecaColetor(isEntidade = true)
    private Nacionalidade nacionalidade;

    @ManyToOne
    @JoinColumn(name = "pro_id", referencedColumnName = "pro_id")
    @PecaColetor(isEntidade = true)
    private Profissao profissao;

    @Column(name = "cli_estado_civil")
    @Enumerated(EnumType.STRING)
    @PecaColetor
    private EstadoCivil estadoCivil;

    @Column(name = "cli_observacao", length = 1024)
    @PecaColetor
    private String observacao;

    @ManyToOne
    @JoinColumn(name = "ore_id", referencedColumnName = "ore_id")
    @PecaColetor(isEntidade = true)
    private OrgaoEmissor orgaoEmissor;

    @Enumerated(EnumType.STRING)
    @Column(name = "cli_sexo")
    @PecaColetor
    private Sexo sexo;

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

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

    public OrgaoEmissor getOrgaoEmissor() {
        return orgaoEmissor;
    }

    public void setOrgaoEmissor(OrgaoEmissor orgaoEmissor) {
        this.orgaoEmissor = orgaoEmissor;
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

    public String getPisPasep() {
        return pisPasep;
    }

    public void setPisPasep(String pisPasep) {
        this.pisPasep = pisPasep;
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

    public String getNome() {
        return this.pessoa.getNome();
    }

    public String getEmail() {
        return this.pessoa.getEmail();
    }

    public String getTelefone() {
        return this.pessoa.getCelular();
    }

    public Date getDataNascimento() {
        return this.pessoa.getDataNascimento();
    }

    public String getDocumentoFormatado() {
        if (this.pessoa.getTipoPessoa().equals(TipoPessoa.PF)) {
            return CpfCnpjUtil.formataCPF(this.cpfCpnj);
        } else if (this.pessoa.getTipoPessoa().equals(TipoPessoa.PJ)) {
            return CpfCnpjUtil.formataCNPJ(this.getCpfCpnj());
        } else {
            return "";
        }

    }

    public String getNomeDaCidade() {
        if (getPessoa().getCidade() != null) {
            return getPessoa().getCidade().getNome();
        } else {
            return "";
        }
    }

    public String getNomeDoEstado() {
        if (getPessoa().getCidade() != null) {
            return getPessoa().getCidade().getUnidadeFederativa().getAbreviacao();
        } else {
            return "";
        }
    }

    public String getContatos() {
        String contatos = new String();
        if ((!"".equals(this.getPessoa().getTelefone())) && this.getPessoa().getTelefone() != null) {
            contatos += " Tel 1: ".concat(this.getPessoa().getTelefone());
        }
        if ((!"".equals(this.getPessoa().getCelular())) && this.getPessoa().getCelular() != null) {
             contatos += " Cel 1: ".concat(this.getPessoa().getCelular());
        }
        if ((!"".equals(this.getPessoa().getEmail())) && this.getPessoa().getEmail() != null) {
             contatos += " Email: ".concat(this.getPessoa().getEmail());
        }
        return contatos;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj
    ) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Cliente other = (Cliente) obj;
        return Objects.equals(this.id, other.id);
    }

}
