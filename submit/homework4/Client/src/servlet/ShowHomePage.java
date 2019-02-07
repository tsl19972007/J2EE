package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import factory.EJBFactory;
import service.UserService;

/**
 * Servlet implementation class StockListServlet
 */
@WebServlet("/ShowHomePage")
public class ShowHomePage extends HttpServlet {
	private UserService userServiceImpl;

	public ShowHomePage(){
		super();
		userServiceImpl=(UserService)EJBFactory.getEJB("UserServiceBean","service.UserService");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

	/*
	寻找名为LoginCookie的cookie值,找到则更新为登陆的用户名;找不到则添加;
	getSession若为null,通过req.getParameter获取用户名;否则通过session
	 */
	private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException {
		HttpSession session = req.getSession(false);
		boolean cookieFound = false;
		Cookie cookie = null;
		Cookie[] cookies = req.getCookies();
		if (null != cookies) {
			// Look through all the cookies and see if the
			// cookie with the login info is there.
			for (int i = 0; i < cookies.length; i++) {
				cookie = cookies[i];
				if (cookie.getName().equals("LoginCookie")) {
					cookieFound = true;
					break;
				}
			}
		}

		//由于index.jsp会自动创建session，可能导致session!=null而session中username为null的情况
		if (session == null||session.getAttribute("username") == null) {
			String username = req.getParameter("username");
			String password = req.getParameter("password");
			System.out.println("get username "+ username + " by req");
			boolean isLoginAction = (null == username) ? false : true;
			if (isLoginAction) { // User is logging in
				if(null==userServiceImpl.findUser(username,password)){
					displayLoginWrongPage(req,resp);
				}
				else {
					if (cookieFound) { // If the cookie exists update the value only
						// if changed
						System.out.println("Find cookie");
						if (!username.equals(cookie.getValue())) {
							cookie.setValue(username);
							resp.addCookie(cookie);
						}
					} else {
						// If the cookie does not exist, create it and set value
						cookie = new Cookie("LoginCookie", username);
						cookie.setMaxAge(Integer.MAX_VALUE);
						System.out.println("Add cookie");
						resp.addCookie(cookie);
					}
					// create a session to show that we are logged in
					session = req.getSession(true);
					session.setAttribute("username", username);
					session.setAttribute("password", password);
					req.getRequestDispatcher("static/html/homepage.html").include(req,resp);
				}
			} else {
				// Display the login page. If the cookie exists, set login
				resp.sendRedirect(req.getContextPath() + "/Login");
			}
		} else {
			String username = (String) session.getAttribute("username");
			System.out.println("get username "+ username + " by session");
			req.getRequestDispatcher("static/html/homepage.html").include(req,resp);
		}
	}

	public void displayLoginWrongPage(HttpServletRequest req, HttpServletResponse res) throws IOException  {
		PrintWriter out = res.getWriter();
		out.println("<h1>Username or password wrong!</h1>");
	}
}
