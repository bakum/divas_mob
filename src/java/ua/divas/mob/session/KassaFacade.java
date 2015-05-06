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
import ua.divas.mob.entity.Kassa;
import ua.divas.mob.entity.Nomenklatura;
import ua.divas.mob.entity.Users;
import ua.divas.mob.util.DataQuery;
import ua.divas.mob.util.WLS_Utility;

/**
 *
 * @author bakum
 */
@Stateless
public class KassaFacade extends AbstractFacade<Kassa> {

    @PersistenceContext(unitName = "divas_mobPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public KassaFacade() {
        super(Kassa.class);
    }

    private DataQuery getQuery() {
        return new DataQuery();
    }

    private Users getCurrentUser() {
        String un = "username";
        DataQuery q = getQuery();
        q.getSessionScopeAttr(un);
        return q.getCurrentUser(q.getSessionScopeAttr(un));
    }

    public List<Kassa> findAllbySettings() {
        DataQuery q = getQuery();
        boolean admin = WLS_Utility.isMember("administrator", q.getSessionScopeAttr("username"), true);
        if (!admin) {
            return getEntityManager().createNamedQuery("Kassa.findBySettings", Kassa.class)
                    .setParameter("user_id", this.getCurrentUser())
                    .getResultList();
        } else {
            return getEntityManager().createNamedQuery("Kassa.findAll", Kassa.class)
                    .getResultList();
        }
    }

}
