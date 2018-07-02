package packt.vaadin.datacentric.chapter06.jooq;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import packt.vaadin.chapter06.jooq.public_.tables.records.MessagesRecord;

/**
 * @author Alejandro Duarte
 */
@Route("")
public class VaadinUI extends Composite<VerticalLayout> {

    private Grid<MessagesRecord> grid;
    private TextField textField;
    private Button button;

    public VaadinUI() {
        initLayout();
        initBehavior();
    }

    private void initLayout() {
        grid = new Grid<>(MessagesRecord.class);
        grid.setSizeFull();
        grid.setColumns("id", "content");
        grid.getColumnByKey("id").setFlexGrow(0);
        grid.getColumnByKey("content").setFlexGrow(1);

        textField = new TextField();
        textField.setPlaceholder("Enter a new message...");
        textField.setSizeFull();

        button = new Button("Save");

        HorizontalLayout formLayout = new HorizontalLayout(textField, button);
        formLayout.setWidth("100%");
        formLayout.expand(textField);

        getContent().add(grid, formLayout);
        getContent().setWidth("600px");
        getContent().setSizeFull();
    }

    private void initBehavior() {
        button.addClickListener(e -> saveCurrentMessage());
        update();
    }

    private void update() {
        grid.setItems(MessageRepository.findAll());
        textField.clear();
        textField.focus();
    }

    private void saveCurrentMessage() {
        MessagesRecord message = new MessagesRecord();
        message.setContent(textField.getValue());
        MessageRepository.save(message);

        update();
        grid.select(message);
    }

}
