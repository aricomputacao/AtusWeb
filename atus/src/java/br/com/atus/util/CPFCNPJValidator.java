/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.util;


import br.com.atus.util.peca.DocumetoUtil;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;

/**
 * Classe do Projeto guardiao criada em 26/03/2013
 *
 * @author: ari
 */
@Named
public class CPFCNPJValidator implements Validator {

    private String doc;

    /**
     *
     * @param fc
     * @param uic
     * @param value
     * @throws ValidatorException
     */
    @Override
    public void validate(FacesContext fc, UIComponent uic, Object value) throws ValidatorException {
        doc = DocumetoUtil.removeCaracteresFromString(value.toString(), ".;/;-", ";");

        if ((DocumetoUtil.CPF(doc) == false) && (DocumetoUtil.validaCNPJ(doc) == false)) {
            FacesMessage msg = new FacesMessage();
            msg.setSeverity(FacesMessage.SEVERITY_FATAL);
            msg.setSummary("Documento invalido");
            msg.setDetail("Documento invalido");

            throw new ValidatorException(msg);
        }
    }
}
