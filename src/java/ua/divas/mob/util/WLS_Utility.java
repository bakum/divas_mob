/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.divas.mob.util;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

/**
 *
 * @author bakum
 */
public class WLS_Utility implements Serializable {

    /**
     * **** DEFINING SOME STATIC VARIABLES *******
     */
    public static final String JNDI_FACTORY = "weblogic.jndi.WLInitialContextFactory";
    public static final String MBEAN_SERVER = "weblogic.management.mbeanservers.domainruntime";
    public static final String JNDI_ROOT = "/jndi/";
    public static final String DEFAULT_PROTOCOL = "t3";
    public static final String PROTOCOL_PROVIDER_PACKAGES = "weblogic.management.remote";
//This how we get our DomainRuntimeService, this is where DomainConfigurationMBeans exists
    public static final String DOMAIN_MBEAN_NAME = "com.bea:Name=DomainRuntimeService,Type=weblogic.management.mbeanservers.domainruntime.DomainRuntimeServiceMBean";
    private static MBeanServerConnection connection;
    private static ObjectName defaultAuthenticator;
    private static ObjectName[] authenticationProviders;
    private static String authenticatorName = "db_users";

    static {
        try {
            String host = "127.0.0.1";
            String port = "7101";
            String username = "weblogic";
            String password = "!stigmata70";
            Hashtable h = new Hashtable();
            JMXServiceURL serviceURL;

            serviceURL
                    = new JMXServiceURL(DEFAULT_PROTOCOL, host, Integer.valueOf(port).intValue(),
                            "/jndi/weblogic.management.mbeanservers.domainruntime");

            h.put("java.naming.security.principal", username);
            h.put("java.naming.security.credentials", password);
            h.put("jmx.remote.protocol.provider.pkgs",
                    "weblogic.management.remote");

            //Creating a JMXConnector to connect to JMX
            JMXConnector connector
                    = JMXConnectorFactory.connect(serviceURL, h);

            connection = connector.getMBeanServerConnection();

            /**
             * **
             * We Get Objects by creating ObjectName with it's Qualified name.
             * The constructor take a String of the full Qualified name of the
             * MBean We then use connection to get Attribute out of this
             * ObjectName but specifying a String of this Attribute ***
             */
            ObjectName configurationMBeans
                    = new ObjectName(DOMAIN_MBEAN_NAME);
            ObjectName domain
                    = (ObjectName) connection.getAttribute(configurationMBeans, "DomainConfiguration");

            ObjectName security
                    = (ObjectName) connection.getAttribute(domain, "SecurityConfiguration");

            ObjectName realm
                    = (ObjectName) connection.getAttribute(security, "DefaultRealm");

            authenticationProviders
                    = (ObjectName[]) connection.getAttribute(realm,
                            "AuthenticationProviders");

            for (int i = 0; i < authenticationProviders.length; i++) {
                String name
                        = (String) connection.getAttribute(authenticationProviders[i],
                                "Name");

                if (name.equals(authenticatorName)) {
                    defaultAuthenticator = authenticationProviders[i];
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean addUser(String username, String psw, String desc) {
        try {
            /**
             * As of connection.getAttribute you can use connection.invoke to
             * invoke an action It Takes ObjectName, String OperationName,
             * Object[] Parameters, and String[] Parameters Definition
             *
             */
            connection.invoke(defaultAuthenticator, "createUser",
                    new Object[]{username, psw, desc},
                    new String[]{"java.lang.String",
                        "java.lang.String",
                        "java.lang.String"});

            return true;
        } catch (InstanceNotFoundException | MBeanException | ReflectionException | IOException e) {
            return false;
            //throw new RuntimeException(e);
        }
    }

    public static boolean removeUser(String username) {
        try {
            if (!username.equalsIgnoreCase("weblogic")) {
                connection.invoke(defaultAuthenticator, "removeUser",
                        new Object[]{username},
                        new String[]{"java.lang.String"});
            }

            return true;
        } catch (InstanceNotFoundException | MBeanException | ReflectionException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean resetUserPassword(String username,
            String newPassword) {
        try {
            if (!username.equalsIgnoreCase("weblogic")) {
                connection.invoke(defaultAuthenticator, "resetUserPassword",
                        new Object[]{username, newPassword},
                        new String[]{"java.lang.String",
                            "java.lang.String"});
            }

            return true;
        } catch (InstanceNotFoundException | MBeanException | ReflectionException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean changeUserPassword(String username, String oldPassword,
            String newPassword) throws Exception {
        try {
            if (!username.equalsIgnoreCase("weblogic")) {
                connection.invoke(defaultAuthenticator, "changeUserPassword",
                        new Object[]{username, oldPassword, newPassword},
                        new String[]{"java.lang.String",
                            "java.lang.String",
                            "java.lang.String"});
            }
            return true;
        } catch (InstanceNotFoundException | MBeanException | ReflectionException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    
     public static boolean isMember(String parentGroupName, String memberUserOrGroupName, boolean recursive) throws RuntimeException {
        try {
            boolean member
                    = ((Boolean) connection.invoke(defaultAuthenticator, "isMember",
                            new Object[]{parentGroupName,memberUserOrGroupName,recursive},
                            new String[]{"java.lang.String","java.lang.String", "java.lang.Boolean"})).booleanValue();

            return member;
        } catch (InstanceNotFoundException | MBeanException | ReflectionException | IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * As of connection.getAttribute you can use connection.invoke to invoke an
     * action It Takes ObjectName, String OperationName, Object[] Parameters,
     * and String[] Parameters Definition, It returns an Object we cast it to
     * Boolean, you can know all about function from MBeans Reference
     *
     */
    public static boolean isUserExists(String currentUser) throws RuntimeException {
        try {
            boolean userExists
                    = ((Boolean) connection.invoke(defaultAuthenticator, "userExists",
                            new Object[]{currentUser},
                            new String[]{"java.lang.String"})).booleanValue();

            return userExists;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static boolean isGroupExists(String currentGroup) throws RuntimeException {
        try {
            boolean gourpExists
                    = ((Boolean) connection.invoke(defaultAuthenticator,
                            "groupExists",
                            new Object[]{currentGroup},
                            new String[]{"java.lang.String"})).booleanValue();

            return gourpExists;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * This one is tricky, You first obtain a String cursor of the Iterator of
     * Users, then you check if It have current, while true we invoke another
     * function called "getCurrentName" which returns the name of the user, then
     * I call advance function for the cursor to move forward, and invoke
     * haveCurrent again and assign it to the same boolean I entered the while
     * with (In order to get out of it!)
     *
     */
    public static List<String> getListOfUsers() throws RuntimeException {
        try {
            List<String> allUsers = new ArrayList<String>();

            String cursor
                    = (String) connection.invoke(defaultAuthenticator, "listUsers",
                            new Object[]{"*",
                                Integer.valueOf(9999)},
                            new String[]{"java.lang.String",
                                "java.lang.Integer"});

            boolean haveCurrent
                    = ((Boolean) connection.invoke(defaultAuthenticator,
                            "haveCurrent",
                            new Object[]{cursor},
                            new String[]{"java.lang.String"})).booleanValue();

            while (haveCurrent) {
                String currentName
                        = (String) connection.invoke(defaultAuthenticator,
                                "getCurrentName",
                                new Object[]{cursor},
                                new String[]{"java.lang.String"});

                allUsers.add(currentName);
                connection.invoke(defaultAuthenticator, "advance",
                        new Object[]{cursor},
                        new String[]{"java.lang.String"});

                haveCurrent
                        = ((Boolean) connection.invoke(defaultAuthenticator, "haveCurrent",
                                new Object[]{cursor},
                                new String[]{"java.lang.String"})).booleanValue();
            }

            return allUsers;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static List<String> getUserGroups(String username) throws RuntimeException {
        try {
            List<String> allUserGroups = new ArrayList<String>();

            String cursor
                    = (String) connection.invoke(defaultAuthenticator, "listMemberGroups",
                            new Object[]{username},
                            new String[]{"java.lang.String"});

            boolean haveCurrent
                    = ((Boolean) connection.invoke(defaultAuthenticator,
                            "haveCurrent",
                            new Object[]{cursor},
                            new String[]{"java.lang.String"})).booleanValue();

            while (haveCurrent) {
                String currentName
                        = (String) connection.invoke(defaultAuthenticator,
                                "getCurrentName",
                                new Object[]{cursor},
                                new String[]{"java.lang.String"});

                allUserGroups.add(currentName);

                connection.invoke(defaultAuthenticator, "advance",
                        new Object[]{cursor},
                        new String[]{"java.lang.String"});

                haveCurrent
                        = ((Boolean) connection.invoke(defaultAuthenticator, "haveCurrent",
                                new Object[]{cursor},
                                new String[]{"java.lang.String"})).booleanValue();
            }

            return allUserGroups;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static List<String> getGroupMembers(String groupName) throws RuntimeException {
        try {
            List<String> allGroupMembers = new ArrayList<String>();

            String cursor
                    = (String) connection.invoke(defaultAuthenticator, "listGroupMembers",
                            new Object[]{groupName, "*", new java.lang.Integer(0)},
                            new String[]{"java.lang.String", "java.lang.String", "java.lang.Integer"});

            boolean haveCurrent
                    = ((Boolean) connection.invoke(defaultAuthenticator,
                            "haveCurrent",
                            new Object[]{cursor},
                            new String[]{"java.lang.String"})).booleanValue();

            while (haveCurrent) {
                String currentName
                        = (String) connection.invoke(defaultAuthenticator,
                                "getCurrentName",
                                new Object[]{cursor},
                                new String[]{"java.lang.String"});

                allGroupMembers.add(currentName);

                connection.invoke(defaultAuthenticator, "advance",
                        new Object[]{cursor},
                        new String[]{"java.lang.String"});

                haveCurrent
                        = ((Boolean) connection.invoke(defaultAuthenticator, "haveCurrent",
                                new Object[]{cursor},
                                new String[]{"java.lang.String"})).booleanValue();
            }

            return allGroupMembers;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static List<String> getListOfGroups() throws RuntimeException {
        try {
            List<String> allUsers = new ArrayList<String>();

            String cursor
                    = (String) connection.invoke(defaultAuthenticator, "listGroups",
                            new Object[]{"*",
                                Integer.valueOf(9999)},
                            new String[]{"java.lang.String",
                                "java.lang.Integer"});

            boolean haveCurrent
                    = ((Boolean) connection.invoke(defaultAuthenticator,
                            "haveCurrent",
                            new Object[]{cursor},
                            new String[]{"java.lang.String"})).booleanValue();

            while (haveCurrent) {
                String currentName
                        = (String) connection.invoke(defaultAuthenticator,
                                "getCurrentName",
                                new Object[]{cursor},
                                new String[]{"java.lang.String"});

                allUsers.add(currentName);

                connection.invoke(defaultAuthenticator, "advance",
                        new Object[]{cursor},
                        new String[]{"java.lang.String"});

                haveCurrent
                        = ((Boolean) connection.invoke(defaultAuthenticator, "haveCurrent",
                                new Object[]{cursor},
                                new String[]{"java.lang.String"})).booleanValue();
            }

            return allUsers;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
