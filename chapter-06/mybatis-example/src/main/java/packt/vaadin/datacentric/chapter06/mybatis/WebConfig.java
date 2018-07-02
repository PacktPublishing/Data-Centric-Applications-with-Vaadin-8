package packt.vaadin.datacentric.chapter06.mybatis;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @author Alejandro Duarte
 */
public class WebConfig {

    @WebListener
    public static class JpaExampleContextListener implements ServletContextListener {

        @Override
        public void contextInitialized(ServletContextEvent sce) {
            MyBatisService.init();
        }

        @Override
        public void contextDestroyed(ServletContextEvent sce) {
        }
    }

}
