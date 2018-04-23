package packt.vaadin.datacentric.chapter01;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;

/**
 * @author Alejandro Duarte
 */
public class VaadinUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        setContent(new Label("Welcome to Data-Centric Applications with Vaadin 8!"));
    }

}
