/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.util;

import br.com.atus.modelo.Peca;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author gilmario
 */
public class DocumentoConverter {

    public static final String PARAMETROS_PADRAO = ".\\$\\{\\w+\\}";

    /**
     * Converter o arquivo preenchendo com os valores do objeto que são iguais
     * aos parametros do objeto
     *
     * @param stream
     * @param entidade
     * @param peca
     * @return
     * @throws org.docx4j.openpackaging.exceptions.Docx4JException
     * @throws java.lang.IllegalAccessException
     * @throws java.io.FileNotFoundException
     */
    public static StreamedContent converterArquivo(InputStream stream, Object entidade, Peca peca) throws Docx4JException, IllegalArgumentException, IllegalAccessException, FileNotFoundException {
        WordprocessingMLPackage wordMLPackage;
        wordMLPackage = WordprocessingMLPackage.load(stream);
        MainDocumentPart arquivoBase = wordMLPackage.getMainDocumentPart();
        List<Object> linhas = arquivoBase.getContent();
        WordprocessingMLPackage destino = WordprocessingMLPackage.createPackage();
        for (Object valor : linhas) {
            String texto = valor.toString();
            Matcher matcher = RegexUtil.getMatcher(PARAMETROS_PADRAO, texto);
            if (matcher.find()) {
                //Troca aqui o valor parametro do texto pelo valor do objeto
                for (Field campo : getListaCampos(entidade.getClass())) {
                    if (texto.contains("${" + campo + "}")) {
                        texto = texto.replaceAll("\\$\\{" + campo + "\\}", campo.get(entidade).toString());
                    }
                }
            }
            // Colocar o texto do primeiro arquivo no segundo
            destino.getMainDocumentPart().addParagraphOfText(texto);
        }

        File file = new File("temp");
        destino.save(file);
        StreamedContent context = new DefaultStreamedContent(new FileInputStream(file), "docx", peca.getArquivo());
        return context;
    }

    /**
     * Verificar se os atributos do documento estão dentro da entidade.
     *
     * @param stream
     * @param entidade
     * @return
     * @throws org.docx4j.openpackaging.exceptions.Docx4JException
     */
    public static boolean validarArquivo(InputStream stream, Class entidade) throws Docx4JException {
        WordprocessingMLPackage wordMLPackage;
        boolean valido = false;
        wordMLPackage = WordprocessingMLPackage.load(stream);
        MainDocumentPart arquivoBase = wordMLPackage.getMainDocumentPart();
        List<Object> linhas = arquivoBase.getContent();
        for (Object valor : linhas) {
            String texto = valor.toString();
            Matcher matcher = RegexUtil.getMatcher(PARAMETROS_PADRAO, texto);
            if (matcher.find()) {
                // Verifica se o parametro existe na classe
                for (Field campo : getListaCampos(entidade)) {
                    if (texto.contains("${" + campo.getName() + "}")) {
                        valido = true;
                    }
                }
            }
        }
        return valido;
    }

    private static List<Field> getListaCampos(Class classe) {
        List<Field> campos = new ArrayList<>();
        for (Field field : classe.getDeclaredFields()) {
            campos.add(field);
        }
        return campos;
    }

}
