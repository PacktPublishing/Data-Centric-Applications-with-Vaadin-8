package packt.vaadin.datacentric.chapter09.config;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @author Alejandro Duarte
 */
public class WebConfig {

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
