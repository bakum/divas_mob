/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.divas.mob.session;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import ua.divas.mob.entity.WlsSettings;

/**
 *
 * @author bakum
 */
@Stateless
public class WlsSettingsFacade extends AbstractFacade<WlsSettings> {
    @PersistenceContext(unitName = "divas_mobPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public WlsSettingsFacade() {
        super(WlsSettings.class);
    }
    
}
