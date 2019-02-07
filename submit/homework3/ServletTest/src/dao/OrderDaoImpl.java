package dao;

import model.Order;
import model.OrderItem;

import java.sql.*;

public class OrderDaoImpl implements OrderDao {

    private static OrderDaoImpl orderDao = new OrderDaoImpl();

    public static OrderDaoImpl getInstance() {
        return orderDao;
    }
    @Override
    public void saveOrder(Order order) {
        Connection connection = BaseDao.getConnection();
        PreparedStatement stmt = null;
        Statement sm = null;
        try {
            //save Order
            stmt = connection.prepareStatement("insert into `Order`(user_id, order_sum, remarks) values(?, ?, ?);");
            stmt.setInt(1, order.getUserId());
            stmt.setDouble(2, order.getOrderSum());
            stmt.setString(3,order.getRemarks());
            stmt.executeUpdate();
            stmt.close();
            //find OrderId saved before
            int maxId=0;
            String select = "select max(id) from `Order`";
            sm = connection.createStatement();
            ResultSet execute_result = sm.executeQuery(select);
            while (execute_result.next()) {
                maxId=execute_result.getInt(1);
            }
            execute_result.close();
            sm.close();
            //save OrderItem
            stmt = connection.prepareStatement("insert into Order_item(order_id, commodity_id, price, num, item_sum) values(?, ?, ?, ?, ?);");
            for(int i=0;i<order.getItemList().size();i++){
                OrderItem item=order.getItemList().get(i);
                stmt.setInt(1, maxId);
                stmt.setInt(2, item.getCommodityId());
                stmt.setDouble(3,item.getPrice());
                stmt.setInt(4,item.getNum());
                stmt.setDouble(5,item.getItemSum());
                stmt.executeUpdate();
            }
            stmt.close();
            //update user balance
            stmt = connection.prepareStatement("update user set balance=balance-? where id=?");
            stmt.setDouble(1,order.getOrderSum());
            stmt.setInt(2,order.getUserId());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
