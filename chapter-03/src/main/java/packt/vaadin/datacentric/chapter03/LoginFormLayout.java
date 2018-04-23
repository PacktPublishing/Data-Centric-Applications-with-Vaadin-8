package packt.vaadin.datacentric.chapter03;

import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import java.io.Serializable;

/**
 * @author Alejandro Duarte
 */
@Deprecated
public class LoginFormLayout extends VerticalLayout {

    public interface LoginListener extends Serializable {
        void logInClicked(LoginFormLayout loginForm);
    }

    private TextField username = new PasswordField("Username");
    private PasswordField password = new PasswordField("password");
    private Button logIn = new Button("Log in");
    private CheckBox rememberMe = new CheckBox("Remember me");

    private LoginListener loginListener;

    public LoginFormLayout(LoginListener loginListener) {
        this();
        this.loginListener = loginListener;
    }

    public LoginFormLayout() {
        logIn.addClickListener(this::logInClicked);
        addComponents(username, password, logIn, rememberMe);
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

    private void logInClicked(Button.ClickEvent clickEvent) {
        if (loginListener != null) {
            loginListener.logInClicked(this);
        }
    }

}
