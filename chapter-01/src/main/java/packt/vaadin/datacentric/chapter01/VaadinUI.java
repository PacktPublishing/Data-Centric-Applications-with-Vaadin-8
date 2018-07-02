package packt.vaadin.datacentric.chapter01;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

/**
 * @author Alejandro Duarte
 */
@Route("")
public class VaadinUI extends VerticalLayout {

    public VaadinUI() {
        add(new Text("Welcome to Data-Centric Applications with Vaadin 10!"));
    }

}
