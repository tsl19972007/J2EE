package servlet;

import factory.ServiceFactory;
import model.Order;
import model.OrderItem;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;

import service.OrderService;
import service.UserService;

@WebServlet("/SubmitOrder")
public class SubmitOrder extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        HttpSession session = request.getSession(false);
        if (session == null||session.getAttribute("username") == null) {
            System.out.println("SubmitOrderWrong");
            response.getWriter().print("notLogin");
            return;
        }
        String username=(String)session.getAttribute("username");
        String password=(String)session.getAttribute("password");
        int userId=0;
        double balance=0;
        UserService userServiceImpl=ServiceFactory.getUserService();
        User user=userServiceImpl.findUser(username,password);
        userId=user.getId();
        balance=user.getBalance();

        String item_list=(String)session.getAttribute("item_list");
        String[] sp_list=item_list.split(";");
        ArrayList<OrderItem> itemList=new ArrayList<OrderItem>();
        for(int i=0;i<sp_list.length;i++){
            String[] sp_item=sp_list[i].split(":");
            OrderItem item=new OrderItem(Integer.parseInt(sp_item[0]),
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
        displaySubmitResult(request,response);
    }

    private void saveOrder(Order order){
        OrderService orderServiceImpl= ServiceFactory.getOrderService();
        orderServiceImpl.saveOrder(order);
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

    private void displaySubmitResult(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        String result=request.getParameter("result");
        String discount=request.getParameter("discount");
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
}
