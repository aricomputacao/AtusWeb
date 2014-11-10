/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.controller;

import br.com.atus.dao.ProcessoDAO;
import br.com.atus.dto.ProcessoAtrasadoDTO;
import br.com.atus.modelo.Processo;
import br.com.atus.modelo.Usuario;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author ari
 */
@Stateless
public class ProcessoController extends Controller<Processo, Long> implements Serializable {

    @EJB
    private ProcessoDAO dao;

    @PostConstruct
    @Override
    protected void inicializaDAO() {
        setDAO(dao);
    }

    public List<ProcessoAtrasadoDTO> processoAtrasadoUsuario(Usuario u) {
        return dao.processoAtrasadoUsuario(u);
    }

    
    public List<ProcessoAtrasadoDTO> processoAtrasadoGeral() {
        return dao.processoAtrasadoGeral();
    }
}
