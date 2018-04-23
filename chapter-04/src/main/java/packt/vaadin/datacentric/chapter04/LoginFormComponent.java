package packt.vaadin.datacentric.chapter04;

import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Composite;
import com.vaadin.ui.LoginForm;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

/**
 * @author Alejandro Duarte
 */
public class LoginFormComponent extends Composite {

    public interface LoginListener {
        void logInClicked(LoginFormComponent loginForm);
    }

    private TextField username;
    private PasswordField password;
    private CheckBox rememberMe = new CheckBox();

    private LoginListener loginListener;

    private String usernameCaption = "Username";
    private String passwordCaption = "Password";
    private String loginButtonCaption = "Log in";
    private String rememberMeCaption = "Remember me";

    public LoginFormComponent(LoginListener loginListener) {
        this();
        this.loginListener = loginListener;
    }

    public LoginFormComponent() {
        LoginForm loginForm = new LoginForm() {
            @Override
            protected Component createContent(TextField username, PasswordField password, Button loginButton) {
                LoginFormComponent.this.username = username;
                LoginFormComponent.this.password = password;

                username.setCaption(null);
                password.setCaption(null);

                username.setPlaceholder(usernameCaption);
                password.setPlaceholder(passwordCaption);
                loginButton.setCaption(loginButtonCaption);
                rememberMe.setCaption(rememberMeCaption);

                return new VerticalLayout(username, password, loginButton, rememberMe);
            }
        };

        loginForm.addLoginListener(this::logInClicked);

        setCompositionRoot(loginForm);
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

    public void setUsernameCaption(String usernameCaption) {
        this.usernameCaption = usernameCaption;
    }

    public void setPasswordCaption(String passwordCaption) {
        this.passwordCaption = passwordCaption;
    }

    public void setLoginButtonCaption(String loginButtonCaption) {
        this.loginButtonCaption = loginButtonCaption;
    }

    public void setRememberMeCaption(String rememberMeCaption) {
        this.rememberMeCaption = rememberMeCaption;
    }

    public void setCaptions(String usernameCaption, String passwordCaption, String loginButtonCaption, String rememberMeCaption) {
        setUsernameCaption(usernameCaption);
        setPasswordCaption(passwordCaption);
        setLoginButtonCaption(loginButtonCaption);
        setRememberMeCaption(rememberMeCaption);
    }

    private void logInClicked(LoginForm.LoginEvent loginEvent) {
        if (loginListener != null) {
            loginListener.logInClicked(this);
        }
    }

}
