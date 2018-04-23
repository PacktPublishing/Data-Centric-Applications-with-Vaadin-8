package packt.vaadin.datacentric.chapter08.ui;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Title;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.StreamResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * @author Alejandro Duarte
 */
@Push
@Title("Report Viewer")
public class VaadinUI extends UI {

    private HorizontalLayout header = new HorizontalLayout();
    private Panel panel = new Panel();
    private MenuBar.MenuItem annualLegalReportItem;
    private AnnualLegalReport annualLegalReport;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        MenuBar menuBar = new MenuBar();
        menuBar.addStyleName(ValoTheme.MENUBAR_BORDERLESS);
        MenuBar.MenuItem reportsMenuItem = menuBar.addItem("Reports", VaadinIcons.FILE_TABLE, null);
        reportsMenuItem.addItem("Worldwide Calls in the Last Hour", VaadinIcons.PHONE_LANDLINE,
                i -> showLastHourCallReport());
        reportsMenuItem.addItem("Monthly Capacity Report", VaadinIcons.BAR_CHART_H,
                i -> showMonthlyCapacityReport());
        annualLegalReportItem = reportsMenuItem.addItem("Annual Legal Report", VaadinIcons.FILE_TEXT_O,
                i -> generateAnnualLegalReport());

        header.addComponents(menuBar);

        panel.addStyleName(ValoTheme.PANEL_WELL);

        VerticalLayout mainLayout = new VerticalLayout(header);
        mainLayout.addComponentsAndExpand(panel);
        setContent(mainLayout);
    }

    private void showLastHourCallReport() {
        panel.setContent(new VerticalLayout(new LastHourCallReport()));
    }

    private void showMonthlyCapacityReport() {
        panel.setContent(new VerticalLayout(new MonthlyCapacityReport()));
    }

    private void generateAnnualLegalReport() {
        Notification.show("Report generation started",
                "You'll be notified once the report is ready.", Notification.Type.TRAY_NOTIFICATION);
        annualLegalReportItem.setEnabled(false);

        new Thread(() -> {
            annualLegalReport = new AnnualLegalReport();
            ByteArrayOutputStream outputStream = annualLegalReport.getOutputStream();
            ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
            access(() -> {
                Button button = new Button("Download Annual Legal Report", VaadinIcons.DOWNLOAD_ALT);
                header.addComponent(button);

                FileDownloader downloader = new FileDownloader(new StreamResource(() -> {
                    header.removeComponent(button);
                    annualLegalReportItem.setEnabled(true);
                    return inputStream;
                }, "annual-legal-report.pdf"));
                downloader.extend(button);

                Notification.show("Report ready for download", Notification.Type.TRAY_NOTIFICATION);
            });
        }).start();
    }

}
