/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.divas.mob.session;

import java.util.Collection;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import ua.divas.mob.util.DataQuery;
import ua.divas.mob.entity.Kontragents;
import ua.divas.mob.entity.OrderStatus;
import ua.divas.mob.entity.Orders;
import ua.divas.mob.entity.OrdersTpOplaty;
import ua.divas.mob.entity.OrdersTpUslugi;
import ua.divas.mob.entity.Users;
import ua.divas.mob.util.WLS_Utility;

/**
 *
 * @author bakum
 */
@Stateless
public class OrdersFacade extends AbstractFacade<Orders> {

    @PersistenceContext(unitName = "divas_mobPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OrdersFacade() {
        super(Orders.class);
    }

    @Override
    public void remove(Orders entity) {
        short del = 1;
        entity.setDeleted(del);
        super.edit(entity);
        //super.remove(entity); //To change body of generated methods, choose Tools | Templates.
    }

    private DataQuery getQuery() {
        return new DataQuery();
    }

    private OrderStatus getStatus() {
        DataQuery q = getQuery();
        return q.getZamerOrderStatus();
    }
    
    private OrderStatus getNewStatus() {
        DataQuery q = getQuery();
        return q.getNewOrderStatus();
    }
    
    private OrderStatus getNotPayStatus() {
        DataQuery q = getQuery();
        return q.getZamerNotPayOrderStatus();
    }

    private Kontragents getZamer() {
        String un = "username";
        DataQuery q = getQuery();
        q.getSessionScopeAttr(un);
        Users u = q.getCurrentUser(q.getSessionScopeAttr(un));
        return q.getCurrenZamer(u.getLogin());
    }
    
    public Orders refreshOrder(Orders o) {
        Orders mergedEntity = getEntityManager().merge(o);
        getEntityManager().refresh(mergedEntity);
        return mergedEntity;
    }
    
    @Override
    public List<Orders> findAll() {
        DataQuery q = getQuery();
        boolean admin = WLS_Utility.isMember("administrator", q.getSessionScopeAttr("username"), true);
        boolean dispatch = WLS_Utility.isMember("z_dispatcher", q.getSessionScopeAttr("username"), true);
        if (admin) {
            return getEntityManager().createNamedQuery("Orders.findAllForAdmin", Orders.class)
                    .getResultList();

        } else if (dispatch) {
            return getEntityManager().createNamedQuery("Orders.findAllForDispatch", Orders.class)
                    .setParameter("name1", this.getStatus())
                    .setParameter("name2", this.getNewStatus())
                    .setParameter("name3", this.getNotPayStatus())
                    .getResultList();

        } else {
            return getEntityManager().createNamedQuery("Orders.findAll", Orders.class)
                    .setParameter("statusid", this.getStatus())
                    .setParameter("zamerid", this.getZamer())
                    .getResultList();
        }
    }

}
