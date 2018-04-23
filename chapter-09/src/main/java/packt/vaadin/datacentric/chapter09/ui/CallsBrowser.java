package packt.vaadin.datacentric.chapter09.ui;

import com.vaadin.data.provider.DataProvider;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Composite;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import packt.vaadin.datacentric.chapter09.domain.Call;
import packt.vaadin.datacentric.chapter09.domain.CallRepository;

/**
 * @author Alejandro Duarte
 */
public class CallsBrowser extends Composite {

    public CallsBrowser() {
        TextField filter = new TextField();
        filter.setPlaceholder("Client / Phone / City");
        filter.focus();

        Button search = new Button(VaadinIcons.SEARCH);
        search.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        Button clear = new Button(VaadinIcons.CLOSE_SMALL);

        CssLayout filterLayout = new CssLayout(filter, search, clear);
        filterLayout.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

        Label countLabel = new Label();
        countLabel.addStyleNames(ValoTheme.LABEL_LIGHT, ValoTheme.LABEL_SMALL);

        HorizontalLayout headerLayout = new HorizontalLayout(filterLayout, countLabel);
        headerLayout.setComponentAlignment(countLabel, Alignment.MIDDLE_LEFT);

        DataProvider<Call, Void> dataProvider = DataProvider.fromFilteringCallbacks(
                query -> CallRepository.find(query.getOffset(), query.getLimit(), filter.getValue(), DataUtils.getOrderMap(query)).stream(),
                query -> {
                    int count = CallRepository.count(filter.getValue());
                    countLabel.setValue(count + " calls found");
                    return count;
                }
        );

        Grid<Call> grid = new Grid<>(Call.class);
        grid.setColumns("id", "client", "phoneNumber", "city", "startTime", "duration", "status");
        grid.setColumnReorderingAllowed(true);
        grid.setDataProvider(dataProvider);
        grid.setSizeFull();

        search.addClickListener(e -> dataProvider.refreshAll());

        clear.addClickListener(e -> {
            filter.clear();
            dataProvider.refreshAll();
        });

        VerticalLayout mainLayout = new VerticalLayout(headerLayout);
        mainLayout.setMargin(false);
        mainLayout.addComponentsAndExpand(grid);
        setCompositionRoot(mainLayout);
    }

}
