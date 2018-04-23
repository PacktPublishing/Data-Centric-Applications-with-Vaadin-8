package packt.vaadin.datacentric.chapter07.ui;

import com.vaadin.data.Binder;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Composite;
import com.vaadin.ui.Grid;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.TextRenderer;
import packt.vaadin.datacentric.chapter07.domain.User;
import packt.vaadin.datacentric.chapter07.domain.UserRepository;

/**
 * @author Alejandro Duarte
 */
public class EditableGridCrud extends Composite {

    private Grid<User> grid = new Grid<>();
    private Button addButton = new Button(null, VaadinIcons.PLUS);

    public EditableGridCrud() {
        initLayout();
        initBehavior();
        refresh();
    }

    private void initLayout() {
        grid.setSizeFull();
        VerticalLayout layout = new VerticalLayout(addButton);
        layout.addComponentsAndExpand(grid);

        setCompositionRoot(layout);
        setSizeFull();
    }

    private void initBehavior() {
        addButton.addClickListener(e -> addClicked());

        Binder<User> binder = grid.getEditor().getBinder();

        grid.addColumn(User::getFirstName)
                .setCaption("First Name")
                .setEditorBinding(binder
                        .forField(new TextField())
                        .withNullRepresentation("")
                        .withValidator(new BeanValidator(User.class, "firstName"))
                        .bind(User::getFirstName, User::setFirstName));

        grid.addColumn(User::getLastName)
                .setCaption("Last Name")
                .setEditorBinding(binder
                        .forField(new TextField())
                        .withNullRepresentation("")
                        .withValidator(new BeanValidator(User.class, "lastName"))
                        .bind(User::getLastName, User::setLastName));

        grid.addColumn(User::getEmail)
                .setCaption("Email")
                .setEditorBinding(binder
                        .forField(new TextField())
                        .withNullRepresentation("")
                        .withValidator(new BeanValidator(User.class, "email"))
                        .bind(User::getEmail, User::setEmail));

        grid.addColumn(User::getPassword)
                .setCaption("Password")
                .setRenderer(user -> "********", new TextRenderer())
                .setEditorBinding(binder
                        .forField(new PasswordField())
                        .withNullRepresentation("")
                        .withValidator(new BeanValidator(User.class, "password"))
                        .bind(User::getPassword, User::setPassword));

        grid.addColumn(User::isBlocked)
                .setCaption("Blocked")
                .setEditorBinding(binder
                        .forField(new CheckBox())
                        .withValidator(new BeanValidator(User.class, "blocked"))
                        .bind(User::isBlocked, User::setBlocked));

        grid.getEditor().addSaveListener(e -> save(e.getBean()));
        grid.getEditor().setEnabled(true);
    }

    private void addClicked() {
        User user = new User();
        user.setFirstName("first name");
        user.setLastName("last name");
        user.setPassword("password");
        user = UserRepository.save(user);
        refresh();
        grid.getEditor().editRow(UserRepository.findAll().size() - 1);
    }

    private void save(User user) {
        UserRepository.save(user);
        refresh();
    }

    private void refresh() {
        grid.setItems(UserRepository.findAll());
    }

}
