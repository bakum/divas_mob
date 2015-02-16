/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.divas.mob.util;

import java.util.List;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import ua.divas.mob.entity.Kontragents;
import ua.divas.mob.entity.LastPrices;
import ua.divas.mob.entity.Nomenklatura;
import ua.divas.mob.entity.OrderStatus;
import ua.divas.mob.entity.UserSettings;
import ua.divas.mob.entity.Users;
import ua.divas.mob.entity.WlsSettings;

/**
 *
 * @author bakum
 */
public class DataQuery {

    EntityManagerFactory emf;
    EntityManager em;

    public DataQuery() {
        emf = Persistence.createEntityManagerFactory("divas_mobPU");
        em = emf.createEntityManager();
        em.getTransaction().begin();
    }
    
    public Users getCurrentUser (String user) {
        try {
            Users u = em.createNamedQuery("Users.findByLogin", Users.class)
                    .setParameter("login", user)
                    .getSingleResult();
            if (u != null) {
                return u;
            }
            return null;
        } catch (Exception e) {
            return null;
        }       
    }
    
    public LastPrices getLastPrices (Nomenklatura n) {
        try {
            LastPrices u = em.createNamedQuery("LastPrices.findByNom", LastPrices.class)
                    .setParameter("nomid", n)
                    .getSingleResult();
            if (u != null) {
                return u;
            }
            return null;
        } catch (Exception e) {
            return null;
        }      
    }
    
    public String getSessionScopeAttr(String key){
        FacesContext ctx = FacesContext.getCurrentInstance();       
        return (String) ctx.getExternalContext().getSessionMap().get(key);
    }
    
    public Kontragents getCurrenZamer(String key){
        try {
            Kontragents u = em.createNamedQuery("Kontragents.findByFullname", Kontragents.class)
                    .setParameter("fullname", key)
                    .getSingleResult();
            if (u != null) {
                return u;
            }
            return null;
        } catch (Exception e) {
            return null;
        }    
        
    }
    
     public Nomenklatura getParentForZamer(){
        try {
            Nomenklatura u = em.createNamedQuery("Nomenklatura.findByFullname", Nomenklatura.class)
                    .setParameter("fullname", "Услуги")
                    .getSingleResult();
            if (u != null) {
                return u;
            }
            return null;
        } catch (Exception e) {
            return null;
        }    
        
    }
     
    public List<Nomenklatura> getParentForCascade(Nomenklatura parent){
        try {
            List<Nomenklatura> u = em.createNamedQuery("Nomenklatura.findByParentGr", Nomenklatura.class)
                    .setParameter("parentid", parent)
                    .getResultList();
            if (u != null) {
                return u;
            }
            return null;
        } catch (Exception e) {
            return null;
        }    
        
    } 
    
    public WlsSettings getCurrentWlsSettings () {
        try {
            WlsSettings u = em.createNamedQuery("WlsSettings.findAll", WlsSettings.class)
                    .getSingleResult();
            if (u != null) {
                return u;
            }
            return null;
        } catch (Exception e) {
            return null;
        } 
    }
    
    public UserSettings getCurrentUserSettings (Users userid) {
        try {
            UserSettings u = em.createNamedQuery("UserSettings.findByUser", UserSettings.class)
                    .setParameter("userid", userid)
                    .getSingleResult();
            if (u != null) {
                return u;
            }
            return null;
        } catch (Exception e) {
            return null;
        }       
    }
    
    public OrderStatus getNewOrderStatus() {
        try {
            OrderStatus u = em.createNamedQuery("OrderStatus.findByName", OrderStatus.class)
                    .setParameter("name", "Новый")
                    .getSingleResult();
            if (u != null) {
                return u;
            }
            return null;
        } catch (Exception e) {
            return null;
        }  
    }
    
    public OrderStatus getZamerOrderStatus() {
        try {
            OrderStatus u = em.createNamedQuery("OrderStatus.findByName", OrderStatus.class)
                    .setParameter("name", "Замер")
                    .getSingleResult();
            if (u != null) {
                return u;
            }
            return null;
        } catch (Exception e) {
            return null;
        }  
    }
    
    public OrderStatus getZamerNotPayOrderStatus() {
        try {
            OrderStatus u = em.createNamedQuery("OrderStatus.findByName", OrderStatus.class)
                    .setParameter("name", "НеОплачен")
                    .getSingleResult();
            if (u != null) {
                return u;
            }
            return null;
        } catch (Exception e) {
            return null;
        }  
    }

}
