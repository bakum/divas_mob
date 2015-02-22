/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.divas.mob.util;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import ua.divas.mob.entity.Orders;

/**
 *
 * @author bakum
 */
public class DivasEntry implements Serializable {

//    //resource injection
//    @Resource(name = "jdbc/divas_mobiDS")
//    private static DataSource ds;
//    public static void entryOrdersJndi(Orders order) throws SQLException, NamingException {
//        Context ctx = new InitialContext();
//        DataSource ds = (DataSource) ctx.lookup("jdbc/divas_mobiDS");
//        if (ds == null) {
//            throw new SQLException("Can't get data source");
//        }
//        //get database connection
//        Connection con = ds.getConnection();
//        if (con == null) {
//            throw new SQLException("Can't get database connection");
//        }
//        String Id = order.getId();
//        PreparedStatement ps
//                = con.prepareStatement("begin ORDERS_ENTRY.ORDERS_MOVE_PLAN_ACC('" + Id + "'); end;");
//        ps.executeQuery();
//
//    }
//    public static void entryOrders(Orders order) {
//        EntityManagerFactory emf;
//        EntityManager em;
//        emf = Persistence.createEntityManagerFactory("divas_mobPU");
//        em = emf.createEntityManager();
//        em.getTransaction().begin();
//        
//        try {
//            Query query = em.createQuery(
//                    "select ORDERS_ENTRY.ORDERS_MOVE_PLAN_ACC(':Id') from dual");
//            query.setParameter("Id", order.getId()).executeUpdate();
//            em.getTransaction().commit();
//        } catch (Exception e) {
//            em.getTransaction().rollback();
//        }
//
//    }
//    private static final String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
//    private static final String DB_CONNECTION = "jdbc:oracle:thin:@192.168.0.200:1521:db11g";
//    private static final String DB_USER = "dba_divas";
//    private static final String DB_PASSWORD = "divas";

//    private static Connection getDBConnection() {
//        Connection dbConnection = null;
//        try {
//            Class.forName(DB_DRIVER);
//        } catch (ClassNotFoundException e) {
//            System.out.println(e.getMessage());
//        }
//        try {
//            dbConnection = DriverManager.getConnection(
//                    DB_CONNECTION, DB_USER, DB_PASSWORD);
//            return dbConnection;
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//        return dbConnection;
//    }

    private static Connection getDBConnectionJndi() throws NamingException, SQLException {
        Connection dbConnection = null;
        Context ctx = null;
        try {
            ctx = new InitialContext();
        } catch (NamingException ex) {
            Logger.getLogger(DivasEntry.class.getName()).log(Level.SEVERE, null, ex);
        }
        DataSource ds = (DataSource) ctx.lookup("jdbc/divas_mobiDS");
        dbConnection = ds.getConnection();
        return dbConnection;
    }

    public static void entryOrders(Orders order) throws SQLException, NamingException {
        Connection dbConnection = null;
        CallableStatement callableStatement = null;
        String entryStoreProc = "begin ORDERS_ENTRY.ORDERS_MOVE_PLAN_ACC(?); end;";
        try {
            dbConnection = getDBConnectionJndi();
            callableStatement = dbConnection.prepareCall(entryStoreProc);
            //System.out.println(order.getId());
            callableStatement.setString(1, order.getId());
            callableStatement.executeUpdate();
            System.out.println("Record is entry!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (callableStatement != null) {
                callableStatement.close();
            }
            if (dbConnection != null) {
                dbConnection.close();
            }
        }
    }

}
