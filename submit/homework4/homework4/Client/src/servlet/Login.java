package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);

        // Logout action removes session, but the cookie remains
        if (null != request.getParameter("Logout")) {
            if (null != session) {
                if(session.getAttribute("username")==null){
                    System.out.println("visitor logout, session turns null");
                }else {
                    String username = (String) session.getAttribute("username");
                    System.out.println(username + " logout, session turns null");
                }
            	session.invalidate();
            }
        }
        else{
            if(null!=session&&(session.getAttribute("username")!=null||session.getAttribute("visitor")!=null)){
                if(session.getAttribute("username")!=null) {
                    response.sendRedirect(request.getContextPath() + "/ShowHomePage");
                }else if(session.getAttribute("visitor")!=null){
                    response.sendRedirect(request.getContextPath() + "/Visitor");
                }
            }
        }
        displayLoginPage(request,response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

	private void displayLoginPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String login="";
        HttpSession session = request.getSession(false);
        Cookie cookie = null;
        Cookie[] cookies = request.getCookies();

        if (null != cookies) {
            // Look through all the cookies and see if the
            // cookie with the login info is there.
            for (int i = 0; i < cookies.length; i++) {
                cookie = cookies[i];
                if (cookie.getName().equals("LoginCookie")) {
                    login=cookie.getValue();
                    break;
                }
            }
        }

        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>登陆</h1>");
        out.println(
                "<form method='POST' action='"
                        + response.encodeURL(request.getContextPath()+"/ShowHomePage")
                        + "'>");
        out.println(
                "username: <input type='text' name='username' value='" + login + "'>");
        out.println(
                "password: <input type='password' name='password' value=''>");
        out.println("<input type='submit' name='Login' value='Login'></form>");
        out.println(
                "<form method='GET' action='"
                        + response.encodeURL(request.getContextPath()+"/Visitor")
                        + "'>");
        out.println("<input type='submit' name='Visitor' value='Visitor'></form>");
        ServletContext context = getServletContext();
        int user = (int) context.getAttribute("logged");
        int guest = (int) context.getAttribute("guest");
        int total=user+guest;
        out.println("<p>在线人数:"+total+"</p>");
        out.println("<p>登陆人数:"+user+"</p>");
        out.println("<p>游客人数:"+guest+"</p>");
        out.println("</body></html>");
    }

}
