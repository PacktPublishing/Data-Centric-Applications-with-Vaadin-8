package packt.vaadin.datacentric.chapter07.ui;

import com.vaadin.data.BeanValidationBinder;
import com.vaadin.data.ValidationException;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CheckBoxGroup;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Composite;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import packt.vaadin.datacentric.chapter07.domain.Role;
import packt.vaadin.datacentric.chapter07.domain.RoleRepository;
import packt.vaadin.datacentric.chapter07.domain.User;
import packt.vaadin.datacentric.chapter07.domain.UserRepository;

/**
 * @author Alejandro Duarte
 */
public class CustomCrud extends Composite {

    private Button refresh = new Button("", VaadinIcons.REFRESH);
    private Button add = new Button("", VaadinIcons.PLUS);
    private Button edit = new Button("", VaadinIcons.PENCIL);

    private Grid<User> grid = new Grid<>(User.class);


    private class UserFormWindow extends Window {

        private TextField firstName = new TextField("First name");
        private TextField lastName = new TextField("Last name");
        private TextField email = new TextField("Email");
        private PasswordField password = new PasswordField("Password");
        private CheckBoxGroup<Role> roles = new CheckBoxGroup<>("Roles", RoleRepository.findAll());
        private ComboBox<Role> mainRole = new ComboBox<>("Main Role", RoleRepository.findAll());
        private CheckBox blocked = new CheckBox("Blocked");

        private Button cancel = new Button("Cancel");
        private Button save = new Button("Save", VaadinIcons.CHECK);

        public UserFormWindow(String caption, User user) {
            initLayout(caption);
            initBehavior(user);
        }

        private void initLayout(String caption) {
            setCaption(caption);
            save.addStyleName(ValoTheme.BUTTON_PRIMARY);

            HorizontalLayout buttons = new HorizontalLayout(cancel, save);
            buttons.setSpacing(true);

            GridLayout formLayout = new GridLayout(3, 3, firstName, lastName, email, password, roles, mainRole, blocked);
            formLayout.setMargin(true);
            formLayout.setSpacing(true);

            VerticalLayout layout = new VerticalLayout(formLayout, buttons);
            layout.setComponentAlignment(buttons, Alignment.BOTTOM_RIGHT);
            setContent(layout);
            setModal(true);
            center();
        }

        private void initBehavior(User user) {
            BeanValidationBinder<User> binder = new BeanValidationBinder<>(User.class);
            binder.bindInstanceFields(this);
            binder.readBean(user);

            cancel.addClickListener(e -> close());
            save.addClickListener(e -> {
                try {
                    binder.validate();
                    binder.writeBean(user);
                    UserRepository.save(user);
                    close();
                    refresh();
                    Notification.show("User saved");

                } catch (ValidationException ex) {
                    Notification.show("Please fix the errors and try again");
                }
            });
        }
    }


    private class RemoveWindow extends Window {
        private Button cancel = new Button("Cancel");
        private Button delete = new Button("Delete", VaadinIcons.TRASH);

        public RemoveWindow(User user) {
            initLayout(user);
            initBehavior(user);
        }

        private void initLayout(User user) {
            setCaption("Confirm");
            Label label = new Label("Do you really want to delete the user " + user.getFirstName() + " " + user.getLastName() + "?");

            delete.addStyleName(ValoTheme.BUTTON_DANGER);
            HorizontalLayout buttons = new HorizontalLayout(cancel, delete);

            VerticalLayout layout = new VerticalLayout(label, buttons);
            layout.setComponentAlignment(buttons, Alignment.BOTTOM_RIGHT);
            setContent(layout);
        }

        private void initBehavior(User user) {
            cancel.addClickListener(e -> close());

            delete.addClickListener(e -> {
                UserRepository.delete(user);
                refresh();
                close();
            });
        }
    }


    public CustomCrud() {
        initLayout();
        initBehavior();
        refresh();
    }

    private void initLayout() {
        CssLayout header = new CssLayout(refresh, add, edit);
        header.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

        grid.setColumns("firstName", "lastName", "email", "mainRole");
        grid.addComponentColumn(user -> new Button("Delete", e -> deleteClicked(user)));
        grid.setSizeFull();

        VerticalLayout layout = new VerticalLayout(header, grid);
        layout.setExpandRatio(grid, 1);
        setCompositionRoot(layout);
        setSizeFull();
    }

    private void initBehavior() {
        grid.asSingleSelect().addValueChangeListener(e -> updateHeader());
        refresh.addClickListener(e -> refresh());
        add.addClickListener(e -> showAddWindow());
        edit.addClickListener(e -> showEditWindow());
    }

    public void refresh() {
        grid.setItems(UserRepository.findAll());
        updateHeader();
    }

    private void deleteClicked(User user) {
        showRemoveWindow(user);
        refresh();
    }

    private void updateHeader() {
        boolean selected = !grid.asSingleSelect().isEmpty();
        edit.setEnabled(selected);
    }

    private void showAddWindow() {
        UserFormWindow window = new UserFormWindow("Add", new User());
        getUI().addWindow(window);
    }

    private void showEditWindow() {
        UserFormWindow window = new UserFormWindow("Edit", grid.asSingleSelect().getValue());
        getUI().addWindow(window);
    }

    private void showRemoveWindow(User user) {
        Window window = new RemoveWindow(user);
        window.setModal(true);
        window.center();
        getUI().addWindow(window);
    }

}
