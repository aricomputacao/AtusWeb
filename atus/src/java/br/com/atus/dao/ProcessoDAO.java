/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.dao;

import br.com.atus.dto.ProcessoAtrasadoDTO;
import br.com.atus.dto.ProcessoGrupoDiaAtrasadoDTO;
import br.com.atus.dto.ProcessosAtrasadoRelatorioDTO;
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
        q = getEm().createQuery("SELECT p FROM Processo p WHERE p.cliente  = :c ORDER BY p.fase,p.fase.usuario", Processo.class)
                .setParameter("c", c);
        if (q.getResultList().isEmpty()) {
            return new ArrayList<>();
        } else {
            return q.getResultList();
        }
    }

    public List<Processo> listarLikeNumero(String num) {
        TypedQuery q;
        q = getEm().createQuery("SELECT p FROM Processo p WHERE UPPER(p.numero) like :c ORDER BY p.fase.usuario,p.fase", Processo.class)
                .setParameter("c", "%" + num.toUpperCase() + "%");
        if (q.getResultList().isEmpty()) {
            return new ArrayList<>();
        } else {
            return q.getResultList();
        }
    }

    public List<ProcessosAtrasadoRelatorioDTO> listaProcessosAtrasadosRelatorio() {
        TypedQuery q;
        q = getEm().createQuery("SELECT p FROM ProcessosAtrasadoRelatorioDTO p  ORDER BY p.processo.fase.usuario,p.processo.fase", ProcessosAtrasadoRelatorioDTO.class);
        if (q.getResultList().isEmpty()) {
            return new ArrayList<>();
        } else {
            return q.getResultList();
        }
    }

    public List<ProcessosAtrasadoRelatorioDTO> listaProcessosAtrasadosRelatorio(Cliente c) {
        TypedQuery q;
        q = getEm().createQuery("SELECT p FROM ProcessosAtrasadoRelatorioDTO p  where p.processo.cliente = :c ORDER BY p.processo.fase.usuario,p.processo.fase", ProcessosAtrasadoRelatorioDTO.class)
                .setParameter("c", c);
        if (q.getResultList().isEmpty()) {
            return new ArrayList<>();
        } else {
            return q.getResultList();
        }
    }

    public List<ProcessosAtrasadoRelatorioDTO> listaProcessosAtrasadosRelatorio(Usuario u) {
        TypedQuery q;
        q = getEm().createQuery("SELECT p FROM ProcessosAtrasadoRelatorioDTO p  where p.processo.fase.usuario = :c ORDER BY p.processo.fase.usuario,p.processo.fase", ProcessosAtrasadoRelatorioDTO.class)
                .setParameter("c", u);
        if (q.getResultList().isEmpty()) {
            return new ArrayList<>();
        } else {
            return q.getResultList();
        }
    }

    public List<Processo> listarFaseNula() {
         TypedQuery q;
        q = getEm().createQuery("SELECT p FROM Processo p WHERE p.fase IS NULL", Processo.class);
        
        return q.getResultList().isEmpty() ? new ArrayList<>() : q.getResultList();
    }
}
