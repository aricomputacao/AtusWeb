/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.dao;

import br.com.atus.modelo.Cidade;
import br.com.atus.modelo.UnidadeFederativa;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author ari
 */
@Stateless
public class CidadeDAO extends DAO<Cidade, Integer> implements Serializable {

    public CidadeDAO() {
        super(Cidade.class);
    }

    public List<Cidade> listarPorUf(UnidadeFederativa uf) {
        return getEm().createQuery("SELECT c FROM Cidade c WHERE c.unidadeFederativa = :uf ORDER BY c.nome", Cidade.class)
                .setParameter("uf", uf)
                .getResultList();
    }

    public Cidade buscarUfNome(UnidadeFederativa uf, String cidade) {
        return getEm().createQuery("SELECT c FROM Cidade c WHERE c.unidadeFederativa = :uf AND c.nome = :nome", Cidade.class)
                .setParameter("uf", uf)
                .setParameter("nome", cidade)
                .getSingleResult();

    }

}
