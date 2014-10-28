/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.managedbean;

import br.com.atus.controller.AdversarioController;
import br.com.atus.controller.EventoController;
import br.com.atus.controller.MovimentacaoController;
import br.com.atus.controller.ParteInteressadaController;
import br.com.atus.controller.PecaController;
import br.com.atus.controller.ProcessoController;
import br.com.atus.exceptions.PecaFileException;
import br.com.atus.modelo.Adversario;
import br.com.atus.modelo.Cliente;
import br.com.atus.modelo.Evento;
import br.com.atus.modelo.Fase;
import br.com.atus.modelo.Movimentacao;
import br.com.atus.modelo.ParteInteressada;
import br.com.atus.modelo.Peca;
import br.com.atus.modelo.Processo;
import br.com.atus.util.AssistentedeRelatorio;
import br.com.atus.util.MenssagemUtil;
import br.com.atus.util.RelatorioSession;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author ari
 */
@ManagedBean
@ViewScoped
public class ProcessoMB extends BeanGenerico<Processo> implements Serializable {

    @Inject
    private NavegacaoMB navegacaoMB;
    @EJB
    private ProcessoController controller;
    @EJB
    private ParteInteressadaController interessadaController;
    @EJB
    private AdversarioController adversarioController;
    @EJB
    private EventoController eventoController;
    @EJB
    private MovimentacaoController movimentacaoController;
    @EJB
    private PecaController pecaController;
    private Movimentacao movimentacao;
    private Processo processo;
    private Adversario adversario;
    private ParteInteressada parteInteressada;
    private Fase fase;
    private List<Movimentacao> listaMovimentacaos;
    private List<Processo> listaProcessos;
    private List<Evento> listaEventos;
    private int i;

    @PostConstruct
    public void init() {
        processo = (Processo) navegacaoMB.getRegistroMapa("processo", new Processo());
        movimentacao = new Movimentacao();
        if (processo.getId() == null) {
            processo.setAdversarios(new ArrayList<Adversario>());
            processo.setParteInteressadas(new ArrayList<ParteInteressada>());
            fase = new Fase();
        } else {
            listaMovimentacaos = movimentacaoController.listarPorProcesso(processo);
            listaEventos = eventoController.listarPorProcessos(processo);
            fase = processo.getFase();
        }
        adversario = new Adversario();
        parteInteressada = new ParteInteressada();
        listaProcessos = new ArrayList<>();
        i = 0;
    }

    public void addInteressado() {
        try {
            interessadaController.salvar(parteInteressada);
            processo.getParteInteressadas().add(parteInteressada);
            parteInteressada = new ParteInteressada();
        } catch (Exception ex) {
            Logger.getLogger(ProcessoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addAdversario() {
        try {
            adversarioController.salvar(adversario);
            processo.getAdversarios().add(adversario);
            adversario = new Adversario();
        } catch (Exception ex) {
            Logger.getLogger(ProcessoMB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void salvar() {
        try {
            //setar cliente como o primeiro cliente da lista da parte interessada
            setarCliente(processo.getParteInteressadas().get(0).getCliente());
            processo.setUsuarioCadastro(navegacaoMB.getUsuarioLogado());
            controller.salvarouAtualizar(processo);
            init();
            MenssagemUtil.addMessageInfo(NavegacaoMB.getMsg("salvar", MenssagemUtil.MENSAGENS));

        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("falha", MenssagemUtil.MENSAGENS), ex, "Advogado");
            Logger.getLogger(ProcessoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Adicionar movimentação
     */
    public void addMovimentacao() {
        try {
            //Adiciona movimentação caso a fase mude
            movimentacao.setDataMovimentacao(new Date());
            movimentacao.setFaseAntiga(processo.getFase());
            movimentacao.setProcesso(processo);
            movimentacao.setUsuario(navegacaoMB.getUsuarioLogado());
            movimentacaoController.salvar(movimentacao);
            //atualizo fase do processo
            processo.setFase(movimentacao.getFaseNova());
            controller.atualizar(processo);
            processo = new Processo();
            movimentacao = new Movimentacao();
            MenssagemUtil.addMessageInfo(NavegacaoMB.getMsg("salvar", MenssagemUtil.MENSAGENS));
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("falha", MenssagemUtil.MENSAGENS), ex, "Movimentação");
            Logger.getLogger(ProcessoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Retorna se o proecesso está atrasado
     *
     * @param p
     * @return
     */
    public boolean processoAtrasado(Processo p) {
        if (p.getId() != null) {
            listaMovimentacaos = movimentacaoController.listarPorProcesso(p);

        }

        if (p.getFase() != null && listaMovimentacaos.size() > 0) {
            Calendar dataAtual = Calendar.getInstance();
            dataAtual.setTime(new Date());

            Calendar prazoFase = Calendar.getInstance();
            prazoFase.setTime(listaMovimentacaos.get(listaMovimentacaos.size() - 1).getDataMovimentacao());
            prazoFase.add(Calendar.DAY_OF_MONTH, p.getFase().getPrazo());

            return dataAtual.before(prazoFase);
        } else {
            return true;
        }

    }

    /**
     * Metodo para registrar o cliente do processo pegando o primeiro da lista
     * de interassados
     *
     * @param c
     */
    private void setarCliente(Cliente c) {
        if (c != null) {
            processo.setCliente(c);
        }
    }

    public void listar() {
        try {
            if (getValorBusca() == null || getValorBusca().equals("")) {
                listaProcessos = controller.listarTodos("numero");
            } else {
                listaProcessos = controller.listarLike("numero", getValorBusca());

            }
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("consulta.vazia", MenssagemUtil.MENSAGENS));
            Logger.getLogger(ProcessoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void excluir(Processo ee) {
        try {
            ee = controller.gerenciar(ee.getId());
            controller.excluir(ee);
            listaProcessos.remove(ee);
            MenssagemUtil.addMessageInfo(NavegacaoMB.getMsg("excluir", MenssagemUtil.MENSAGENS));

        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("excluir.falha", MenssagemUtil.MENSAGENS));
            Logger.getLogger(ProcessoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo utilizado para importar fase atual do projeto
     */
    public void utilitarioSetarFase() {
        try {
            listaProcessos = controller.listarTodos("id");
            for (Processo p : listaProcessos) {
                listaMovimentacaos = movimentacaoController.listarPorProcesso(p);
                if (!listaMovimentacaos.isEmpty()) {
                    p.setFase(listaMovimentacaos.get(listaMovimentacaos.size() - 1).getFaseNova());
                    controller.atualizar(p);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(ProcessoMB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void setarProcesso(Processo p) {
        processo = p;
        listaEventos = eventoController.listarPorProcessos(processo);
        listaMovimentacaos = movimentacaoController.listarPorProcesso(processo);
    }

    public void imprimir() {
        if (!listaProcessos.isEmpty()) {
            Map<String, Object> m = new HashMap<>();
            byte[] rel = new AssistentedeRelatorio().relatorioemByte(listaProcessos, m, "WEB-INF/relatorios/rel_especie_eventos.jasper", "Relatório de Processos");
            RelatorioSession.setBytesRelatorioInSession(rel);
        }

    }

    public ProcessoMB() {
        super(Processo.class);
    }

    public Processo getProcesso() {
        return processo;
    }

    public void setProcesso(Processo processo) {
        this.processo = processo;
    }

    public List<Processo> getListaProcessos() {
        return listaProcessos;
    }

    public void setListaProcessos(List<Processo> listaProcessos) {
        this.listaProcessos = listaProcessos;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public ParteInteressada getParteInteressada() {
        return parteInteressada;
    }

    public void setParteInteressada(ParteInteressada parteInteressada) {
        this.parteInteressada = parteInteressada;
    }

    public Adversario getAdversario() {
        return adversario;
    }

    public void setAdversario(Adversario adversario) {
        this.adversario = adversario;
    }

    public List<Evento> getListaEventos() {
        return listaEventos;
    }

    public void setListaEventos(List<Evento> listaEventos) {
        this.listaEventos = listaEventos;
    }

    public List<Movimentacao> getListaMovimentacaos() {
        return listaMovimentacaos;
    }

    public void setListaMovimentacaos(List<Movimentacao> listaMovimentacaos) {
        this.listaMovimentacaos = listaMovimentacaos;
    }

    public Movimentacao getMovimentacao() {
        return movimentacao;
    }

    public void setMovimentacao(Movimentacao movimentacao) {
        this.movimentacao = movimentacao;
    }

    public Fase getFase() {
        return fase;
    }

    public void setFase(Fase fase) {
        this.fase = fase;
    }

    public StreamedContent downloadModelo(Processo processo, Peca peca) {
        try {
            return pecaController.getModeloDownload(peca, processo);
        } catch (PecaFileException | FileNotFoundException | Docx4JException | IllegalArgumentException | IllegalAccessException | InvocationTargetException | NoSuchFieldException | ClassNotFoundException e) {
            MenssagemUtil.addMessageErro(e);
            Logger.getLogger(ProcessoMB.class.getName()).log(Level.SEVERE, null, e);

        }
        return null;
    }

}
