package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private DataSource datasource = null;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub

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

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置逻辑实现
        initDB();
        Connection connection = null;
        PreparedStatement stmt = null;
        ArrayList<String> usernameList = new ArrayList<String>();
        Statement sm = null;
        try {
            connection = datasource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        if (usernameList.contains((String) request.getParameter("username"))) {
            PrintWriter out = response.getWriter();
            out.println("<h1>Username Repeated!</h1>");
        }
        else {
            int register_result=0;
            try {
                stmt = connection.prepareStatement("insert into user(username, password, balance) values(?, ?, 100);");
                stmt.setString(1, (String) request.getParameter("username"));
                stmt.setString(2, (String) request.getParameter("password"));
                register_result = stmt.executeUpdate();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            PrintWriter out = response.getWriter();
            if(register_result>0) {
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

