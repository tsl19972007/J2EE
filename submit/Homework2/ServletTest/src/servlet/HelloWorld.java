package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/*
使用注解配置Servlet（或在web.xml中进行配置）
见：https://www.cnblogs.com/printN/p/6537903.html
 */

@WebServlet("/HelloWorld")
public class HelloWorld extends HttpServlet {
    private String message;

    @Override
    public void init() throws ServletException {
        message = "Hello world, this message is from servlet!";
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(req.getCharacterEncoding());
        req.getRequestDispatcher("/static/html/hello.html").include(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("redirect");
        var base="http://localhost:8080/ServletTest/Visitor";
        resp.getWriter().print(base+"Visitor");
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
