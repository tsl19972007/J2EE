package servlet;

import model.Commodity;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;


@WebServlet("/GetCommodity")
public class GetCommodity extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private DataSource datasource = null;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetCommodity() {
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
        } catch (NamingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<Commodity> list=getCommodityList();
        response.getWriter().print(comListToString(list));    //将结果返回到前端页面
    }

    public ArrayList<Commodity> getCommodityList() {
        Connection connection = null;
        ArrayList<Commodity> list = new ArrayList<Commodity>();

        try {
            connection = datasource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            String select = "select * from commodity";
            Statement sm = connection.createStatement();
            ResultSet result = sm.executeQuery(select);
            while (result.next()) {
                Commodity com = new Commodity();
                com.setId(result.getInt("id"));
                com.setName(result.getString("name"));
                com.setPrice(result.getDouble("price"));
                list.add(com);
                //System.out.println("comid: "+result.getInt("id"));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return list;
    }

    private String comListToString(ArrayList<Commodity> list){
        String res="";
        for(int i=0;i<list.size();i++){
            if(i!=0){
                res+=";";
            }
            res+=list.get(i).toString();
        }
        return res;
    }
}
