package packt.vaadin.datacentric.chapter08.ui;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.data.renderer.IconRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * @author Alejandro Duarte
 */
@Push
@PageTitle("Report Viewer")
@Route("")
public class VaadinUI extends Composite<VerticalLayout> {

    private HorizontalLayout header = new HorizontalLayout();
    private VerticalLayout panel = new VerticalLayout();
    private AnnualLegalReport annualLegalReport;
    private boolean generatingAnnualReport;

    @Data
    @AllArgsConstructor
    private class ReportItem {
        private String name;
        private Runnable reportGenerator;
        private Icon icon;
    }

    public VaadinUI() {
        // No suitable menu component is available in Vaadin 10.0.1

        ComboBox<ReportItem> menu = new ComboBox<>();
        menu.setPlaceholder("Reports");
        menu.setWidth("400px");
        menu.setRenderer(new IconRenderer<>(i -> i.getIcon(), ReportItem::getName));
        menu.setItemLabelGenerator(ReportItem::getName);
        menu.setItems(new ReportItem("Worldwide Calls in the Last Hour", this::showLastHourCallReport, VaadinIcon.PHONE_LANDLINE.create()),
                new ReportItem("Monthly Capacity Report", this::showMonthlyCapacityReport, VaadinIcon.BAR_CHART_H.create()),
                new ReportItem("Annual Legal Report", this::generateAnnualLegalReport, VaadinIcon.FILE_TEXT_O.create()));
        menu.addValueChangeListener(e -> {
            ReportItem item = e.getValue();
            if (item != null) {
                item.getReportGenerator().run();
                menu.clear();
            }
        });

        header.setWidth("100%");
        header.add(menu);
        header.setMargin(false);
        header.setPadding(false);

        panel.setMargin(false);
        panel.setPadding(false);

        getContent().add(header, panel);
        getContent().expand(panel);
    }

    private void showLastHourCallReport() {
        panel.removeAll();
        panel.add(new LastHourCallReport());
    }

    private void showMonthlyCapacityReport() {
        panel.removeAll();
        panel.add(new MonthlyCapacityReport());
    }

    private void generateAnnualLegalReport() {
        if (generatingAnnualReport) {
            Notification.show("The report was already requested or is ready for download");
            return;
        }
        generatingAnnualReport = true;

        Notification notification = new Notification(
                new H2("Report generation started"),
                new Span("You'll be notified once the report is ready."));

        notification.setPosition(Notification.Position.MIDDLE);
        notification.setDuration(5000);
        notification.open();

        UI ui = UI.getCurrent();

        new Thread(() -> {
            annualLegalReport = new AnnualLegalReport();
            ByteArrayOutputStream outputStream = annualLegalReport.getOutputStream();
            byte[] buf = outputStream.toByteArray();
            ByteArrayInputStream inputStream = new ByteArrayInputStream(buf);

            Span downloadLink = new Span();

            ui.access(() -> {
                StreamResource streamResource = new StreamResource("annual-legal-report.pdf", () -> {
                    header.remove(downloadLink);
                    generatingAnnualReport = false;
                    return inputStream;
                });
                Anchor anchor = new Anchor(streamResource, "Download Annual Legal Report");
                anchor.getElement().setAttribute("download", true);

                Icon icon = VaadinIcon.DOWNLOAD_ALT.create();

                downloadLink.add(icon, anchor);
                header.add(downloadLink);

                Notification.show("Report ready for download");
            });
        }).start();
    }

}
