/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.util.converter;

import br.com.atus.cadastro.controller.OrgaoEmissorController;
import br.com.atus.cadastro.modelo.OrgaoEmissor;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;
import javax.persistence.PersistenceException;

/**
 *
 * @author ari
 */
@Named
public class OrgaoEmissorConverter implements Converter, Serializable {

    @EJB
    private OrgaoEmissorController controller;

    /**
     *
     * @param context
     * @param component
     * @param value
     * @return
     */
    @Override
    public OrgaoEmissor getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && !"".equals(value)) {
            try {
                Integer i = Integer.decode(value);
                OrgaoEmissor emissor = controller.carregar(i);
                return emissor;
            } catch (SQLException ex) {
                Logger.getLogger(OrgaoEmissorConverter.class.getName()).log(Level.SEVERE, null, ex);
            } catch (PersistenceException ex) {
                Logger.getLogger(OrgaoEmissorConverter.class.getName()).log(Level.SEVERE, null, ex);
            } catch (EJBException ex) {
                Logger.getLogger(OrgaoEmissorConverter.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(OrgaoEmissorConverter.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        } else {
            return null;
        }
    }

    /**
     *
     * @param context
     * @param component
     * @param value
     * @return
     */
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            if (((OrgaoEmissor) value).getId() != null) {
                return ((OrgaoEmissor) value).getId().toString();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

}
