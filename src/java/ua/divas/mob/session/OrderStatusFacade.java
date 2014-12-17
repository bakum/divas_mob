/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.divas.mob.session;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import ua.divas.mob.entity.OrderStatus;
import ua.divas.mob.util.DataQuery;
import ua.divas.mob.util.WLS_Utility;

/**
 *
 * @author bakum
 */
@Stateless
public class OrderStatusFacade extends AbstractFacade<OrderStatus> {

    @PersistenceContext(unitName = "divas_mobPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OrderStatusFacade() {
        super(OrderStatus.class);
    }

    private DataQuery getQuery() {
        return new DataQuery();
    }

    @Override
    public List<OrderStatus> findAll() {
        DataQuery q = getQuery();
        boolean admin = WLS_Utility.isMember("administrator", q.getSessionScopeAttr("username"), true);
        boolean dispatch = WLS_Utility.isMember("z_dispatcher", q.getSessionScopeAttr("username"), true);
        if (admin) {
            return getEntityManager().createNamedQuery("OrderStatus.findAll", OrderStatus.class)
                    .getResultList();
        } else if (dispatch) {
            return getEntityManager().createNamedQuery("OrderStatus.findByNameForDispatch", OrderStatus.class)
                    .setParameter("name1", "ЗАМЕР")
                    .setParameter("name2", "НЕОПЛАЧЕН")
                    .setParameter("name3", "ОТКАЗ")
                    .setParameter("name4", "КОНТРОЛЬ")
                    .setParameter("name5", "НОВЫЙ")
                    .getResultList();
        } else {
            return getEntityManager().createNamedQuery("OrderStatus.findByNameForZamer", OrderStatus.class)
                    .setParameter("name1", "ЗАМЕР")
                    .setParameter("name2", "НЕОПЛАЧЕН")
                    .setParameter("name3", "ОТКАЗ")
                    .setParameter("name4", "КОНТРОЛЬ")
                    .getResultList();
        }
    }

}
