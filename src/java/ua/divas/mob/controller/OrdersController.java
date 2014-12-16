package ua.divas.mob.controller;

import ua.divas.mob.entity.Orders;
import ua.divas.mob.util.JsfUtil;
import ua.divas.mob.util.JsfUtil.PersistAction;
import ua.divas.mob.session.OrdersFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.primefaces.context.RequestContext;
import ua.divas.mob.entity.OrdersTpOplaty;
import ua.divas.mob.entity.OrdersTpUslugi;

@ManagedBean(name = "orders")
@SessionScoped
public class OrdersController implements Serializable {

    @EJB
    private ua.divas.mob.session.OrdersFacade ejbFacade;
    private List<Orders> items = null;
    private Orders selected;
    private boolean zamerChanged;

    public OrdersController() {
    }
    
    public String refresh() {
        if (items != null) {
            items = null;            
        }
        selected = null;
        //return "pm:list";
        return null;
    }

    public boolean haveOplaty() {
        Orders sl = this.getSelected();
        int c = sl.getOrdersTpOplatyCollection().size();
        return c != 0;
    }
    
    public String colorStatusOpl(){
        if (this.haveOplaty()) {
            return "color: blue;";
        }
        return "color: red";
    }
    
    private void hardReset() {
        getFacade().refreshOrder(selected);
        items = null;
        items = getFacade().findAll();
        for (Orders element : items) {
            if (element.getId().equals(selected.getId())) {
                
                setSelected(element);
                break;
            }
        }
        //selected.setOrdersTpOplatyCollection(getFacade().refreshTpOplatyCollection(selected.getOrdersTpOplatyCollection()));
        selected.getOrdersTpOplatyCollection();
        selected.getOrdersTpUslugiCollection();
    }
    
    public String resetOplaty() {
        hardReset();
        RequestContext.getCurrentInstance().reset(":editOplata:editOplForm");
        return "pm:listOplat?transition=flip";
    }
    
    public String resetUslugi() {
        hardReset();
        RequestContext.getCurrentInstance().reset(":newjob:newjobform");
        return "pm:uslugiList?transition=flip";
    }

    public String oplatyStatus() {
        //String r;
        double sum = 0.0;
        Orders sl = this.getSelected();
        int c = sl.getOrdersTpOplatyCollection().size();
        if (c != 0) {
            for (OrdersTpOplaty next : sl.getOrdersTpOplatyCollection()) {                
                sum = sum + next.getSum().floatValue();
            }
            //r = String.valueOf(sum);
            return "Оплачено "+ String.format("%.2f",sum) +" грн";

        } else {
            return "Заказ не оплачен!";
        }
    }
    
    public boolean haveZamer() {
        Orders sl = this.getSelected();
        int c = sl.getOrdersTpUslugiCollection().size();
        return c != 0;
    }
    
    public String zamerStatus() {
        //String r;
        double sum = 0.0;
        Orders sl = this.getSelected();
        int c = sl.getOrdersTpUslugiCollection().size();
        if (c != 0) {
            for (OrdersTpUslugi next : sl.getOrdersTpUslugiCollection()) {                
                sum = sum + next.getSumm().floatValue();
            }
           // r = String.valueOf(sum);
            return "Работы: "+ String.format("%.2f",sum) +" грн";

        } else {
            return "Работы не определены";
        }
    }

    public boolean isZamerChanged() {
        Orders sl = this.getSelected();
        zamerChanged = !sl.getStatusId().getFullname().equalsIgnoreCase("Замер");
        return zamerChanged;
    }

    public void onValueZamerChange() {
        this.setZamerChanged(true);
    }

    public void setZamerChanged(boolean zamerChanged) {
        this.zamerChanged = zamerChanged;
    }

    public Orders getSelected() {
        return selected;
    }

    public void setSelected(Orders selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private OrdersFacade getFacade() {
        return ejbFacade;
    }

    public Orders prepareCreate() {
        selected = new Orders();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("OrdersCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("OrdersUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("OrdersDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Orders> getItems() {
        if (items == null) {
            items = getFacade().findAll();
//            for (Orders item : items) {
//                item.getOrdersTpOplatyCollection().addAll(null);
//            }
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
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

    public List<Orders> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Orders> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Orders.class)
    public static class OrdersControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            OrdersController controller = (OrdersController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "ordersController");
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
            if (object instanceof Orders) {
                Orders o = (Orders) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Orders.class.getName()});
                return null;
            }
        }

    }

}
