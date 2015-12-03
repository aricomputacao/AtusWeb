/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.atus.chat.modelo;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Ari
 */
public class Menssagem implements Serializable ,Comparable<Menssagem>{
    
    private String destino;
    private String autor;
    private String conteudo;
    private Date  data;
    private boolean entregue;

    public boolean isEntregue() {
        return entregue;
    }

    public void setEntregue(boolean entregue) {
        this.entregue = entregue;
    }
    
    

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }
    
     @Override
    public int compareTo(Menssagem o) {
        if (this.getData().before(o.getData())) {
            return -1;
        }
        if (this.getData().after(o.getData())) {
            return 1;
        }
        return 0;

    }
    
}
