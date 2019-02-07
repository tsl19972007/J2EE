package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/ShoppingCart")
public class ShoppingCart extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null||session.getAttribute("username") == null) {
            response.getWriter().print("未登录");
            System.out.println("ShoppingCartWrong");
            return;
        }
        String cart=request.getParameter("item_list");
        System.out.println("saveCart:"+cart);
        session.setAttribute("item_list", cart);
        response.getWriter().print("success");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null||session.getAttribute("username") == null) {
            System.out.println("ShoppingCartWrong");
            return;
        }
        String cart=(String)session.getAttribute("item_list");
        System.out.println("getCart:"+cart);
        response.getWriter().print(cart);
    }
}
