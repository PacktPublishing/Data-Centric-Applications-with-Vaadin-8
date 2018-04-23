package packt.vaadin.datacentric.chapter04;

import com.vaadin.ui.Composite;
import com.vaadin.ui.Notification;

/**
 * @author Alejandro Duarte
 */
public class PublicComponent extends Composite {

    public PublicComponent() {
        LoginFormComponent loginForm = new LoginFormComponent();
        loginForm.setCaptions(
                Messages.get("auth.username"),
                Messages.get("auth.password"),
                Messages.get("auth.login"),
                Messages.get("auth.rememberMe"));

        loginForm.setLoginListener(form -> loginClicked(form));
        setCompositionRoot(loginForm);
    }

    private void loginClicked(LoginFormComponent form) {
        if (!AuthService.authenticate(form.getUsername(), form.getPassword(), form.isRememberMe())) {
            Notification.show(Messages.get("auth.bad.credentials"), Notification.Type.ERROR_MESSAGE);
        }
    }

}
