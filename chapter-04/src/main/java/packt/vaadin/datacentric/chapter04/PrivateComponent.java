package packt.vaadin.datacentric.chapter04;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

/**
 * @author Alejandro Duarte
 */
public class PrivateComponent extends Composite<VerticalLayout> {

    public PrivateComponent() {
        Span span = new Span("User: " + AuthService.getAuthenticatedUsername());
        Button logOutButton = new Button(Messages.get("auth.logout"), e -> logoutClicked());
        getContent().add(span, logOutButton);
    }

    private void logoutClicked() {
        AuthService.logout();
    }

}
