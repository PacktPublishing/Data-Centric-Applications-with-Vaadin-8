package packt.vaadin.datacentric.chapter04;

import com.vaadin.server.Page;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinSession;

/**
 * @author Alejandro Duarte
 */
public class AuthService {

    public static final String USERNAME_ATTRIBUTE = "username";

    public static boolean authenticate(String username, String password) {
        boolean authentic = "admin".equals(username) && "admin".equals(password);

        if (authentic) {
            VaadinSession.getCurrent().setAttribute(USERNAME_ATTRIBUTE, username);
            Page.getCurrent().reload();
        }

        return authentic;
    }

    public static boolean isAuthenticated() {
        return VaadinSession.getCurrent().getAttribute(USERNAME_ATTRIBUTE) != null;
    }

    public static String getAuthenticatedUsername() {
        return (String) VaadinSession.getCurrent().getAttribute(USERNAME_ATTRIBUTE);
    }

    public static void logout() {
        VaadinService.getCurrentRequest().getWrappedSession().invalidate();
        Page.getCurrent().setLocation("");
    }

}
