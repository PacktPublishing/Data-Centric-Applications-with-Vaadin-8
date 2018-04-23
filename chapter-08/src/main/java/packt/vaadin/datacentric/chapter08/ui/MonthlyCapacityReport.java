package packt.vaadin.datacentric.chapter08.ui;

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.AutoText;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.chart.builder.DJBar3DChartBuilder;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Composite;
import com.vaadin.ui.Label;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.j2ee.servlets.ImageServlet;
import net.sf.jasperreports.web.util.WebHtmlResourceHandler;
import packt.vaadin.datacentric.chapter08.reports.CapacityDto;
import packt.vaadin.datacentric.chapter08.reports.ReportsService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Alejandro Duarte
 */
public class MonthlyCapacityReport extends Composite {

    public MonthlyCapacityReport() {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            AbstractColumn monthColumn;
            AbstractColumn callsColumn;

            DynamicReport report = new FastReportBuilder()
                    .setUseFullPageWidth(true)
                    .setTitle("Monthly Capacity Report")
                    .setWhenNoData("(no data)", new Style())
                    .addAutoText("CONFIDENTIAL", AutoText.POSITION_HEADER, AutoText.ALIGMENT_LEFT, 200, new Style())
                    .addAutoText(LocalDateTime.now().toString(), AutoText.POSITION_HEADER, AutoText.ALIGNMENT_RIGHT, 200, new Style())
                    .addColumn(monthColumn = ColumnBuilder.getNew()
                            .setColumnProperty("monthName", String.class)
                            .setTitle("Month")
                            .build())
                    .addColumn(callsColumn = ColumnBuilder.getNew()
                            .setColumnProperty("calls", Integer.class)
                            .setTitle("Calls")
                            .build())
                    .addChart(new DJBar3DChartBuilder()
                            .setCategory((PropertyColumn) monthColumn)
                            .addSerie(callsColumn)
                            .build())
                    .build();

            List<CapacityDto> months = ReportsService.currentYearCapacity();
            JasperPrint print = DynamicJasperHelper.generateJasperPrint(report, new ClassicLayoutManager(), months);
            VaadinSession.getCurrent().getSession().setAttribute(ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, print);

            SimpleHtmlExporterOutput exporterOutput = new SimpleHtmlExporterOutput(outputStream);
            exporterOutput.setImageHandler(new WebHtmlResourceHandler("image?image={0}"));


            HtmlExporter exporter = new HtmlExporter();
            exporter.setExporterOutput(exporterOutput);
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
