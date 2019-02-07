package dao;

import model.User;

import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;

public class UserDaoImpl implements UserDao {

    private static UserDaoImpl userDao = new UserDaoImpl();

    public static UserDaoImpl getInstance() {
        return userDao;
    }

    @Override
    public User findUser(String username, String password) {
        Connection connection = BaseDao.getConnection();
        User user=null;
        try {
            PreparedStatement stmt = null;
            stmt = connection.prepareStatement("select * from user where username=? and password=?;");
            stmt.setString(1, (String) username);
            stmt.setString(2, (String) password);
            ResultSet result = stmt.executeQuery();
            if(result.next()) {
                user=new User(result.getInt(1),
                        result.getString(2),
                        result.getString(3),
                        result.getDouble(4));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public boolean addUser(User user) {
        Connection connection = BaseDao.getConnection();
        PreparedStatement stmt = null;
        ArrayList<String> usernameList = new ArrayList<String>();
        Statement sm = null;
        try {
            String select = "select username from user";
            sm = connection.createStatement();
            ResultSet execute_result = sm.executeQuery(select);
            while (execute_result.next()) {
                usernameList.add(execute_result.getString(1));
            }
            execute_result.close();
            sm.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (usernameList.contains((String) user.getUsername())) {
            return false;
        }
        else {
            try {
                stmt = connection.prepareStatement("insert into user(username, password, balance) values(?, ?, ?);");
                stmt.setString(1, user.getUsername());
                stmt.setString(2, user.getPassword());
                stmt.setDouble(3, user.getBalance());
                stmt.executeUpdate();
                stmt.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return true;
    }

    @Override
    public void updateUser(User user) {
        Connection connection = BaseDao.getConnection();
        PreparedStatement stmt = null;
        try{
            stmt = connection.prepareStatement("update user set username=? password=? balance=? where id=?");
            stmt.setString(1,user.getUsername());
            stmt.setString(2,user.getPassword());
            stmt.setDouble(3,user.getBalance());
            stmt.setInt(4,user.getId());
            int res=stmt.executeUpdate();
            stmt.close();
            assert res!=0;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
