package packt.vaadin.datacentric.chapter04;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

/**
 * @author Alejandro Duarte
 */
@Route("")
public class VaadinUI extends Composite<VerticalLayout> {

    static {
        Messages.addBundle("messages");
    }

    public VaadinUI() {
        if (AuthService.isAuthenticated()) {
            getContent().add(new PrivateComponent());
        } else {
            getContent().add(new PublicComponent());
        }
    }

}
