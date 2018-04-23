package packt.vaadin.datacentric.chapter09.config;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinServlet;
import packt.vaadin.datacentric.chapter09.ui.VaadinUI;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.annotation.WebServlet;

/**
 * @author Alejandro Duarte
 */
public class WebConfig {

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(ui = VaadinUI.class, productionMode = false)
    public static class Chapter09VaadinServlet extends VaadinServlet {
    }

    @WebListener
    public static class Chapter09ContextListener implements ServletContextListener {

        @Override
        public void contextInitialized(ServletContextEvent sce) {
            JPAService.init();
            DataGenerator.start(1000000 / 12, 6);
        }

        @Override
        public void contextDestroyed(ServletContextEvent sce) {
            DataGenerator.stop();
            JPAService.close();
        }
    }

}
