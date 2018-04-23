package packt.vaadin.datacentric.chapter07.config;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinServlet;
import packt.vaadin.datacentric.chapter07.ui.VaadinUI;

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
    public static class Chapter07VaadinServlet extends VaadinServlet {
    }

    @WebListener
    public static class Chapter07ContextListener implements ServletContextListener {

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
