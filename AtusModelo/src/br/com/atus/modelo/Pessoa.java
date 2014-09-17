/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.modelo;

import br.com.atus.enumerated.Sexo;
import br.com.atus.enumerated.TipoPessoa;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author Ari
 */
@Embeddable
public class Pessoa {

    // Email da pessoa
    @Email
    @Column(name = "pes_email")
    private String email;
    // Nome da pessoa
    @NotBlank
    @Column(name = "pes_nome", length = 255, nullable = false)
    @Length(min = 3)
    private String nome;

    @Column(name = "pes_telefone")
    private String telefone;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "pes_tipo", nullable = false)
    private TipoPessoa tipoPessoa;

    @Enumerated(EnumType.STRING)
    @Column(name = "pes_sexo", nullable = false)
    private Sexo sexo;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "cid_id", referencedColumnName = "cid_id", nullable = false)
    private Cidade cidade;

    @Column(name = "end_cep")
    private String cep;

    @Column(name = "end_logradouro")
    private String logradouro;

    @Column(name = "end_bairro")
    private String bairro;

    @Column(name = "end_numero")
    private String numero;

    @Column(name = "end_complemento")
    private String complemento;

    @Temporal(TemporalType.DATE)
    @Column(name = "pes_data_nascimento")
    private Date dataNascimento;

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

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
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

}
