/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author ari
 */
public class ManipuladorDeArquivo {

    public static final String SEPARADOR = System.getProperty("file.separator");
    public static final String PATH_NOTIFICACOES_WINDOWS = "C:" + SEPARADOR ;
    public static final String PATH_NOTIFICACOES_LINUX = "/arquivos" + SEPARADOR ;
    public static final String PASTA_PECAS = "pecas" + SEPARADOR;
    public static final String PASTA_NOTIFICACOES = "notificacoes" + SEPARADOR;
    public static final String PATH_FILES = "pecas" + SEPARADOR + "documentos";

    private static boolean ehLinux() {
        String os = System.getProperties().getProperty("os.name").toLowerCase();
        if (os.contains("windows")) {
            return false;
        }
        return true;
    }

    /**
     * Criar o arquivo com os dados(bytes) do banco
     *
     * @param file
     * @param pasta
     * @param nomeArquivo
     * @param conteudoArquivo
     * @throws Exception
     */
    private static void criarArquivoCasoNaoExista(File file,String pecaOuNotificacao,String pasta, String nomeArquivo, byte[] conteudoArquivo) throws Exception {
        if (!file.exists()) {
            ManipuladorDeArquivo.gravaArquivo(pecaOuNotificacao,pasta, nomeArquivo, conteudoArquivo);
        }
    }

    /**
     * O arquivo deve ser passado com extenção
     *
     * @param pecaOuNotificacao
     * @param pasta
     * @param nomeArquivo
     * @param conteudoArquivo
     * @return
     * @throws Exception
     */
    public static String checarExistenciaDoArquivoNaPasta(String pecaOuNotificacao,String pasta, String nomeArquivo, byte[] conteudoArquivo) throws Exception {
        File file;

        String caminho = pecaOuNotificacao.concat(pasta.concat(SEPARADOR.concat(nomeArquivo)));

        if (ehLinux()) {
            file = new File(PATH_NOTIFICACOES_LINUX + SEPARADOR + caminho);
        } else {
            file = new File(PATH_NOTIFICACOES_WINDOWS + SEPARADOR + caminho);

        }
        if (!file.exists()) {
            ManipuladorDeArquivo.gravaArquivo(pecaOuNotificacao,pasta, nomeArquivo, conteudoArquivo);
        }
        return file.getAbsolutePath();

    }

    public static String caminhoDoArquivo(String pecaOuNotificacao,String pasta, String nome) {
        File pastaGeral = new File(PATH_NOTIFICACOES_WINDOWS + pecaOuNotificacao + pasta);
        if (ehLinux()) {
            pastaGeral = new File(PATH_NOTIFICACOES_LINUX + pecaOuNotificacao + pasta);
        }
        return pastaGeral + SEPARADOR + nome;
    }

// Pasta relativa onde deve ficar os arquivos do sistema
    public static void gravaArquivo(String pecaOuNotificacao,String pasta, String nome, byte[] conteudo) throws IOException, Exception {
        File pastaGeral = new File(PATH_NOTIFICACOES_WINDOWS + pecaOuNotificacao + pasta);
        if (ehLinux()) {
            pastaGeral = new File(PATH_NOTIFICACOES_LINUX + pecaOuNotificacao + pasta);
        }

        if (!pastaGeral.exists()) {
            if (!pastaGeral.mkdirs()) {
                throw new Exception("Erro ao cria pasta relativa");
            }
        } else {
            pastaGeral.delete();
        }

        try (FileOutputStream writer = new FileOutputStream(pastaGeral + SEPARADOR + nome)) {
            writer.write(conteudo);
            writer.flush();
        }
    }

    public static String diretorioRelativoNotificacao(String diretorioRealDaAplicacao) {
        String localAbs = diretorioRealDaAplicacao;
        String relativo = localAbs.substring(0, localAbs.lastIndexOf(SEPARADOR));
        relativo = relativo.substring(0, relativo.lastIndexOf(SEPARADOR));
        relativo = relativo.substring(0, relativo.lastIndexOf(SEPARADOR));

        if (ehLinux()) {
            return relativo + SEPARADOR + PATH_NOTIFICACOES_LINUX;
        } else {
            return relativo + SEPARADOR + PATH_NOTIFICACOES_WINDOWS;
        }

    }

    public static String retornaDiretorioDoSistemaOperacional() {
        if (ehLinux()) {
            return PATH_NOTIFICACOES_LINUX;
        } else {
            return PATH_NOTIFICACOES_WINDOWS;
        }
    }

    // Listar os arquivos de uma pasta
    public static List<File> aquivos(String pecaOunotificacao,String pasta) {
        String relativo = retornaDiretorioDoSistemaOperacional()+ pecaOunotificacao + pasta;
        File f = new File(relativo);
        if (f.exists()) {
            return Arrays.asList(f.listFiles());
        } else {
            return new ArrayList<>();
        }
    }

    public static void uploadArquivo(FileUploadEvent event, String pecaOunotificacao,String pasta) throws IOException, Exception {
        UploadedFile f = event.getFile();
        ManipuladorDeArquivo.gravaArquivo(pecaOunotificacao , pasta, f.getFileName(), f.getContents());
    }

    public static String diretorioRelativo() {
        String localAbs = new AssistentedeRelatorio().getDiretorioReal(SEPARADOR);
        String relativo = localAbs.substring(0, localAbs.lastIndexOf(SEPARADOR));
        relativo = relativo.substring(0, relativo.lastIndexOf(SEPARADOR));
        relativo = relativo.substring(0, relativo.lastIndexOf(SEPARADOR));
        return relativo + SEPARADOR + PATH_FILES;

    }

    public static void excluirAquivo(String path, String nomeFile) {
        File file = new File(diretorioRelativoNotificacao(nomeFile) + SEPARADOR + path + SEPARADOR + nomeFile);
        if (file.exists()) {
            file.delete();
        }
    }
}
