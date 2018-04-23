package packt.vaadin.datacentric.chapter04;

import com.vaadin.ui.Button;
import com.vaadin.ui.Composite;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * @author Alejandro Duarte
 */
public class PrivateComponent extends Composite {

    public PrivateComponent() {
        Label label = new Label("User: " + AuthService.getAuthenticatedUsername());
        Button logOutButton = new Button(Messages.get("auth.logout"), e -> logoutClicked());
        setCompositionRoot(new VerticalLayout(label, logOutButton));
    }

    private void logoutClicked() {
        AuthService.logout();
    }

}
