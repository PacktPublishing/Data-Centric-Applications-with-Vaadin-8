package packt.vaadin.datacentric.chapter03;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;

/**
 * @author Alejandro Duarte
 */
public class LoginFormComponent extends Composite<VerticalLayout> {

    public interface LoginListener {
        void logInClicked(LoginFormComponent loginForm);
    }

    private TextField username = new TextField();
    private PasswordField password = new PasswordField();
    private Button loginButton = new Button();
    private Checkbox rememberMe = new Checkbox();

    private LoginListener loginListener;

    public LoginFormComponent(LoginListener loginListener) {
        this();
        this.loginListener = loginListener;
    }

    public LoginFormComponent() {
        getContent().add(username, password, loginButton, rememberMe);
        loginButton.addClickListener(this::logInClicked);
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
        username.setPlaceholder(usernameCaption);
    }

    public void setPasswordCaption(String passwordCaption) {
        password.setPlaceholder(passwordCaption);
    }

    public void setLoginButtonCaption(String loginButtonCaption) {
        loginButton.setText(loginButtonCaption);
    }

    public void setRememberMeCaption(String rememberMeCaption) {
        rememberMe.setLabel(rememberMeCaption);
    }

    public void setCaptions(String usernameCaption, String passwordCaption, String loginButtonCaption, String rememberMeCaption) {
        setUsernameCaption(usernameCaption);
        setPasswordCaption(passwordCaption);
        setLoginButtonCaption(loginButtonCaption);
        setRememberMeCaption(rememberMeCaption);
    }

    private void logInClicked(ClickEvent event) {
        if (loginListener != null) {
            loginListener.logInClicked(this);
        }
    }

}
