package br.com.atus.util;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
 * @author gilmario
 */
public class ArquivoUtil {

    public static final String PATH_FILES = "pecas/documentos";

    // Listar os arquivos de uma pasta
    public static List<File> aquivos(String pasta) {
        String relativo = diretorioRelativo() + "/" + pasta;
        File f = new File(relativo);
        if (f.exists()) {
            return Arrays.asList(f.listFiles());
        } else {
            return new ArrayList<>();
        }
    }

    // Pasta relativa onde deve ficar os arquivos do sistema
    public static void gravaArquivo(String local, String nome, byte[] conteudo) throws IOException, Exception {
        File pastaGeral = new File(diretorioRelativo() + "/" + local);
        if (!pastaGeral.exists()) {
            if (!pastaGeral.mkdirs()) {
                throw new Exception("Erro ao cria pasta relativa");
            }
        }

        try (FileOutputStream writer = new FileOutputStream(pastaGeral + "/" + nome)) {
            writer.write(conteudo);
            writer.flush();
        }
    }

    public static void uploadArquivo(FileUploadEvent event, String pasta) throws IOException, Exception {
        String relativo = diretorioRelativo();
        UploadedFile f = event.getFile();
        ArquivoUtil.gravaArquivo(relativo + "/" + pasta, f.getFileName(), f.getContents());
    }

    public static String diretorioRelativo() {
        String localAbs = new AssistentedeRelatorio().getDiretorioReal("/");
        String relativo = localAbs.substring(0, localAbs.lastIndexOf("/"));
        relativo = relativo.substring(0, relativo.lastIndexOf("/"));
        relativo = relativo.substring(0, relativo.lastIndexOf("/"));
        return relativo + "/" + PATH_FILES;

    }

    public static void excluirAquivo(String path, String nomeFile) {
        File file = new File(diretorioRelativo() + "/" + path + "/" + nomeFile);
        file.delete();
    }
}
