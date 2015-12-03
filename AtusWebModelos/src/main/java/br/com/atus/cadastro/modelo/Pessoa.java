/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.cadastro.modelo;

import br.com.atus.cadastro.modelo.Cidade;
import br.com.atus.enumerated.TipoPessoa;
import br.com.atus.util.peca.PecaColetor;
import br.com.atus.util.peca.TipoMascara;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author Ari
 */
@Embeddable
public class Pessoa implements Serializable {

    // Email da pessoa
    @Email
    @Column(name = "pes_email")
    @PecaColetor
    private String email;
    // Nome da pessoa
    @NotBlank
    @Column(name = "pes_nome", length = 255, nullable = false)
    @Length(min = 3)
    @PecaColetor
    private String nome;

    @Column(name = "pes_telefone")
    @PecaColetor
    private String telefone;

    @Column(name = "pes_celular")
    @PecaColetor
    private String celular;

    @Enumerated(EnumType.STRING)
    @Column(name = "pes_tipo", nullable = false)
    private TipoPessoa tipoPessoa;

    @ManyToOne
    @JoinColumn(name = "cid_id", referencedColumnName = "cid_id")
    @PecaColetor(isEntidade = true)
    private Cidade cidade;

    @Column(name = "end_cep")
    @PecaColetor(mascara = "##.###-###")
    private String cep;

    @Column(name = "end_logradouro")
    @PecaColetor
    private String logradouro;

    @Column(name = "end_bairro")
    @PecaColetor
    private String bairro;

    @Column(name = "end_numero")
    @PecaColetor
    private String numero;

    @Column(name = "end_complemento")
    @PecaColetor
    private String complemento;

    @Temporal(TemporalType.DATE)
    @Column(name = "pes_data_nascimento")
    @PecaColetor(tipo = TipoMascara.DATA)
    private Date dataNascimento;
    @Temporal(TemporalType.DATE)
    @Column(name = "pes_data_cadastro")
    @PecaColetor(tipo = TipoMascara.DATA)
    private Date dataCadastro;

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public TipoPessoa getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(TipoPessoa tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

   

}
