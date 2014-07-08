/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.khleuven.bjornbillen.db;

/**
 *
 * @author Bejarn
 */
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Database {

    private static Database database = new Database();
    public static ReservationSystemRemote REMOTEDB;

    private Database() {
    }
    // singleton

    public static void init() {
        Context c;
        Object o;
        try {
            // volgens website : http://stackoverflow.com/questions/9453775/caused-by-javax-naming-namingexception
        /*
             Properties props = new Properties();
             props.setProperty("java.naming.factory.initial",
             "com.sun.enterprise.naming.SerialInitContextFactory");
             props.setProperty("java.naming.factory.url.pkgs",
             "com.sun.enterprise.naming");
             props.setProperty("java.naming.factory.state",
             "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");
             props.setProperty("org.omg.CORBA.ORBInitialHost", "localhost");
             props.setProperty("org.omg.CORBA.ORBInitialPort", "3700");
             */

            c = new InitialContext(/*props*/);
            o = c.lookup("java:global/ReservatieEJBFinal/ReservationSystem!be.khleuven.bjornbillen.db.ReservationSystemRemote");
            REMOTEDB = (ReservationSystemRemote) o;
        } catch (Exception e) {
            System.out.println("EJB error" + " exception :" + e);
        }

    }
}
