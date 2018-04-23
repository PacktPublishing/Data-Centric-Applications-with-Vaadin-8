package packt.vaadin.datacentric.chapter08.ui;

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.AutoText;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Composite;
import com.vaadin.ui.Label;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import packt.vaadin.datacentric.chapter08.domain.City;
import packt.vaadin.datacentric.chapter08.domain.Status;
import packt.vaadin.datacentric.chapter08.reports.CallDto;
import packt.vaadin.datacentric.chapter08.reports.ReportsService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author Alejandro Duarte
 */
public class LastHourCallReport extends Composite {

    public LastHourCallReport() {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            DynamicReport report = new FastReportBuilder()
                    .setTitle("Worldwide Calls in the Last Hour")
                    .addAutoText("CONFIDENTIAL", AutoText.POSITION_HEADER, AutoText.ALIGMENT_LEFT, 200, new Style())
                    .addAutoText(LocalDateTime.now().toString(), AutoText.POSITION_HEADER, AutoText.ALIGNMENT_RIGHT, 200, new Style())
                    .setUseFullPageWidth(true)
                    .setWhenNoData("(no calls)", new Style())
                    .setPrintBackgroundOnOddRows(true)
                    .addColumn(ColumnBuilder.getNew()
                            .setColumnProperty("city", City.class)
                            .setTitle("City")
                            .build())
                    .addColumn(ColumnBuilder.getNew()
                            .setColumnProperty("client", String.class)
                            .setTitle("Client")
                            .build())
                    .addColumn(ColumnBuilder.getNew()
                            .setColumnProperty("phoneNumber", String.class)
                            .setTitle("Phone number")
                            .build())
                    .addColumn(ColumnBuilder.getNew()
                            .setColumnProperty("startTime", LocalDateTime.class)
                            .setTitle("Date")
                            .setTextFormatter(DateTimeFormatter.ISO_DATE.toFormat())
                            .build())
                    .addColumn(ColumnBuilder.getNew()
                            .setColumnProperty("startTime", LocalDateTime.class)
                            .setTextFormatter(DateTimeFormatter.ISO_LOCAL_TIME.toFormat())
                            .setTitle("Start time")
                            .build())
                    .addColumn(ColumnBuilder.getNew()
                            .setColumnProperty("duration", Integer.class)
                            .setTitle("Minutes")
                            .build())
                    .addColumn(ColumnBuilder.getNew()
                            .setColumnProperty("status", Status.class)
                            .setTitle("Status").build())
                    .build();

            List<CallDto> calls = ReportsService.lastHourCalls();
            JasperPrint print = DynamicJasperHelper.generateJasperPrint(report, new ClassicLayoutManager(), calls);

            HtmlExporter exporter = new HtmlExporter();
            exporter.setExporterOutput(new SimpleHtmlExporterOutput(outputStream));
            exporter.setExporterInput(new SimpleExporterInput(print));
            exporter.exportReport();

            outputStream.flush();
            Label htmlLabel = new Label("", ContentMode.HTML);
            htmlLabel.setValue(outputStream.toString("UTF-8"));
            setCompositionRoot(htmlLabel);

        } catch (JRException | IOException e) {
            throw new RuntimeException(e);
        }
    }

}
