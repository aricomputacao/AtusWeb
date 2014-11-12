/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.dao;

import br.com.atus.dto.ProcessoAtrasadoDTO;
import br.com.atus.dto.ProcessoGrupoDiaAtrasadoDTO;
import br.com.atus.modelo.Cliente;
import br.com.atus.modelo.Fase;
import br.com.atus.modelo.Processo;
import br.com.atus.modelo.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

/**
 *
 * @author ari
 */
@Stateless
public class ProcessoDAO extends DAO<Processo, Long> implements Serializable {

    public ProcessoDAO() {
        super(Processo.class);
    }

    public List<ProcessoAtrasadoDTO> processoAtrasadoUsuario(Usuario u) {
        TypedQuery q;
        q = getEm().createQuery("SELECT p FROM ProcessoAtrasadoDTO p WHERE p.fase.usuario = :u ORDER BY p.fase.nome", ProcessoAtrasadoDTO.class)
                .setParameter("u", u);
        if (q.getResultList().isEmpty()) {
            return new ArrayList<>();
        } else {
            return q.getResultList();
        }

    }

    public List<ProcessoAtrasadoDTO> processoAtrasadoGeral() {
        TypedQuery q;
        q = getEm().createQuery("SELECT p FROM ProcessoAtrasadoDTO p ORDER BY p.fase.nome", ProcessoAtrasadoDTO.class);
        if (q.getResultList().isEmpty()) {
            return new ArrayList<>();
        } else {
            return q.getResultList();
        }

    }

    public List<ProcessoGrupoDiaAtrasadoDTO> processoGrupoDiaAtrasadoGeral() {
        TypedQuery q;
        q = getEm().createQuery("SELECT p FROM ProcessoGrupoDiaAtrasadoDTO p ORDER BY p.fase.nome", ProcessoGrupoDiaAtrasadoDTO.class);
        if (q.getResultList().isEmpty()) {
            return new ArrayList<>();
        } else {
            return q.getResultList();
        }

    }

    public List<ProcessoGrupoDiaAtrasadoDTO> processoGrupoDiaAtrasadoSetor(Usuario usuarioLogado) {
        TypedQuery q;
        q = getEm().createQuery("SELECT p FROM ProcessoGrupoDiaAtrasadoDTO p WHERE p.fase.usuario = :usr ORDER BY p.fase.nome", ProcessoGrupoDiaAtrasadoDTO.class)
                .setParameter("usr", usuarioLogado);
        if (q.getResultList().isEmpty()) {
            return new ArrayList<>();
        } else {
            return q.getResultList();
        }
    }

    public List<Processo> listarPorFase(Fase f) {
        TypedQuery q;
        q = getEm().createQuery("SELECT p FROM Processo p WHERE p.fase  = :f ", Processo.class)
                .setParameter("f", f);
        if (q.getResultList().isEmpty()) {
            return new ArrayList<>();
        } else {
            return q.getResultList();
        }
    }

    public List<Processo> listarPorCliente(Cliente c) {
        TypedQuery q;
        q = getEm().createQuery("SELECT p FROM Processo p WHERE p.cliente  = :c ", Processo.class)
                .setParameter("c", c);
        if (q.getResultList().isEmpty()) {
            return new ArrayList<>();
        } else {
            return q.getResultList();
        }
    }
}
