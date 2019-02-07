package dao;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.Properties;

public class BaseDao {
    public static Connection getConnection(){
        Connection connection=null;
        DataSource dataSource=null;
        InitialContext jndiContext = null;
        try {
            jndiContext = new InitialContext();
            dataSource = (DataSource) jndiContext.lookup("java:jboss/datasources/mysqlDS");
        } catch (NamingException e) {
            e.printStackTrace();
        }
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
