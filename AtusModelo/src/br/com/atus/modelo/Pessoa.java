/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.atus.modelo;

import br.com.atus.enumerated.Sexo;
import br.com.atus.enumerated.TipoPessoa;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
public  class Pessoa {
     // Email da pessoa
    @Email
    @Column(name = "pes_email", length = 255)
    private String email;
    // Nome da pessoa
    @NotBlank
    @Column(name = "pes_nome", length = 255, nullable = false)
    @Length(min = 3)
    private String nome;
    // cidade parte do endereço da pessoa
    @Column(name = "pes_telefone")
    private String telefone;
    
    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "pes_tipo",nullable = false)
    private TipoPessoa tipoPessoa;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "pes_sexo",nullable = false)
    private Sexo sexo;
}
