package packt.vaadin.datacentric.chapter02.api;

import com.vaadin.server.SerializableConsumer;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Composite;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Alejandro Duarte
 */
public class TabBasedApplicationLayout extends Composite implements ApplicationLayout {

    private VerticalLayout mainLayout = new VerticalLayout();
    private HorizontalLayout header = new HorizontalLayout();
    private HorizontalSplitPanel splitPanel = new HorizontalSplitPanel();
    private VerticalLayout menuLayout = new VerticalLayout();
    private TabSheet tabSheet = new TabSheet();

    private Collection<WorkingAreaComponent> workingAreaComponents = new HashSet<>();
    private Collection<String> menuButtonStyles = new HashSet<>();

    public TabBasedApplicationLayout(String caption) {
        Label captionLabel = new Label(caption);
        captionLabel.setStyleName("h1");

        header.addComponent(captionLabel);
        header.setComponentAlignment(captionLabel, Alignment.MIDDLE_LEFT);
        header.setExpandRatio(captionLabel, 1);
        header.setMargin(new MarginInfo(false, true));
        header.setWidth("100%");

        tabSheet.setSizeFull();
        tabSheet.setCloseHandler((tabsheet, tabContent) -> closeTab(tabContent));

        splitPanel.setFirstComponent(menuLayout);
        splitPanel.setSecondComponent(tabSheet);
        splitPanel.setSizeFull();
        splitPanel.setSplitPosition(20, Unit.PERCENTAGE);

        mainLayout.setMargin(false);
        mainLayout.setSpacing(false);
        mainLayout.addComponent(header);
        mainLayout.addComponent(splitPanel);
        mainLayout.setExpandRatio(splitPanel, 1);
        mainLayout.setSizeFull();

        setCompositionRoot(mainLayout);
        setSizeFull();
    }

    @Override
    public void addHeaderComponent(Component component) {
        component.setWidth(null);
        header.addComponent(component);
        header.setComponentAlignment(component, Alignment.MIDDLE_RIGHT);
    }

    @Override
    public void addWorkingAreaComponent(WorkingAreaComponent component) {
        addWorkingAreaComponent(component, true);
    }

    public void addWorkingAreaComponent(WorkingAreaComponent component, boolean closable) {
        if (!workingAreaComponents.contains(component)) {
            TabSheet.Tab tab = tabSheet.addTab(component.getComponent(), component.getCaption());
            tab.setClosable(closable);
            tabSheet.setSelectedTab(tab);
            workingAreaComponents.add(component);
        } else {
            showComponent(component.getCaption());
        }
    }

    @Override
    public Collection<Component> getHeaderComponents() {
        return IntStream.range(0, header.getComponentCount())
                .mapToObj(header::getComponent)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<WorkingAreaComponent> getWorkingAreaComponents() {
        return workingAreaComponents;
    }

    @Override
    public void addMenuOption(MenuOption menuOption, SerializableConsumer<MenuOption> clickListener) {
        Button button = new Button(menuOption.getCaption(), event -> clickListener.accept(menuOption));
        menuButtonStyles.forEach(button::addStyleName);
        menuLayout.addComponent(button);
    }

    public void closeTab(Component tabContent) {
        List<WorkingAreaComponent> toRemove = workingAreaComponents.stream()
                .filter(component -> component.getComponent().equals(tabContent))
                .collect(Collectors.toList());

        workingAreaComponents.removeAll(toRemove);
        tabSheet.removeTab(tabSheet.getTab(tabContent));
    }

    public void showComponent(String caption) {
        IntStream.range(0, tabSheet.getComponentCount())
                .mapToObj(tabSheet::getTab)
                .filter(tab -> tab.getCaption().equals(caption))
                .forEach(tabSheet::setSelectedTab);
    }

    public void setHeaderStyleName(String styleName) {
        header.setStyleName(styleName);
    }

    public void addHeaderStyleName(String styleName) {
        header.addStyleName(styleName);
    }

    public void setMenuButtonsStyleName(String styleName) {
        menuButtonStyles.clear();
        menuButtonStyles.add(styleName);
        updateMenuButtonsStyle(styleName, Component::setStyleName);
    }

    public void addMenuButtonsStyleName(String styleName) {
        menuButtonStyles.add(styleName);
        updateMenuButtonsStyle(styleName, Component::addStyleName);
    }

    private void updateMenuButtonsStyle(String styleName, BiConsumer<Component, String> setOrAddStyleMethod) {
        IntStream.range(0, menuLayout.getComponentCount())
                .mapToObj(menuLayout::getComponent)
                .forEach(component -> setOrAddStyleMethod.accept(component, styleName));
    }

}
