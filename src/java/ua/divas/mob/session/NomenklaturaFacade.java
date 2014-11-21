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
import ua.divas.mob.entity.Nomenklatura;
import ua.divas.mob.util.DataQuery;

/**
 *
 * @author bakum
 */
@Stateless
public class NomenklaturaFacade extends AbstractFacade<Nomenklatura> {
    @PersistenceContext(unitName = "divas_mobPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NomenklaturaFacade() {
        super(Nomenklatura.class);
    }
    
    private DataQuery getQuery() {
        return new DataQuery();
    }
    
    private Nomenklatura getParent(){
        DataQuery q = getQuery();
        return q.getParentForZamer();
    }
    
    public List<Nomenklatura> findAllgrp() {
        return getEntityManager().createNamedQuery("Nomenklatura.findByParent", Nomenklatura.class)
                    .setParameter("parentid", this.getParent())
                    .getResultList();
    }
    
    public List<Nomenklatura> findAllnomByGr(Nomenklatura parent) {
        if (parent != null)
        System.out.println(parent.getFullname());
        return getEntityManager().createNamedQuery("Nomenklatura.findByParentGr", Nomenklatura.class)
                    .setParameter("parentid", parent)
                    .getResultList();
    }
    
}
