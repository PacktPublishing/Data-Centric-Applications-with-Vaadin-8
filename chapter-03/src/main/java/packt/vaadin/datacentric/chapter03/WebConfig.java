package packt.vaadin.datacentric.chapter03;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinServlet;

import javax.servlet.annotation.WebServlet;

/**
 * @author Alejandro Duarte
 */
public class WebConfig {

    @WebServlet("/*")
    @VaadinServletConfiguration(ui = VaadinUI.class, productionMode = false)
    public static class Chapter03VaadinServlet extends VaadinServlet {
    }

}
