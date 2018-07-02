package packt.vaadin.datacentric.chapter03;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.notification.Notification;
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
        LoginFormComponent loginForm = new LoginFormComponent();
        loginForm.setCaptions(
                Messages.get("auth.username"),
                Messages.get("auth.password"),
                Messages.get("auth.login"),
                Messages.get("auth.rememberMe"));

        loginForm.setLoginListener(form -> loginClicked(form));
        getContent().add(loginForm);
    }

    private void loginClicked(LoginFormComponent form) {
        Notification.show("Not implemented yet.");
    }

}
