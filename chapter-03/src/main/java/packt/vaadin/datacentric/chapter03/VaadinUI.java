package packt.vaadin.datacentric.chapter03;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Notification;
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
        LoginFormComponent loginForm = new LoginFormComponent();
        loginForm.setCaptions(
                Messages.get("auth.username"),
                Messages.get("auth.password"),
                Messages.get("auth.login"),
                Messages.get("auth.rememberMe"));

        loginForm.setLoginListener(form -> loginClicked(form));
        setContent(loginForm);
    }

    private void loginClicked(LoginFormComponent form) {
        Notification.show("Not implemented yet.");
    }

}
