package packt.vaadin.datacentric.chapter06.jpa;

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
    public static class JpaExampleVaadinServlet extends VaadinServlet {
    }

    @WebListener
    public static class JpaExampleContextListener implements ServletContextListener {

        @Override
        public void contextInitialized(ServletContextEvent sce) {
            JPAService.init();
        }

        @Override
        public void contextDestroyed(ServletContextEvent sce) {
            JPAService.close();
        }
    }

}
