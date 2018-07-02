package packt.vaadin.datacentric.chapter05.jdbc;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.sql.SQLException;

/**
 * @author Alejandro Duarte
 */
@Route("")
public class VaadinUI extends Composite<VerticalLayout> {

    public VaadinUI() {
        try {
            DatabaseService.findAllMessages().forEach(
                    m -> getContent().add(new Span(m))
            );

        } catch (SQLException e) {
            Notification.show("Database error. See the server's log.");
            e.printStackTrace();
        }
    }

}
