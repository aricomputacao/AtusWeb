/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.util.peca;

import br.com.atus.modelo.Peca;
import br.com.atus.util.MascaraUtil;
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
import org.docx4j.wml.ContentAccessor;
import org.docx4j.wml.Text;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author gilmario
 */
public class DocumentoConverter {

    public static final String PARAMETROS_PADRAO = "\\$\\{[a-z|.|A-Z|0-9]+\\}";
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
    /**
     * private void substituirPalavra(WordprocessingMLPackage template, String
     * valor, String expressao) { List<Object> texts =
     * getAllElementFromObject(template.getMainDocumentPart(), Text.class); for
     * (Object text : texts) { Text textElement = (Text) text; if
     * (textElement.getValue().equals(expressao)) { textElement.setValue(valor);
     * } } }
     *
     */
    private static WordprocessingMLPackage getTemplate(String name) throws Docx4JException, FileNotFoundException {
        WordprocessingMLPackage template = WordprocessingMLPackage.load(new File(name));
        return template;
    }

    public static WordprocessingMLPackage getTemplate(InputStream is) throws Docx4JException, FileNotFoundException {
        WordprocessingMLPackage template = WordprocessingMLPackage.load(is);
        return template;
    }

    public static List<Object> getAllElementFromObject(Object obj, Class<?> toSearch) {
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

    public static List<Object> getListPartesDocument(InputStream stream) throws Docx4JException, FileNotFoundException {
        WordprocessingMLPackage template = getTemplate(stream);
        return getAllElementFromObject(template.getMainDocumentPart(), Text.class);
    }

    public static StreamedContent converterArquivo(InputStream stream, Object entidade, Peca peca) throws Docx4JException, IllegalArgumentException, IllegalAccessException, FileNotFoundException, InvocationTargetException, NoSuchFieldException, ClassNotFoundException {
        WordprocessingMLPackage template = getTemplate(stream);
        List<Object> linhas = getAllElementFromObject(template.getMainDocumentPart(), Text.class);
        // Text tagParte = null;
        // 0 - sem parte; 1 - parte $; 2 - parte { ; 3 - parte texto ; 4 - parte }
        // int flag = 0;
        for (Object text : linhas) {
            Text textElement = (Text) text;
            String texto = textElement.getValue();
            Matcher matcher = RegexUtil.getMatcher(PARAMETROS_PADRAO, texto);
            System.out.println("linha " + texto + " ");
            if (matcher.find()) {
                //Troca aqui o valor parametro do texto pelo valor do objeto
                for (CampoPersonalizado campo : getListaCampos(entidade.getClass(), "")) {
                    String resposta;
                    if (texto.contains(campo.getTagName())) {
                        // Correção no caso de um bug
                        Object valor = executaMetodo(campo.getNome(), entidade);
                        resposta = valor.toString();
                        texto = texto.replaceAll(campo.getReplaceTagName(), Matcher.quoteReplacement(resposta));
                        textElement.setValue(texto);
//                        System.out.println(campo.getNome() + " foi substituido por " + valor);
                    }
                }
            } else {
                //Tentar encontar parte da tag
                /**
                 * switch (flag) { case 0: // Verifica se contem o $ // Parece
                 * haver um tag partida if (texto.contains("$") ||
                 * texto.contains("${")) { // Parece haver um tag partida
                 * tagParte = (Text) text; flag = 1; } break; // Montando a tag
                 * partida // abril a tag case 1: if (tagParte != null) { if
                 * (texto.contains("{")) { // Parece haver um tag partida flag =
                 * 2; tagParte.setValue(tagParte.getValue() +
                 * textElement.getValue()); textElement.setValue(""); } } case
                 * 2: // Incluir partes ate encontrar o fim da tag if (tagParte
                 * != null) { if (texto.contains("}")) { // Parece haver um tag
                 * partida flag = 0; tagParte.setValue(tagParte.getValue() +
                 * textElement.getValue()); texto = tagParte.getValue(); for
                 * (CampoPersonalizado campo :
                 * getListaCampos(entidade.getClass(), "")) { String resposta;
                 * if (texto.contains(campo.getTagName())) { Object valor =
                 * executaMetodo(campo.getNome(), entidade); resposta =
                 * valor.toString(); texto =
                 * texto.replaceAll(campo.getReplaceTagName(), resposta);
                 * tagParte.setValue(texto); System.out.println(campo.getNome()
                 * + "em tag parte foi substituido por " + valor); } }
                 * textElement.setValue(""); tagParte = null; } else {
                 * tagParte.setValue(tagParte.getValue() +
                 * textElement.getValue()); textElement.setValue(""); }
                 *
                 * }
                 *
                 * default: break; }*
                 */
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
     *
     * public static boolean validarArquivo(InputStream stream, Class entidade)
     * throws Docx4JException, NoSuchFieldException, ClassNotFoundException {
     * WordprocessingMLPackage wordMLPackage; boolean valido = false;
     * wordMLPackage = WordprocessingMLPackage.load(stream); MainDocumentPart
     * arquivoBase = wordMLPackage.getMainDocumentPart(); List<Object> linhas =
     * arquivoBase.getContent(); for (Object valor : linhas) { String texto =
     * valor.toString(); Matcher matcher =
     * RegexUtil.getMatcher(PARAMETROS_PADRAO, texto); if (matcher.find()) { //
     * Verifica se o parametro existe na classe for (CampoPersonalizado campo :
     * getListaCampos(entidade, "")) { if (texto.contains(campo.getTagName())) {
     * valido = true; } } } } return valido; }
     */
    /**
     *
     * @param classe
     * @param prefix
     * @return
     * @throws NoSuchFieldException
     * @throws ClassNotFoundException
     */
    public static List<CampoPersonalizado> getListaCampos(Class classe, String prefix) throws NoSuchFieldException, ClassNotFoundException {
        List<CampoPersonalizado> campos = new ArrayList<>();
        for (Field field : classe.getDeclaredFields()) {
            try {
                PecaColetor p = (PecaColetor) field.getAnnotation(PecaColetor.class);
                if (p != null) {
                    if (p.isEntidade()) {
                        campos.addAll(getListaCampos(field.getType(), prefix + field.getName() + "."));
                    } else if (p.isLista()) {
                        campos.addAll(getListaCampos(tipoLista(classe, field.getName()), prefix + field.getName() + "."));
                    } else {
                        campos.add(new CampoPersonalizado((prefix + field.getName()), p.mascara(), p.tipo()));
                    }
                }
            } catch (NullPointerException n) {
                // Se nãp possue não adiciona
            }
        }
        return campos;
    }

    private static Method getMetodo(String nomeCampo, Class classe) {
        for (Method m : classe.getDeclaredMethods()) {
            String nomeMetodo = m.getName().toLowerCase();
            if (nomeMetodo.equals("get" + nomeCampo.toLowerCase())) {
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
    private static Object executaMetodo(String campo, Object entidade) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
        if (campo.contains(".")) {
            List<Object> respostas = new ArrayList<>();
            String[] partes = campo.split("\\.");
            String c = partes[0];
            String m = partes[1];
            if (partes.length > 2) {
                m = campo.replace(c + ".", "");
            }
            Object valor = executaMetodo(c, entidade);
            if (valor instanceof List) {
                for (Object o : (List) valor) {
                    respostas.add(executaMetodo(m, o));
                }
                String resp = "";
                for (Object r : respostas) {
                    resp += r.toString() + ", ";
                }
                if (resp.length() > 2) {
                    return resp.substring(0, resp.length() - 2);
                } else {
                    return "";
                }
            } else {
                return executaMetodo(m, valor);
            }
        } else {
            Method metodo = getMetodo(campo, entidade.getClass());
            if (metodo != null) {
                Object valor = metodo.invoke(entidade);
                if (valor == null) {
                    return "";
                } else {
                    CampoPersonalizado campoPer = getCampoPorNome(campo, entidade.getClass());
                    if (campoPer.getMascara().equals("") && campoPer.getTipoMascara().equals(TipoMascara.GENERICO)) {
                        return valor;
                    } else {
                        return MascaraUtil.formatarValor(valor, campoPer.getMascara(), campoPer.getTipoMascara());
                    }
                }
            } else {
                return "";
            }
        }

    }

    private static CampoPersonalizado getCampoPorNome(String nome, Class classe) throws NoSuchFieldException {
        Field campo = classe.getDeclaredField(nome);
        PecaColetor p = (PecaColetor) campo.getAnnotation(PecaColetor.class);
        if (p != null) {
            return new CampoPersonalizado(campo.getName(), p.mascara(), p.tipo());
        } else {
            return null;
        }

    }
}
