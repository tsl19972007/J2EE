package servlet;

import model.Commodity;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import service.CommodityService;
import factory.ServiceFactory;

@WebServlet("/GetCommodity")
public class GetCommodity extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CommodityService commodityServiceImpl=ServiceFactory.getCommodityService();
        ArrayList<Commodity> list=commodityServiceImpl.getCommodityList();
        response.getWriter().print(commodityServiceImpl.comListToString(list));
    }
}
