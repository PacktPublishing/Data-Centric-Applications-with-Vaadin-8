package packt.vaadin.datacentric.chapter06.jooq;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @author Alejandro Duarte
 */
public class WebConfig {

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
