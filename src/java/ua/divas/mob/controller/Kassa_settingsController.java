/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.divas.mob.controller;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import ua.divas.mob.entity.Kassa;
import ua.divas.mob.entity.Kontragents;
import ua.divas.mob.session.KassaFacade;

/**
 *
 * @author bakum
 */
@ManagedBean(name = "kassa_settings")
@SessionScoped
public class Kassa_settingsController implements Serializable{
    @EJB
    private ua.divas.mob.session.KassaFacade ejbFacade;
    
    public List<Kassa> getItemsAvailableSelectOne() {
        return ejbFacade.findAllbySettings();
    }
    
    private KassaFacade getFacade() {
        return ejbFacade;
    }
    
    @FacesConverter(forClass = Kassa.class)
    public static class KassaControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            Kassa_settingsController controller = (Kassa_settingsController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "kassa_settings");
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
            if (object instanceof Kassa) {
                Kassa o = (Kassa) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Kassa.class.getName()});
                return null;
            }
        }

    }
    
}
