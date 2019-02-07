package servlet;

import model.Order;
import model.Order_item;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import java.sql.ResultSet;

@WebServlet("/SubmitOrder")
public class SubmitOrder extends HttpServlet {
    private DataSource datasource = null;

    public void init() {
        initDB();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        Connection connection = null;
        PreparedStatement stmt = null;
        HttpSession session = request.getSession(false);
        if (session == null||session.getAttribute("username") == null) {
            System.out.println("SubmitOrderWrong");
            response.getWriter().print("notLogin");
            return;
        }
        String username=(String)session.getAttribute("username");
        int userId=0;
        double balance=0;
        try {
            connection = datasource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            stmt = connection.prepareStatement("select id, balance from User where username=?");
            stmt.setString(1, username);
            ResultSet result=stmt.executeQuery();
            while (result.next()) {
                userId=result.getInt(1);
                balance=result.getDouble(2);
            }
            stmt.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String item_list=(String)session.getAttribute("item_list");
        String[] sp_list=item_list.split(";");
        ArrayList<Order_item> itemList=new ArrayList<Order_item>();
        for(int i=0;i<sp_list.length;i++){
            String[] sp_item=sp_list[i].split(":");
            Order_item item=new Order_item(Integer.parseInt(sp_item[0]),
                    Double.parseDouble(sp_item[3]),
                    Integer.parseInt(sp_item[2]),
                    Double.parseDouble(sp_item[4]));
            itemList.add(item);
        }
        Order order=new Order(userId,itemList);
        System.out.println(order.getOrderSum());
        if(balance<order.getOrderSum()){
            response.getWriter().print("http://localhost:8080/ServletTest/SubmitOrder?result=fail");
        }else {
            saveOrder(order);
            clearShoppingCart(request, response);
            if(order.getOrderSum()>66){
                int discount=(int)order.getOrderSum()/10;
                response.getWriter().print("http://localhost:8080/ServletTest/SubmitOrder?result=success&discount="+discount);
            }else{
                response.getWriter().print("http://localhost:8080/ServletTest/SubmitOrder?result=success");
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        String result=request.getParameter("result");
        String discount=request.getParameter("discount");
        System.out.println(result);
        System.out.println(discount);
        String s1="",s2="";
        if("success".equals(result)){
            s1="Submit Success";
        }else{
            s1="Submit Fail";
        }
        if(null!=discount){
            s2=",get discount "+discount;
        }
        out.println("<html><body>");
        out.println("<h1>"+s1+s2+"</h1>");
        out.println("<a href='http://localhost:8080/ServletTest/ShowHomePage'>返回</a>");
        out.println("</body></html>");
    }

    private void saveOrder(Order order){
        Connection connection = null;
        PreparedStatement stmt = null;
        Statement sm = null;
        try {
            connection = datasource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            stmt = connection.prepareStatement("insert into `Order`(user_id, order_sum, remarks) values(?, ?, ?);");
            stmt.setInt(1, order.getUserId());
            stmt.setDouble(2, order.getOrderSum());
            stmt.setString(3,order.getRemarks());
            int result = stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            int maxId=0;
            String select = "select max(id) from `Order`";
            sm = connection.createStatement();
            ResultSet execute_result = sm.executeQuery(select);
            while (execute_result.next()) {
                maxId=execute_result.getInt(1);
            }
            execute_result.close();
            sm.close();

            stmt = connection.prepareStatement("insert into Order_item(order_id, commodity_id, price, num, item_sum) values(?, ?, ?, ?, ?);");
            for(int i=0;i<order.getItemList().size();i++){
                Order_item item=order.getItemList().get(i);
                stmt.setInt(1, maxId);
                stmt.setInt(2, item.getCommodityId());
                stmt.setDouble(3,item.getPrice());
                stmt.setInt(4,item.getNum());
                stmt.setDouble(5,item.getItemSum());
                stmt.executeUpdate();
            }
            stmt.close();
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

    private void clearShoppingCart(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession(false);
        if (session == null||session.getAttribute("username") == null) {
            System.out.println("ShoppingCartWrong");
            return;
        }
        System.out.println("clear cart");
        session.setAttribute("item_list", null);
    }

    private void initDB(){
        InitialContext jndiContext = null;

        Properties properties = new Properties();
        properties.put(javax.naming.Context.PROVIDER_URL, "jnp:///");
        properties.put(javax.naming.Context.INITIAL_CONTEXT_FACTORY, "org.apache.naming.java.javaURLContextFactory");
        try {
            jndiContext = new InitialContext(properties);
            datasource = (DataSource) jndiContext.lookup("java:comp/env/jdbc/ServletTest");
        } catch (NamingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
