/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.util.peca;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author gilmario
 */
public class CampoPersonalizado implements Serializable {

    private String nome;
    private String mascara;
    private TipoMascara tipoMascara;

    public CampoPersonalizado(String nome, String mascara, TipoMascara tipoMascara) {
        this.nome = nome;
        this.mascara = mascara;
        this.tipoMascara = tipoMascara;
    }

    public TipoMascara getTipoMascara() {
        return tipoMascara;
    }

    public void setTipoMascara(TipoMascara tipoMascara) {
        this.tipoMascara = tipoMascara;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMascara() {
        return mascara;
    }

    public void setMascara(String mascara) {
        this.mascara = mascara;
    }

    public String getTagName() {
        return DocumentoConverter.PARTE_ESQUERDA + nome + DocumentoConverter.PARTE_DIREITA;
    }

    public String getReplaceTagName() {
        return DocumentoConverter.STRING_ESQUERDA + nome + DocumentoConverter.STRING_DIREITA;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.nome);
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
        final CampoPersonalizado other = (CampoPersonalizado) obj;
        return Objects.equals(this.nome, other.nome);
    }

}
