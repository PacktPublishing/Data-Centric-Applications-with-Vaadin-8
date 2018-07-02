package packt.vaadin.datacentric.chapter04;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.notification.Notification;

/**
 * @author Alejandro Duarte
 */
public class PublicComponent extends Composite<LoginFormComponent> {

    public PublicComponent() {
        getContent().setCaptions(
                Messages.get("auth.username"),
                Messages.get("auth.password"),
                Messages.get("auth.login"),
                Messages.get("auth.rememberMe"));

        getContent().setLoginListener(form -> loginClicked(form));
    }

    private void loginClicked(LoginFormComponent form) {
        if (!AuthService.authenticate(form.getUsername(), form.getPassword(), form.isRememberMe())) {
            Notification.show(Messages.get("auth.bad.credentials"));
        }
    }

}
