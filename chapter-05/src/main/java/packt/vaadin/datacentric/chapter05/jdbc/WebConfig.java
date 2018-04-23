package packt.vaadin.datacentric.chapter05.jdbc;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinServlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.annotation.WebServlet;

/**
 * @author Alejandro Duarte
 */
public class WebConfig {

    @WebServlet("/*")
    @VaadinServletConfiguration(ui = VaadinUI.class, productionMode = false)
    public static class JdbcExampleVaadinServlet extends VaadinServlet {
    }

    @WebListener
    public static class JdbcExampleContextListener implements ServletContextListener {

        @Override
        public void contextInitialized(ServletContextEvent sce) {
            try {
                DatabaseService.init();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void contextDestroyed(ServletContextEvent sce) {
            DatabaseService.shutdown();
        }
    }

}
