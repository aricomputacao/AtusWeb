/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.cadastro.dao;

import br.com.atus.util.dao.DAO;
import br.com.atus.cadastro.modelo.Usuario;
import java.io.Serializable;
import javax.ejb.Stateless;

/**
 *
 * @author ari
 */
@Stateless
public class UsuarioDAO extends DAO<Usuario, Long> implements Serializable {

    public UsuarioDAO() {
        super(Usuario.class);
    }

    public Usuario usuarioLogin(String remoteUser) {
        try {
            return (Usuario) getEm().createQuery("SELECT u FROM Usuario u  WHERE u.login = :log")
                    .setParameter("log", remoteUser)
                    .getSingleResult();
        } catch (Exception e) {
            return new Usuario();
        }

    }

}
