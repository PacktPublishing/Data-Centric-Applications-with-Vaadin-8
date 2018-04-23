package packt.vaadin.datacentric.chapter07.ui;

import com.vaadin.ui.Composite;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.VerticalLayout;
import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.form.impl.field.provider.CheckBoxGroupProvider;
import org.vaadin.crudui.form.impl.field.provider.ComboBoxProvider;
import org.vaadin.crudui.layout.impl.HorizontalSplitCrudLayout;
import packt.vaadin.datacentric.chapter07.domain.RoleRepository;
import packt.vaadin.datacentric.chapter07.domain.User;
import packt.vaadin.datacentric.chapter07.domain.UserRepository;

/**
 * @author Alejandro Duarte
 */
public class CrudAddOn extends Composite {

    private GridCrud<User> crud = new GridCrud<>(User.class, new HorizontalSplitCrudLayout());

    public CrudAddOn() {
        initLayout();
        initBehavior();
    }

    private void initLayout() {
        crud.getGrid().setColumns("firstName", "lastName", "email", "mainRole");
        crud.getCrudFormFactory().setVisibleProperties("firstName", "lastName", "email", "password", "roles", "mainRole", "blocked");

        crud.getCrudFormFactory().setFieldType("password", PasswordField.class);
        crud.getCrudFormFactory().setFieldProvider("roles", new CheckBoxGroupProvider<>(RoleRepository.findAll()));
        crud.getCrudFormFactory().setFieldProvider("mainRole", new ComboBoxProvider<>("Main Role", RoleRepository.findAll()));

        VerticalLayout layout = new VerticalLayout(crud);
        setCompositionRoot(layout);
        setSizeFull();
    }

    private void initBehavior() {
        crud.setFindAllOperation(() -> UserRepository.findAll());
        crud.setAddOperation(user -> UserRepository.save(user));
        crud.setUpdateOperation(user -> UserRepository.save(user));
        crud.setDeleteOperation(user -> UserRepository.delete(user));
        crud.getCrudFormFactory().setUseBeanValidation(true);
    }

}
