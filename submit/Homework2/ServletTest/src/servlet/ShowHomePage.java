package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import model.Commodity;



/**
 * Servlet implementation class StockListServlet
 */
@WebServlet("/ShowHomePage")
public class ShowHomePage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DataSource datasource = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ShowHomePage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void init() {
		InitialContext jndiContext = null;

		Properties properties = new Properties();
		properties.put(javax.naming.Context.PROVIDER_URL, "jnp:///");
		properties.put(javax.naming.Context.INITIAL_CONTEXT_FACTORY, "org.apache.naming.java.javaURLContextFactory");
		try {
			jndiContext = new InitialContext(properties);
			datasource = (DataSource) jndiContext.lookup("java:comp/env/jdbc/ServletTest");
			System.out.println("got context");
			System.out.println("About to get ds");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
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
			String loginValue = req.getParameter("username");
			String password = req.getParameter("password");
			System.out.println("get username "+ loginValue + " by req");
			boolean isLoginAction = (null == loginValue) ? false : true;
			if (isLoginAction) { // User is logging in
				Connection connection = null;
				boolean logged=false;
				try {
					connection = datasource.getConnection();
				} catch (SQLException e) {
					e.printStackTrace();
				}

				try {
					PreparedStatement stmt = null;
					stmt = connection.prepareStatement("select null from user where username=? and password=?;");
					stmt.setString(1, (String) req.getParameter("username"));
					stmt.setString(2, (String) req.getParameter("password"));
					ResultSet login_result = stmt.executeQuery();
					if(login_result.next()) {
						logged = true;
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(!logged){
					displayLoginWrongPage(req,resp);
				}
				else {
					if (cookieFound) { // If the cookie exists update the value only
						// if changed
						System.out.println("Find cookie");
						if (!loginValue.equals(cookie.getValue())) {
							cookie.setValue(loginValue);
							resp.addCookie(cookie);
						}
					} else {
						// If the cookie does not exist, create it and set value
						cookie = new Cookie("LoginCookie", loginValue);
						cookie.setMaxAge(Integer.MAX_VALUE);
						System.out.println("Add cookie");
						resp.addCookie(cookie);
					}
					// create a session to show that we are logged in
					session = req.getSession(true);
					session.setAttribute("username", loginValue);
					req.setAttribute("username", loginValue);
					System.out.println(req.getCharacterEncoding());
					req.getRequestDispatcher("static/html/homepage.html").include(req,resp);
					/*
					displayLogoutPage(req, resp);
					*/
				}
			} else {
				// Display the login page. If the cookie exists, set login
				resp.sendRedirect(req.getContextPath() + "/Login");
			}
		} else {
			String loginValue = (String) session.getAttribute("username");
			System.out.println("get username "+ loginValue + " by session");
			req.setAttribute("username", loginValue);
			req.getRequestDispatcher("static/html/homepage.html").include(req,resp);
			/*
			displayLogoutPage(req, resp);
			*/
		}
	}

	public void displayLoginWrongPage(HttpServletRequest req, HttpServletResponse res) throws IOException {
		PrintWriter out = res.getWriter();
		out.println("<h1>Username or password wrong!</h1>");
	}

	/*
	public void displayLogoutPage(HttpServletRequest req, HttpServletResponse res) throws IOException {
		PrintWriter out = res.getWriter();
		HttpSession session=req.getSession(true	);
		String username="";
		if (session != null) {
			username=(String)session.getAttribute("username");
		}
		out.println("<h1>Welcome User "+username+"</h1>");
		out.println("<form method='GET' action='" + res.encodeURL(req.getContextPath() + "/Login") + "'>");
		out.println("</p>");
		out.println("<input type='submit' name='Logout' value='Logout'>");
		out.println("</form>");
		out.println("<p>Servlet is version @version@</p>");
		out.println("</body></html>");
	}
	*/

}
