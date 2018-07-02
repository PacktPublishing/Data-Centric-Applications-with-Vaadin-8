package packt.vaadin.datacentric.chapter05.jdbc;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @author Alejandro Duarte
 */
public class WebConfig {

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
