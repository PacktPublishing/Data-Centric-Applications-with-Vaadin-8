package packt.vaadin.datacentric.chapter04;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

/**
 * @author Alejandro Duarte
 */
public class VaadinUI extends UI {

    static {
        Messages.addBundle("messages");
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        if (AuthService.isAuthenticated()) {
            setContent(new PrivateComponent());
        } else {
            setContent(new PublicComponent());
        }
    }

}
