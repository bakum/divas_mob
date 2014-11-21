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
import ua.divas.mob.entity.MeasureUnit;
import ua.divas.mob.session.MeasureUnitFacade;

/**
 *
 * @author bakum
 */
@ManagedBean(name = "measure")
@SessionScoped
public class MeasureController implements Serializable{
    
    @EJB
    private ua.divas.mob.session.MeasureUnitFacade ejbFacade;
    private List<MeasureUnit> items = null;
    private MeasureUnit selected;
    
    public MeasureController() {
    }
    
    public MeasureUnit getSelected() {
        return selected;
    }

    public void setSelected(MeasureUnit selected) {
        this.selected = selected;
    }
    
    private MeasureUnitFacade getFacade() {
        return ejbFacade;
    }
    
    public List<MeasureUnit> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }
    
    public List<MeasureUnit> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }
    
        @FacesConverter(forClass = MeasureUnit.class)
    public static class MeasureControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MeasureController controller = (MeasureController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "measure");
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
            if (object instanceof MeasureUnit) {
                MeasureUnit o = (MeasureUnit) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), MeasureUnit.class.getName()});
                return null;
            }
        }

    }
    
}
