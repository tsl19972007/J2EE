package listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionBindingEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;

@WebListener()
public class CounterListener implements ServletContextListener,
        HttpSessionListener, HttpSessionAttributeListener {

    private ServletContext servletContext;

    private String counterFilePath = "web/data/counter.txt";

    /**
     * 服务器启动
     *
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            servletContext = servletContextEvent.getServletContext();
            servletContext.setAttribute("logged", 0);
            servletContext.setAttribute("guest", 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Context Init");
    }

    public void sessionCreated(HttpSessionEvent sessionEvent) {
        System.out.println("Create Session");
        //writeCounter(servletContext);
    }

    public void sessionDestroyed(HttpSessionEvent sessionEvent) {
        if(sessionEvent.getSession().getAttribute("username")==null){
            servletContext.setAttribute("guest", ((int) servletContext.getAttribute("guest")) - 1);
            System.out.println("Destroy Session guest");
        }
        else {
            servletContext.setAttribute("logged", ((int) servletContext.getAttribute("logged")) - 1);
            System.out.println("Destroy Session logged");
        }
        //writeCounter(servletContext);
    }

    public void attributeAdded(HttpSessionBindingEvent se) {
        System.out.println("HttpSessionBindingEvent Added Name " + se.getName());
        if (se.getName().equals("username")) {
            servletContext.setAttribute("logged", ((int) servletContext.getAttribute("logged") + 1));
        }
        else if(se.getName().equals("visitor")){
            servletContext.setAttribute("guest", ((int) servletContext.getAttribute("guest") + 1));
        }
        //writeCounter(servletContext);
    }


    /**
     * 控制并发
     *
     * @param servletContext 上下文
     */
    private synchronized void writeCounter(ServletContext servletContext) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(counterFilePath));
            writer.write(Integer.toString((int) servletContext.getAttribute("logged")));
            writer.write("\n");
            writer.write(Integer.toString((int) servletContext.getAttribute("guest")));
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
