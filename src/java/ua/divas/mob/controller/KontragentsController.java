/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.divas.mob.controller;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import ua.divas.mob.entity.Kontragents;
import ua.divas.mob.session.KontragentsFacade;
import ua.divas.mob.util.JsfUtil;

/**
 *
 * @author bakum
 */
@ManagedBean(name = "kontragents")
@SessionScoped
public class KontragentsController implements Serializable{
    
    @EJB
    private ua.divas.mob.session.KontragentsFacade ejbFacade;
    private List<Kontragents> items = null;
    private Kontragents selected;
    
    public KontragentsController() {
    }
    
    protected void setEmbeddableKeys() {
    }
    
    public Kontragents getSelected() {
        return selected;
    }

    public void setSelected(Kontragents selected) {
        this.selected = selected;
    }
    
    private KontragentsFacade getFacade() {
        return ejbFacade;
    }
    
    public List<Kontragents> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }
    
    private void persist(JsfUtil.PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != JsfUtil.PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }
    
    public String update() {
        persist(JsfUtil.PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("KontragentUpdated"));
        if (!JsfUtil.isValidationFailed()) {
            return "pm:viewTask?transition=flip";
        }
        return null;
    }
    
    public List<Kontragents> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }
    
    public List<Kontragents> getZamerItemsAvaibleSelectOne(){
        return getFacade().findZamer();
    }
    
        @FacesConverter(forClass = Kontragents.class)
    public static class KontragentsControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            KontragentsController controller = (KontragentsController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "kontragents");
            return controller.getFacade().find(getKey(value));
        }

        java.lang.String getKey(String value) {
            java.lang.String key;
            key = value;
            return key;
        }

        String getStringKey(java.lang.String value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Kontragents) {
                Kontragents o = (Kontragents) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Kontragents.class.getName()});
                return null;
            }
        }

    }
    
}
