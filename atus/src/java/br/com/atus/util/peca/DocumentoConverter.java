/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.util.peca;

import br.com.atus.modelo.Peca;
import br.com.atus.util.RegexUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import javax.xml.bind.JAXBElement;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.ContentAccessor;
import org.docx4j.wml.Text;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author gilmario
 */
public class DocumentoConverter {

    public static final String PARAMETROS_PADRAO = "\\$\\{\\w+\\}";

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
    private void substituirPalavra(WordprocessingMLPackage template, String valor, String expressao) {
        List<Object> texts = getAllElementFromObject(template.getMainDocumentPart(), Text.class);
        for (Object text : texts) {
            Text textElement = (Text) text;
            if (textElement.getValue().equals(expressao)) {
                textElement.setValue(valor);
            }
        }
    }

    private static WordprocessingMLPackage getTemplate(String name) throws Docx4JException, FileNotFoundException {
        WordprocessingMLPackage template = WordprocessingMLPackage.load(new File(name));
        return template;
    }

    private static WordprocessingMLPackage getTemplate(InputStream is) throws Docx4JException, FileNotFoundException {
        WordprocessingMLPackage template = WordprocessingMLPackage.load(is);
        return template;
    }

    private static List<Object> getAllElementFromObject(Object obj, Class<?> toSearch) {
        List<Object> result = new ArrayList<>();
        if (obj instanceof JAXBElement) {
            obj = ((JAXBElement<?>) obj).getValue();
        }
        if (obj.getClass().equals(toSearch)) {
            result.add(obj);
        } else if (obj instanceof ContentAccessor) {
            List<?> children = ((ContentAccessor) obj).getContent();
            for (Object child : children) {
                result.addAll(getAllElementFromObject(child, toSearch));
            }
        }
        return result;
    }

    public static StreamedContent converterArquivo(InputStream stream, Object entidade, Peca peca) throws Docx4JException, IllegalArgumentException, IllegalAccessException, FileNotFoundException, InvocationTargetException {
        WordprocessingMLPackage template = getTemplate(stream);
        List<Object> linhas = getAllElementFromObject(template.getMainDocumentPart(), Text.class);
        for (Object text : linhas) {
            Text textElement = (Text) text;
            String texto = textElement.getValue();
            Matcher matcher = RegexUtil.getMatcher(PARAMETROS_PADRAO, texto);
            System.out.println(texto);
            if (matcher.find()) {
                //Troca aqui o valor parametro do texto pelo valor do objeto
                for (Field campo : getListaCampos(entidade.getClass())) {
                    String nomeCampo = campo.getName();

                    System.out.println(nomeCampo);
                    if (texto.contains("${" + nomeCampo + "}")) {
                        String valorzz = getMetodo(nomeCampo, entidade.getClass()).invoke(entidade).toString();
                        texto = texto.replaceAll("\\$\\{" + nomeCampo + "\\}", valorzz);
                        textElement.setValue(texto);
                        System.out.println(valorzz);
                        System.out.println(nomeCampo);
                    }
                }
            }
        }

        File file = new File("temp.docx");
        template.save(file);
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
            try {
                PecaColetor p = (PecaColetor) field.getAnnotation(PecaColetor.class);
                if (p != null) {
                    campos.add(field);
                }
            } catch (NullPointerException n) {
                // Se nãp possue
            }
        }
        return campos;
    }

    public static List<String> getListaTags(Class classe) {
        List<String> campos = new ArrayList<>();
        for (Field f : classe.getDeclaredFields()) {
            campos.add("${" + f.getName() + "}");
        }
        return campos;
    }

    private static Method getMetodo(String nomeCampo, Class classe) {
        for (Method m : classe.getDeclaredMethods()) {
            if (m.getName().toLowerCase().equals("get" + nomeCampo)) {
                return m;
            }
        }
        return null;
    }

}
