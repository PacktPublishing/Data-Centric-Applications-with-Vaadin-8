package packt.vaadin.datacentric.chapter06.jooq;

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
    public static class JooqExampleVaadinServlet extends VaadinServlet {
    }

    @WebListener
    public static class JooqExampleContextListener implements ServletContextListener {

        @Override
        public void contextInitialized(ServletContextEvent sce) {
            try {
                JooqService.init();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void contextDestroyed(ServletContextEvent sce) {
            JooqService.shutdown();
        }
    }

}
