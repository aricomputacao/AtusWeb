/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.processo.managedbean;

import br.com.atus.processo.controller.AdversarioController;
import br.com.atus.processo.controller.EventoController;
import br.com.atus.processo.controller.MovimentacaoController;
import br.com.atus.processo.controller.ParteInteressadaController;
import br.com.atus.processo.controller.PecaController;
import br.com.atus.processo.controller.ProcessoController;
import br.com.atus.dto.ProcessoUltimaMovimentacaoDTO;
import br.com.atus.financeiro.controller.ParcelaReceberController;
import br.com.atus.financeiro.modelo.ParcelasReceber;
import br.com.atus.util.managedbean.BeanGenerico;
import br.com.atus.util.managedbean.NavegacaoMB;
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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
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
  
    @Inject
    private ProcessoController controller;
    @Inject
    private ParteInteressadaController interessadaController;
    @Inject
    private AdversarioController adversarioController;
    @Inject
    private EventoController eventoController;
    @Inject
    private MovimentacaoController movimentacaoController;
    @Inject
    private PecaController pecaController;
    @Inject
    private ParcelaReceberController parcelaReceberController;

    private Movimentacao movimentacao;
    private Processo processo;
    private Adversario adversario;
    private ParteInteressada parteInteressada;
    private Fase fase;
    private Cliente cliente;
    private List<Movimentacao> listaMovimentacaos;
    private List<Processo> listaProcessos;
    private List<ProcessoUltimaMovimentacaoDTO> listaUltimaMovimentacaoDTOs;
    private List<Evento> listaEventos;
    private List<ParcelasReceber> listaParcelasAbertas;
    private List<ParcelasReceber> listaParcelasVencidas;
    private List<ParcelasReceber> listaParcelasPagas;
    private int i;
    private boolean renderPesquisa;

    @PostConstruct
    public void init() {

        setValorBusca((String) navegacaoMB.getRegistroMapa("nomeCliente", ""));
        if (!getValorBusca().equals("")) {
            setCampoBusca("cliente");
        }

        processo = (Processo) navegacaoMB.getRegistroMapa("processo", new Processo());
        movimentacao = new Movimentacao();
        cliente = new Cliente();
        if (processo.getId() == null) {
            processo.setAdversarios(new ArrayList<Adversario>());
            processo.setParteInteressadas(new ArrayList<ParteInteressada>());
            fase = new Fase();
            listaParcelasAbertas = new ArrayList<>();
            listaParcelasVencidas = new ArrayList<>();
            listaParcelasPagas = new ArrayList<>();
        } else {
            fase = processo.getFase();
            consultarPagamentos();
        }

        listaProcessos = new ArrayList<>();
        listaMovimentacaos = movimentacaoController.listarPorProcesso(processo);
        listaEventos = eventoController.listarPorProcessos(processo);
        listaUltimaMovimentacaoDTOs = new ArrayList<>();
        adversario = new Adversario();
        parteInteressada = new ParteInteressada();
        i = 0;
    }

    public void aposSalvar() {
        try {
            processo = controller.carregar(processo.getId());
            movimentacao = new Movimentacao();
            cliente = new Cliente();
            fase = processo.getFase();
            listaMovimentacaos = movimentacaoController.listarPorProcesso(processo);
            listaEventos = eventoController.listarPorProcessos(processo);
//            listaEventos = eventoController.listarPorProcessos(processo);
            adversario = new Adversario();
            parteInteressada = new ParteInteressada();
            listaProcessos = new ArrayList<>();
            i = 0;
        } catch (Exception ex) {
            Logger.getLogger(ProcessoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void consultarPagamentos() {
        listaParcelasAbertas = parcelaReceberController.consultaParcelasAbertasDo(processo);
        listaParcelasVencidas = parcelaReceberController.consultaParcelasVencidasDo(processo);
        listaParcelasPagas = parcelaReceberController.consultaParcelasPagasDo(processo);
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

    public void consultarEventosDoProcesso() {
        listaEventos = eventoController.listarPorProcessos(processo);

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

    public void delAdversario(Adversario adv, Processo p) {
        try {
            p.getAdversarios().remove(adv);
            controller.atualizar(p);
            adv = adversarioController.gerenciar(adv.getId());
            adversarioController.excluir(adv);
            MenssagemUtil.addMessageInfo(NavegacaoMB.getMsg("excluir_adversario", MenssagemUtil.MENSAGENS));
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("excluir.falha", MenssagemUtil.MENSAGENS));
            Logger.getLogger(ProcessoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void delInteressado(ParteInteressada pi, Processo p) {
        try {
            p.getParteInteressadas().remove(pi);
            controller.atualizar(p);
            pi = interessadaController.gerenciar(pi.getId());
            interessadaController.excluir(pi);

            MenssagemUtil.addMessageInfo(NavegacaoMB.getMsg("excluir_interessado", MenssagemUtil.MENSAGENS));
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("excluir.falha", MenssagemUtil.MENSAGENS));
            Logger.getLogger(ProcessoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void salvar() {
        try {
            //setar cliente como o primeiro cliente da lista da parte interessada
            setarCliente(processo.getParteInteressadas().get(0).getCliente());
            controller.salvarouAtualizarEditarFase(processo, fase, navegacaoMB.getUsuarioLogado());
            aposSalvar();
            MenssagemUtil.addMessageInfo(NavegacaoMB.getMsgParametro("salvar_processo", MenssagemUtil.MENSAGENS, processo.getId().toString()));

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
            processo.setMotivoFase(movimentacao.getMotivo());
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
     * @throws java.lang.Exception
     */
    public boolean processoAtrasado(Processo p) throws Exception {

        if (p.getFase() != null) {
            Calendar dataAtual = Calendar.getInstance();
            dataAtual.setTime(new Date());
            Movimentacao ultimaMovimentacao = movimentacaoController.ultimaMovimentacao(p);
            Calendar prazoFase = Calendar.getInstance();
            if (ultimaMovimentacao != null) {
                prazoFase.setTime(movimentacaoController.ultimaMovimentacao(p).getDataMovimentacao());
            } else {
                Calendar dt = Calendar.getInstance();
                dt.setTime(p.getDataCadastro());
                prazoFase.setTime(dt.getTime());
            }
            prazoFase.add(Calendar.DAY_OF_MONTH, p.getFase().getPrazo());

            boolean b = (dataAtual.compareTo(prazoFase) < 0);
//            System.out.print(b);
//
//            System.out.print(p.getFase().getPrazo());
//            System.out.print(MetodosUtilitarios.formatarData(new Date(listaMovimentacaos.get(listaMovimentacaos.size() - 1).getDataMovimentacao().getTime())));
//
//            System.out.print(MetodosUtilitarios.formatarData(new Date(dataAtual.getTimeInMillis())));
//            System.out.print(MetodosUtilitarios.formatarData(new Date(prazoFase.getTimeInMillis())));

            return b;
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

    public void renderizarPesquisa() {
        if (getCampoBusca().equals("numero") || getCampoBusca().equals("id")) {
            renderPesquisa = false;
        } else {
            renderPesquisa = true;
        }
    }

    public void listar() {
        try {

            switch (getCampoBusca()) {
                case "id":
                    listaProcessos.clear();
                    processo = controller.carregarPor(Long.decode(getValorBusca()), navegacaoMB.getUsuarioLogado());
                    listaProcessos.add(processo);
                    break;
                case "numero":
                    listaProcessos = controller.consultaLikePor(getValorBusca(), navegacaoMB.getUsuarioLogado());
                    break;
                case "cliente":
                    listaProcessos = controller.consultaPorLike(getValorBusca(), navegacaoMB.getUsuarioLogado());
                    break;

            }
            listaUltimaMovimentacaoDTOs = controller.ultimasMovimentacoesDe(listaProcessos);

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

    public void navegarDeAtendimentoParaConsultaDeProcesso(String nomeCliente) {
        try {
            navegacaoMB.redirecionarConsulta("pro.processo");
            listaProcessos = controller.consultaPorLike(nomeCliente, navegacaoMB.getUsuarioLogado());
            listaUltimaMovimentacaoDTOs = controller.ultimasMovimentacoesDe(listaProcessos);
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(NavegacaoMB.getMsg("consulta.vazia", MenssagemUtil.MENSAGENS));

            Logger.getLogger(ProcessoMB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Metodo utilizado para importar fase atual do projeto
     */
    public void utilitarioSetarFase() {
        try {
            listaProcessos = controller.consultarTodos("id");
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
        consultarPagamentos();
    }

    public void imprimirProcesso() {
        if (!listaUltimaMovimentacaoDTOs.isEmpty()) {
            Map<String, Object> m = new HashMap<>();
            byte[] rel = new AssistentedeRelatorio().relatorioemByte(listaUltimaMovimentacaoDTOs, m, "WEB-INF/relatorios/rel_processos.jasper", "Relatório de Processos");
            RelatorioSession.setBytesRelatorioInSession(rel);
        }

    }

    public void imprimirDossie() {
        List<Processo> l = new ArrayList<>();
        l.add(processo);
        Map<String, Object> m = new HashMap<>();
        m.put("id", processo.getId());
        m.put("endere", processo.getEnderecamento().getNome());
        byte[] rel = new AssistentedeRelatorio().relatorioemByte(l, m, "WEB-INF/relatorios/rel_dossie.jasper", "Relatório de Processos");
        RelatorioSession.setBytesRelatorioInSession(rel);

    }

    public void imprimirProtocolo() {
        List<Processo> l = new ArrayList<>();
        l.add(processo);
        Map<String, Object> m = new HashMap<>();
        m.put("id", processo.getId());
        m.put("endere", processo.getEnderecamento().getNome());
        byte[] rel = new AssistentedeRelatorio().relatorioemByte(l, m, "WEB-INF/relatorios/rel_protocolo.jasper", "Relatório de Processos");
        RelatorioSession.setBytesRelatorioInSession(rel);

    }

    public void imprimirProcuracaoAdJud() {
        List<Processo> l = new ArrayList<>();
        l.add(processo);
        Map<String, Object> m = new HashMap<>();
        m.put("id", processo.getId());
        m.put("endere", processo.getEnderecamento().getNome());
        byte[] rel = new AssistentedeRelatorio().relatorioemByte(l, m, "WEB-INF/relatorios/rel_proc_ad_judicia.jasper", "Relatório de Processos");
        RelatorioSession.setBytesRelatorioInSession(rel);

    }

    public void imprimirDeclaracao() {
        List<Processo> l = new ArrayList<>();
        l.add(processo);
        Map<String, Object> m = new HashMap<>();
        m.put("id", processo.getId());
        m.put("endere", processo.getEnderecamento().getNome());
        byte[] rel = new AssistentedeRelatorio().relatorioemByte(l, m, "WEB-INF/relatorios/re_declaracao_hipo.jasper", "Relatório de Processos");
        RelatorioSession.setBytesRelatorioInSession(rel);

    }

    public void imprimirContrato() {
        List<Processo> l = new ArrayList<>();
        l.add(processo);
        Map<String, Object> m = new HashMap<>();
        m.put("id", processo.getId());
        m.put("endere", processo.getEnderecamento().getNome());
        byte[] rel = new AssistentedeRelatorio().relatorioemByte(l, m, "WEB-INF/relatorios/rel_contrato_honorarios.jasper", "Relatório de Processos");
        RelatorioSession.setBytesRelatorioInSession(rel);

    }

    public void imprimirTodos() {
        imprimirContrato();
        imprimirDeclaracao();
        imprimirDossie();
        imprimirProcuracaoAdJud();
        imprimirProtocolo();
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
        } catch (Exception e) {
            MenssagemUtil.addMessageErro(e);
            Logger.getLogger(ProcessoMB.class.getName()).log(Level.SEVERE, null, e);

        }
        return null;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public boolean isRenderPesquisa() {
        return renderPesquisa;
    }

    public void setRenderPesquisa(boolean renderPesquisa) {
        this.renderPesquisa = renderPesquisa;
    }

    public List<ProcessoUltimaMovimentacaoDTO> getListaUltimaMovimentacaoDTOs() {
        return listaUltimaMovimentacaoDTOs;
    }

    public void setListaUltimaMovimentacaoDTOs(List<ProcessoUltimaMovimentacaoDTO> listaUltimaMovimentacaoDTOs) {
        this.listaUltimaMovimentacaoDTOs = listaUltimaMovimentacaoDTOs;
    }

    public List<ParcelasReceber> getListaParcelasAbertas() {
        return listaParcelasAbertas;
    }

    public List<ParcelasReceber> getListaParcelasVencidas() {
        return listaParcelasVencidas;
    }

    public List<ParcelasReceber> getListaParcelasPagas() {
        return listaParcelasPagas;
    }

}
