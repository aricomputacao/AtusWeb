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
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
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

    public static final String PARAMETROS_PADRAO = "\\$\\{\\w+.\\w+\\}";
    public static final String STRING_ESQUERDA = "\\$\\{";
    public static final String STRING_DIREITA = "\\}";
    public static final String PARTE_ESQUERDA = "${";
    public static final String PARTE_DIREITA = "}";

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

    public static StreamedContent converterArquivo(InputStream stream, Object entidade, Peca peca) throws Docx4JException, IllegalArgumentException, IllegalAccessException, FileNotFoundException, InvocationTargetException, NoSuchFieldException, ClassNotFoundException {
        WordprocessingMLPackage template = getTemplate(stream);
        List<Object> linhas = getAllElementFromObject(template.getMainDocumentPart(), Text.class);
        for (Object text : linhas) {
            Text textElement = (Text) text;
            String texto = textElement.getValue();
            Matcher matcher = RegexUtil.getMatcher(PARAMETROS_PADRAO, texto);
            System.out.println("linha " + texto + " ");
            if (matcher.find()) {
                //Troca aqui o valor parametro do texto pelo valor do objeto
                for (String campo : getListaCampos(entidade.getClass(), "")) {
                    if (texto.contains(PARTE_ESQUERDA + campo + PARTE_DIREITA)) {
                        Object valor = executaMetodo(campo, entidade);
                        if (valor instanceof List) {
                            for (Object o : (List) valor) {
                                System.out.println(o);
                            }
                        }
                        texto = texto.replaceAll(STRING_ESQUERDA + campo + STRING_DIREITA, valor.toString());
                        textElement.setValue(texto);
                        System.out.println(campo + " foi substituido por " + valor);
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
    public static boolean validarArquivo(InputStream stream, Class entidade) throws Docx4JException, NoSuchFieldException, ClassNotFoundException {
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
                for (String campo : getListaTags(entidade)) {
                    if (texto.contains(campo)) {
                        valido = true;
                    }
                }
            }
        }
        return valido;
    }

    private static List<String> getListaCampos(Class classe, String prefix) throws NoSuchFieldException, ClassNotFoundException {
        List<String> campos = new ArrayList<>();
        for (Field field : classe.getDeclaredFields()) {
            try {
                PecaColetor p = (PecaColetor) field.getAnnotation(PecaColetor.class);
                if (p != null) {
                    if (p.isEntidade()) {
                        campos.addAll(getListaCampos(field.getType(), prefix + field.getName() + "."));
                    } else if (p.isLista()) {
                        campos.addAll(getListaCampos(tipoLista(classe, field.getName()), "Lista" + (prefix + field.getName()).substring(0, 1).toUpperCase() + "."));
                    } else {
                        campos.add(prefix + field.getName());
                    }
                }
            } catch (NullPointerException n) {
                // Se nãp possue não adiciona
            }
        }
        return campos;
    }

    public static List<String> getListaTags(Class classe) throws NoSuchFieldException, ClassNotFoundException {
        List<String> campos = new ArrayList<>();
        for (String campo : getListaCampos(classe, "")) {
            campos.add(PARTE_ESQUERDA + campo + PARTE_DIREITA);
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

    public static Class tipoLista(Class entidade, String nomeVar) throws NoSuchFieldException, ClassNotFoundException {
        Field f = entidade.getDeclaredField(nomeVar);
        Type t = ((ParameterizedType) f.getGenericType()).getActualTypeArguments()[0];
        return Class.forName(t.toString().replace("class ", ""));
    }

    // Retorna Uma String vazia no caso de ser nulo
    private static Object executaMetodo(String campo, Object entidade) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (campo.contains(".")) {
            String[] partes = campo.split("\\.");
            String c = partes[0];
            String m = partes[1];
            if (partes.length > 2) {
                m = campo.replace(c + ".", "");
            }
            Object valor = executaMetodo(c, entidade);
            return executaMetodo(m, valor);
        } else {
            Method metodo = getMetodo(campo, entidade.getClass());
            if (metodo != null) {
                Object valor = metodo.invoke(entidade);
                return valor == null ? "" : valor;
            } else {
                return "";
            }
        }
    }

}
