package web.DAO;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPull {
    private static Connection connection;

    // connection initialisation block
    static {
        try {
            Context context = new InitialContext();
            DataSource ds = (DataSource) context.lookup("java:comp/env/jdbc/delivery");
            connection = ds.getConnection();
        } catch (SQLException sqlException) {
            System.out.println("Connection getting sql error");
        } catch (NamingException e) {
            System.out.println("Connection getting naming error");
        }
    }

    public static Connection getConnection(){
        return connection;
    }
}
