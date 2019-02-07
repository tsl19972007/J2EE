package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "EncodingFilter",urlPatterns = "/*")
public class EncodingFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        //System.out.println("I'm Filter!");
        //对request和response进行一些预处理
        HttpServletRequest request=(HttpServletRequest)req;
        String method=request.getMethod();
        String url=request.getRequestURI();
        if(method.equals("POST")) {
            if(!url.equals("/ServletTest/ShowHomePage")) {
                req.setCharacterEncoding("utf-8");
                resp.setCharacterEncoding("utf-8");
            }
        }
        if(method.equals("GET")){
            if(url.equals("/ServletTest/ShoppingCart")){
                req.setCharacterEncoding("utf-8");
                resp.setCharacterEncoding("utf-8");
            }
        }
        chain.doFilter(req, resp);//让目标资源执行，即：放行
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
