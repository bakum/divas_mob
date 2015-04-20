package ua.divas.mob.controller;

import ua.divas.mob.entity.OrdersTpUslugi;
import ua.divas.mob.util.JsfUtil;
import ua.divas.mob.util.JsfUtil.PersistAction;
import ua.divas.mob.session.OrdersTpUslugiFacade;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.faces.event.AjaxBehaviorEvent;
import org.primefaces.context.RequestContext;
import ua.divas.mob.entity.Kontragents;
import ua.divas.mob.entity.LastPrices;
import ua.divas.mob.entity.Nomenklatura;
import ua.divas.mob.entity.Orders;
import ua.divas.mob.entity.UserSettings;
import ua.divas.mob.entity.Users;
import ua.divas.mob.util.DataQuery;
import ua.divas.mob.util.DivasEntry;

@ManagedBean(name = "ordersTpUslugiController")
@SessionScoped
public class OrdersTpUslugiController implements Serializable {

    @EJB
    private ua.divas.mob.session.OrdersTpUslugiFacade ejbFacade;
    private List<OrdersTpUslugi> items = null;
    private OrdersTpUslugi selected;
    private Orders master;
    private boolean editable;

    public OrdersTpUslugiController() {
    }

    public boolean getEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public void nomenklaturaChanged(AjaxBehaviorEvent e) {
        //System.out.println("listener");
        DataQuery q = new DataQuery();
        LastPrices lp = q.getLastPrices(getSelected().getNomId());
        if (lp != null) {
            getSelected().setMeasureId(lp.getEdIzm());
            getSelected().setPrice(lp.getPriceGoods().add(lp.getPriceUsl()).multiply(new BigDecimal(1.2)));
        } else {
            getSelected().setMeasureId(null);
            getSelected().setPrice(null);
        }

        getSelected().setQuantity(null);
        getSelected().setSumm(null);
    }

    public void quantityChanged(AjaxBehaviorEvent e) {
        BigDecimal pr = getSelected().getPrice();
        BigDecimal qt = getSelected().getQuantity();
        if (qt != null && pr != null) {
            getSelected().setSumm(pr.multiply(qt));
        } else {
            getSelected().setSumm(null);
        }
    }
    
    public void reset() {
        RequestContext.getCurrentInstance().reset("editOplata:editOplForm");
    }
    
    public void addChanged(AjaxBehaviorEvent e) {
        BigDecimal sm = getSelected().getSumm();
        BigDecimal ad = getSelected().getPriceAdd();
        if (sm != null && ad != null) {
            getSelected().setSumm(sm.add(ad));
        } else {
            getSelected().setSumm(null);
        }
    }

    public Orders getMaster() {
        return master;
    }

    public void setMaster(Orders master) {
        this.master = master;
        this.prepareCreate();
    }

    public OrdersTpUslugi getSelected() {
        return selected;
    }

    public void setSelected(OrdersTpUslugi selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }
    
    protected Kontragents getCurrentZamer(){
        DataQuery q = new DataQuery();
        Users u = q.getCurrentUser(q.getSessionScopeAttr("username"));
        UserSettings us = q.getCurrentUserSettings(u);
        return q.getCurrenZamer(us.getZamerkontragId().getId());
    }

    protected void initializeEmbeddableKey() {
        selected.setId(UUID.randomUUID().toString());
        selected.setOrderId(master);
        selected.setDatToPay(new Date());
        selected.setDatComplete(new Date());
        selected.setSotrId(getCurrentZamer());
        
    }

    private OrdersTpUslugiFacade getFacade() {
        return ejbFacade;
    }

    public OrdersTpUslugi prepareCreate() {
        selected = new OrdersTpUslugi();
        initializeEmbeddableKey();
        return selected;
    }

    public String create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("OrdersTpUslugiCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
            return "pm:uslugiList?transition=flip";
        }
        selected = null;
        return null;
    }

    public String update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("OrdersTpUslugiUpdated"));
        if (!JsfUtil.isValidationFailed()) {
            return "pm:uslugiList?transition=flip";
        }
        return null;
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("OrdersTpUslugiDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<OrdersTpUslugi> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    if (persistAction != PersistAction.CREATE) {
                        getFacade().edit(selected);
                    } else {
                        getFacade().create(selected);
                        getMaster().getOrdersTpUslugiCollection().add(selected);
                    }
                } else {
                    getFacade().remove(selected);
                }
                DivasEntry.entryOrders(selected.getOrderId());
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                selected = null;
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
                selected = null;
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public List<OrdersTpUslugi> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<OrdersTpUslugi> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    public List<Nomenklatura> getItemsNomAvailableSelectOne() {
        DataQuery q = new DataQuery();
        System.out.println("Master field " + getSelected().getGroupId());
        return q.getParentForCascade(getSelected().getGroupId());
    }

    @FacesConverter(forClass = OrdersTpUslugi.class)
    public static class OrdersTpUslugiControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            OrdersTpUslugiController controller = (OrdersTpUslugiController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "ordersTpUslugiController");
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
            if (object instanceof OrdersTpUslugi) {
                OrdersTpUslugi o = (OrdersTpUslugi) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), OrdersTpUslugi.class.getName()});
                return null;
            }
        }

    }

}
