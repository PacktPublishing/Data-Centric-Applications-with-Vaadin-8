package packt.vaadin.datacentric.chapter09.ui;

import com.vaadin.ui.Button;
import com.vaadin.ui.Composite;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import packt.vaadin.datacentric.chapter09.domain.Call;
import packt.vaadin.datacentric.chapter09.domain.CallRepository;

import java.util.HashMap;
import java.util.List;

/**
 * @author Alejandro Duarte.
 */
public class LazyLoadingVerticalLayout extends Composite {

    private CssLayout content = new CssLayout();
    private Button button = new Button("Load more...");

    private int offset;
    private int pageSize;

    public LazyLoadingVerticalLayout(int pageSize) {
        this.pageSize = pageSize;

        button.setStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);

        VerticalLayout mainLayout = new VerticalLayout(content, button);
        setCompositionRoot(mainLayout);

        button.addClickListener(e -> loadMore());
        loadMore();
    }

    public void loadMore() {
        List<Call> calls = CallRepository.find(offset, pageSize, "", new HashMap<>());

        if (calls.size() < pageSize) {
            button.setVisible(false);
        }

        calls.stream()
                .map(call -> new Label(call.toString()))
                .forEach(content::addComponent);

        offset += pageSize;
    }

}
