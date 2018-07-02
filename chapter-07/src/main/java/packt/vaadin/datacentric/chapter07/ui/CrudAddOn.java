package packt.vaadin.datacentric.chapter07.ui;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
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
public class CrudAddOn extends Composite<VerticalLayout> {

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

        getContent().add(crud);
        getContent().setSizeFull();
        getContent().setMargin(false);
        getContent().setPadding(false);
    }

    private void initBehavior() {
        crud.setFindAllOperation(() -> UserRepository.findAll());
        crud.setAddOperation(user -> UserRepository.save(user));
        crud.setUpdateOperation(user -> UserRepository.save(user));
        crud.setDeleteOperation(user -> UserRepository.delete(user));
        crud.getCrudFormFactory().setUseBeanValidation(true);
    }

}
