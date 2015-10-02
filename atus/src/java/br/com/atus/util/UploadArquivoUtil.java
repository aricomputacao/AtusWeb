/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.util;

import br.com.atus.processo.modelo.NotificacaoProcesso;
import static br.com.atus.util.peca.ArquivoUtil.diretorioRelativo;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.faces.context.FacesContext;

/**
 *
 * @author ari
 */
public class UploadArquivoUtil {

    public static final String SEPARADOR = System.getProperty("file.separator");
    public static final String PATH_NOTIFICACOES_WINDOWS = "C:" + SEPARADOR + "notificacoes";
    public static final String PATH_NOTIFICACOES_LINUX = "/home" + SEPARADOR + "notificacoes";

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
    private static void criarArquivoCasoNaoExista(File file, String pasta, String nomeArquivo, byte[] conteudoArquivo) throws Exception {
        if (!file.exists()) {
            UploadArquivoUtil.gravaArquivoNoticacao(pasta, nomeArquivo, conteudoArquivo);
        }
    }

    public static String checarExistenciaDoArquivoNaPasta(NotificacaoProcesso np) throws Exception {
        File file;
        
        String pasta = np.getProcesso().getId().toString();
        String arquivo = np.getId().toString().concat(" - ").concat(np.getNome()) + ".pdf";
        String caminho = pasta.concat(SEPARADOR.concat(arquivo));
        
        if (ehLinux()) {
            file = new File(PATH_NOTIFICACOES_LINUX + SEPARADOR + caminho);
            criarArquivoCasoNaoExista(file, np.getProcesso().getId().toString(), np.getNome(), np.getArquivo());
        } else {
            file = new File(PATH_NOTIFICACOES_WINDOWS + SEPARADOR + caminho);
            criarArquivoCasoNaoExista(file, np.getProcesso().getId().toString(), np.getNome(), np.getArquivo());
        }
        return file.getAbsolutePath();

    }

    public static String caminhoDoArquivo(String pasta, String nome) {
        File pastaGeral = new File(PATH_NOTIFICACOES_WINDOWS + SEPARADOR + pasta);
        if (ehLinux()) {
            pastaGeral = new File(PATH_NOTIFICACOES_LINUX + SEPARADOR + pasta);
        }
        return pastaGeral + SEPARADOR + nome;
    }

// Pasta relativa onde deve ficar os arquivos do sistema
    public static void gravaArquivoNoticacao(String pasta, String nome, byte[] conteudo) throws IOException, Exception {
        File pastaGeral = new File(PATH_NOTIFICACOES_WINDOWS + SEPARADOR + pasta);
        if (ehLinux()) {
            pastaGeral = new File(PATH_NOTIFICACOES_LINUX + SEPARADOR + pasta);
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

    public static String diretorioRelativoNotificacao() {
        String localAbs = new AssistentedeRelatorio().getDiretorioReal(SEPARADOR);
        String relativo = localAbs.substring(0, localAbs.lastIndexOf(SEPARADOR));
        relativo = relativo.substring(0, relativo.lastIndexOf(SEPARADOR));
        relativo = relativo.substring(0, relativo.lastIndexOf(SEPARADOR));

        if (ehLinux()) {
            return relativo + SEPARADOR + PATH_NOTIFICACOES_LINUX;
        } else {
            return relativo + SEPARADOR + PATH_NOTIFICACOES_WINDOWS;
        }

    }

    public static void excluirAquivo(String path, String nomeFile) {
        File file = new File(diretorioRelativo() + SEPARADOR + path + SEPARADOR + nomeFile);
        if (file.exists()) {
            file.delete();
        }
    }

    // Listar os arquivos de uma pasta
    public static List<File> aquivos(String pasta) {
        String relativo = diretorioRelativoNotificacao() + SEPARADOR + pasta;
        File f = new File(relativo);
        if (f.exists()) {
            return Arrays.asList(f.listFiles());
        } else {
            return new ArrayList<>();
        }
    }

//    public List<String> getImages() throws SQLException, IOException {
//
////        List<NotificacaoProcesso> listaLocais = notificacaoProcessoController.consultarPor(processo);
//        List<String> images = new ArrayList<String>();
//        String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/imagens");
////        for (NotificacaoProcesso local : listaLocais) {
////            FileOutputStream fos = new FileOutputStream(path + "/" + local.getNome() + ".jpg");
////            fos.write(local.getArquivo());
////            fos.close();
////            images.add(local.getNome() + ".jpg");
////        }
//        return images;
//    }
    public static void main(String[] args) {
        System.out.println(UploadArquivoUtil.ehLinux());
    }
}
