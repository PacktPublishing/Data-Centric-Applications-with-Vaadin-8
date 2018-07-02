package packt.vaadin.datacentric.chapter08.config;

import net.sf.jasperreports.j2ee.servlets.ImageServlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.annotation.WebServlet;

/**
 * @author Alejandro Duarte
 */
public class WebConfig {

    @WebServlet("/image")
    public static class ReportsImageServlet extends ImageServlet {
    }

    @WebListener
    public static class Chapter08ContextListener implements ServletContextListener {

        @Override
        public void contextInitialized(ServletContextEvent sce) {
            JPAService.init();
            MyBatisService.init();
            DataGenerator.start(1000000 / 12, 6);
        }

        @Override
        public void contextDestroyed(ServletContextEvent sce) {
            DataGenerator.stop();
            JPAService.close();
        }
    }

}
