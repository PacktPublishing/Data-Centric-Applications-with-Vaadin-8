package packt.vaadin.datacentric.chapter02.api;


import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.function.SerializableConsumer;
import org.vaadin.tabs.PagedTabs;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

/**
 * @author Alejandro Duarte
 */
public class TabBasedApplicationLayout extends Composite<VerticalLayout> implements ApplicationLayout {

    private VerticalLayout mainLayout = getContent();
    private HorizontalLayout header = new HorizontalLayout();
    private SplitLayout splitLayout = new SplitLayout();
    private VerticalLayout menuLayout = new VerticalLayout();
    private PagedTabs tabs = new PagedTabs();

    private HashMap<String, WorkingAreaComponent> workingAreaComponents = new HashMap<>();
    private HashMap<Tab, WorkingAreaComponent> tabsToWorkingAreaComponents = new HashMap<>();
    private Collection<String> menuButtonStyles = new HashSet<>();

    public TabBasedApplicationLayout(String caption) {
        H1 captionComponent = new H1(caption);

        header.add(captionComponent);
        header.setAlignSelf(FlexComponent.Alignment.START, captionComponent);
        header.setVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidth("100%");

        tabs.setSizeFull();
        tabs.setTabCloseListener(tab -> {
            workingAreaComponents.remove(tabsToWorkingAreaComponents.get(tab).getCaption());
            tabsToWorkingAreaComponents.remove(tab);
        });

        splitLayout.addToPrimary(menuLayout);
        splitLayout.addToSecondary(tabs);
        splitLayout.setSizeFull();
        menuLayout.setWidth("20%");

        mainLayout.setMargin(false);
        mainLayout.setSpacing(false);
        mainLayout.add(header, splitLayout);
        mainLayout.expand(splitLayout);
        mainLayout.setSizeFull();

        getContent().setSizeFull();
        getContent().setMargin(false);
        getContent().setPadding(false);
    }

    @Override
    public void addHeaderComponent(Component component) {
        component.getElement().getStyle().set("width", null);
        header.add(component);
        header.setAlignSelf(FlexComponent.Alignment.END, component);
        header.setVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
    }

    @Override
    public void addWorkingAreaComponent(WorkingAreaComponent component) {
        addWorkingAreaComponent(component, true);
    }

    public void addWorkingAreaComponent(WorkingAreaComponent component, boolean closable) {
        if (!workingAreaComponents.values().contains(component)) {
            Tab tab = tabs.add(component.getComponent(), component.getCaption(), closable);
            tabs.select(tab);
            workingAreaComponents.put(component.getCaption(), component);
            tabsToWorkingAreaComponents.put(tab, component);
        }
    }

    @Override
    public Collection<Component> getHeaderComponents() {
        return header.getChildren().collect(Collectors.toList());
    }

    @Override
    public Collection<WorkingAreaComponent> getWorkingAreaComponents() {
        return workingAreaComponents.values();
    }

    @Override
    public void addMenuOption(MenuOption menuOption, SerializableConsumer<MenuOption> clickListener) {
        Button button = new Button(menuOption.getCaption(), event -> clickListener.accept(menuOption));
        button.setWidth("100%");
        menuButtonStyles.forEach(button::addClassName);
        menuLayout.add(button);
    }

    public void setHeaderStyleName(String styleName) {
        header.setClassName(styleName);
    }

    public void addHeaderStyleName(String styleName) {
        header.addClassName(styleName);
    }

    public void setMenuButtonsStyleName(String styleName) {
        menuButtonStyles.clear();
        menuButtonStyles.add(styleName);
        updateMenuButtonsStyle(styleName, HasStyle::setClassName);
    }

    public void addMenuButtonsStyleName(String styleName) {
        menuButtonStyles.add(styleName);
        updateMenuButtonsStyle(styleName, HasStyle::addClassName);
    }

    private void updateMenuButtonsStyle(String styleName, BiConsumer<HasStyle, String> setOrAddStyleMethod) {
        menuLayout.getChildren()
                .forEach(component -> setOrAddStyleMethod.accept((HasStyle) component, styleName));
    }

}
