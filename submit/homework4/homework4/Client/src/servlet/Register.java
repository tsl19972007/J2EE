package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import factory.EJBFactory;
import service.OrderService;
import service.UserService;
import model.User;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
    private UserService userServiceImpl;

    public Register(){
        super();
        userServiceImpl=(UserService)EJBFactory.getEJB("UserServiceBean","service.UserService");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>注册</h1>");
        out.println(
                "<form method='POST' action='"
                        + response.encodeURL(request.getContextPath()+"/Register")
                        + "'>");
        out.println(
                "username: <input type='text' name='username' value=''>");
        out.println(
                "password: <input type='password' name='password' value=''>");
        out.println("<input type='submit' name='Register' value='Register'>");

        out.println("<p>Servlet is version @version@</p>");

        out.println("</form></body></html>");

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置逻辑实现
        User user=new User(request.getParameter("username"),request.getParameter("password"),100);
        boolean register_result=userServiceImpl.addUser(user);
        PrintWriter out = response.getWriter();
        if(register_result) {
            out.println("<h1>Successfully Registered!</h1>");
            out.println("<h4>username: " + request.getParameter("username") + "</h4>");
            out.println("<h4>password: " + request.getParameter("password") + "</h4>");
        }else{
            out.println("<h1>An error occurred while registering!</h1>");
            out.println("<h4>username: " + request.getParameter("username") + "</h4>");
            out.println("<h4>password: " + request.getParameter("password") + "</h4>");
        }
    }
}

