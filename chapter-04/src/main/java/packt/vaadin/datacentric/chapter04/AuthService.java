package packt.vaadin.datacentric.chapter04;

import com.vaadin.server.Page;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinSession;

import javax.servlet.http.Cookie;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Alejandro Duarte
 */
public class AuthService {

    private static final String USERNAME_ATTRIBUTE = "username";
    private static final String COOKIE_NAME = "remember-me";
    public static final int COOKIE_MAX_AGE_DAYS = 30;

    private static Map<String, String> remembered = new HashMap<>();
    private static SecureRandom random = new SecureRandom();

    public static boolean authenticate(String username, String password, boolean rememberMe) {
        boolean authentic = "admin".equals(username) && "admin".equals(password);

        if (authentic) {
            if (rememberMe) {
                rememberUser(username);
            }
            VaadinSession.getCurrent().setAttribute(USERNAME_ATTRIBUTE, username);
            Page.getCurrent().reload();
        }

        return authentic;
    }

    public static boolean isAuthenticated() {
        if (VaadinSession.getCurrent().getAttribute(USERNAME_ATTRIBUTE) != null) {
            return true;
        } else if (isRememberedUser()) {
            String username = remembered.get(getCookie().get().getValue());
            VaadinSession.getCurrent().setAttribute(USERNAME_ATTRIBUTE, username);
            return true;
        }

        return false;
    }

    public static boolean isRememberedUser() {
        Optional<Cookie> cookie = getCookie();
        return cookie.isPresent() && remembered.containsKey(cookie.get().getValue());
    }

    public static String getAuthenticatedUsername() {
        return (String) VaadinSession.getCurrent().getAttribute(USERNAME_ATTRIBUTE);
    }

    public static void logout() {
        removeRememberedUser();
        VaadinService.getCurrentRequest().getWrappedSession().invalidate();
        Page.getCurrent().setLocation("");
    }

    private static void removeRememberedUser() {
        Optional<Cookie> cookie = getCookie();

        if (cookie.isPresent()) {
            remembered.remove(cookie.get().getValue());
            saveCookie("", 0);
        }
    }

    private static void rememberUser(String username) {
        String randomKey = new BigInteger(130, random).toString(32);
        remembered.put(randomKey, username);
        saveCookie(randomKey, 60 * 60 * 24 * COOKIE_MAX_AGE_DAYS);
    }

    private static void saveCookie(String value, int age) {
        Cookie cookie = new Cookie(COOKIE_NAME, value);
        cookie.setPath("/");
        cookie.setMaxAge(age);
        VaadinService.getCurrentResponse().addCookie(cookie);
    }

    private static Optional<Cookie> getCookie() {
        Cookie[] cookies = VaadinService.getCurrentRequest().getCookies();
        return Arrays.stream(cookies)
                .filter(c -> COOKIE_NAME.equals(c.getName()))
                .findFirst();
    }

}
