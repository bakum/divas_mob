package ua.divas.mob.controller;

import ua.divas.mob.entity.OrdersTpOplaty;
import ua.divas.mob.util.JsfUtil;
import ua.divas.mob.util.JsfUtil.PersistAction;
import ua.divas.mob.session.OrdersTpOplatyFacade;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;
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
import ua.divas.mob.util.DataQuery;
import ua.divas.mob.entity.Orders;
import ua.divas.mob.entity.UserSettings;
import ua.divas.mob.entity.Users;
import ua.divas.mob.util.DivasEntry;

@ManagedBean(name = "ordersTpOplatyController")
@SessionScoped
public class OrdersTpOplatyController implements Serializable {

    @EJB
    private ua.divas.mob.session.OrdersTpOplatyFacade ejbFacade;
    private List<OrdersTpOplaty> items = null;
    private OrdersTpOplaty selected;
    private Orders master;

    public OrdersTpOplatyController() {
    }

    public Orders getMaster() {
        return master;
    }

    public void setMaster(Orders master) {
        this.master = master;
        this.prepareCreate();
    }

    public OrdersTpOplaty getSelected() {
        return selected;
    }

    public void setSelected(OrdersTpOplaty selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private OrdersTpOplatyFacade getFacade() {
        return ejbFacade;
    }

    private Users getCurrentUser() {
        DataQuery q = new DataQuery();
        System.out.println(q.getCurrentUser(q.getSessionScopeAttr("username")));
        return q.getCurrentUser(q.getSessionScopeAttr("username"));
    }
    
    protected Kontragents getCurrentZamer(){
        DataQuery q = new DataQuery();
        Users u = q.getCurrentUser(q.getSessionScopeAttr("username"));
        UserSettings us = q.getCurrentUserSettings(u);
        return q.getCurrenZamer(us.getZamerkontragId().getId());
    }

    private void initializeDefaultValue() {
        selected.setId(UUID.randomUUID().toString());
        selected.setDat(new Date());
        selected.setOrderId(master);
        //selected.setSum(BigDecimal.ZERO);
        selected.setUserId(getCurrentUser());
        selected.setZamerId(getCurrentZamer());
    }

    public String refresh() {
        if (items != null) {
            items = null;
        }
        return "pm:listOplat";
    }

    public OrdersTpOplaty prepareCreate() {
        selected = new OrdersTpOplaty();
        initializeEmbeddableKey();
        initializeDefaultValue();
        System.out.println(master.toString());
        return selected;
    }

    public String create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("OrdersTpOplatyCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
        return "pm:listOplat?transition=flip";
    }

    public String update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("OrdersTpOplatyUpdated"));
        return "pm:listOplat?transition=flip";
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("OrdersTpOplatyDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<OrdersTpOplaty> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void hardReset() {
        items = null;
        items = getFacade().findAll();
        Collection<OrdersTpOplaty> tp = null;
        getMaster().getOrdersTpOplatyCollection().clear();
        for (OrdersTpOplaty e : items) {
            if (e.getOrderId().getId().equals(getMaster().getId())) {
                tp.add(e);
            }
        }
        getMaster().setOrdersTpOplatyCollection(tp);
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    if (persistAction != PersistAction.CREATE) {
                        getFacade().edit(selected);
                    } else {
                        getFacade().edit(selected);
                        getMaster().getOrdersTpOplatyCollection().add(selected);
                    }
                } else {
                    getFacade().remove(selected);
                }
                DivasEntry.entryOrders(selected.getOrderId());
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

    public List<OrdersTpOplaty> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<OrdersTpOplaty> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = OrdersTpOplaty.class)
    public static class OrdersTpOplatyControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            OrdersTpOplatyController controller = (OrdersTpOplatyController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "ordersTpOplatyController");
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
            if (object instanceof OrdersTpOplaty) {
                OrdersTpOplaty o = (OrdersTpOplaty) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), OrdersTpOplaty.class.getName()});
                return null;
            }
        }

    }

}
