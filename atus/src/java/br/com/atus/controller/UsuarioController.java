/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.controller;

import br.com.atus.dao.UsuarioDAO;
import br.com.atus.enumerated.Perfil;
import br.com.atus.modelo.Usuario;
import br.com.atus.util.CriptografiaSenha;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author ari
 */
@Stateless
public class UsuarioController extends Controller<Usuario, Long> implements Serializable {

    @EJB
    private UsuarioDAO dao;

    @PostConstruct
    @Override
    protected void inicializaDAO() {
        setDAO(dao);
    }

    public void alterarSenha(Usuario u, String senhaAtual, String novaSenha, String confirmeSenha) throws Exception {
        CriptografiaSenha cs = new CriptografiaSenha();
        senhaAtual = cs.criptografarSenha(senhaAtual);
        if (u.getSenha().equals(senhaAtual)) {
            if (novaSenha.equals(confirmeSenha)) {
                u.setSenha(cs.criptografarSenha(novaSenha));
                dao.atualizar(u);
            } else {
                throw new Exception("Senha não confere!");
            }
        } else {
            throw new Exception("Senha não confere!");
        }
    }

    public Usuario usuarioLogin(String remoteUser) {
        return dao.usuarioLogin(remoteUser);
    }

    public boolean ehUsuarioDoEscritorio(Usuario usuarioLogado) {
        return !usuarioLogado.getPerfil().equals(Perfil.COLABORADOR);
    }

}
