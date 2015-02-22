/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.divas.mob.util;

import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import ua.divas.mob.entity.Orders;

/**
 *
 * @author bakum
 */
public class DivasEntry implements Serializable {

//    //resource injection
//    @Resource(name = "jdbc/divas_mobiDS")
//    private static DataSource ds;

//    public static void entryOrders(Orders order) throws SQLException {
//        if (ds == null) {
//            throw new SQLException("Can't get data source");
//        }
//
//        //get database connection
//        Connection con = ds.getConnection();
//
//        if (con == null) {
//            throw new SQLException("Can't get database connection");
//        }
//        
//        String Id = order.getId();
//
//        PreparedStatement ps
//                = con.prepareStatement("begin ORDERS_ENTRY.ORDERS_MOVE_PLAN_ACC('"+Id+"') end;");
//        ps.executeQuery();
//
//    }
    public static void entryOrders(Orders order) {
        EntityManagerFactory emf;
        EntityManager em;
        emf = Persistence.createEntityManagerFactory("divas_mobPU");
        em = emf.createEntityManager();
        em.getTransaction().begin();
        
        try {
            Query query = em.createQuery(
                    "select ORDERS_ENTRY.ORDERS_MOVE_PLAN_ACC(':Id') from dual");
            query.setParameter("Id", order.getId()).executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }

    }

}
