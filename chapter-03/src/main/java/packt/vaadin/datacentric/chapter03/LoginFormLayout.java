package packt.vaadin.datacentric.chapter03;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;

import java.io.Serializable;

/**
 * @author Alejandro Duarte
 */
@Deprecated
public class LoginFormLayout extends VerticalLayout {

    public interface LoginListener extends Serializable {
        void logInClicked(LoginFormLayout loginForm);
    }

    private PasswordField username = new PasswordField("Username");
    private PasswordField password = new PasswordField("password");
    private Button logIn = new Button("Log in");
    private Checkbox rememberMe = new Checkbox("Remember me");

    private LoginListener loginListener;

    public LoginFormLayout(LoginListener loginListener) {
        this();
        this.loginListener = loginListener;
    }

    public LoginFormLayout() {
        logIn.addClickListener(this::logInClicked);
        add(username, password, logIn, rememberMe);
    }

    public String getUsername() {
        return username.getValue();
    }

    public String getPassword() {
        return password.getValue();
    }

    public boolean isRememberMe() {
        return rememberMe.getValue();
    }

    public void setLoginListener(LoginListener loginListener) {
        this.loginListener = loginListener;
    }

    private void logInClicked(ClickEvent clickEvent) {
        if (loginListener != null) {
            loginListener.logInClicked(this);
        }
    }

}
