package packt.vaadin.datacentric.chapter08.ui;

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.AutoText;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import packt.vaadin.datacentric.chapter08.domain.City;
import packt.vaadin.datacentric.chapter08.reports.ClientCountDto;
import packt.vaadin.datacentric.chapter08.reports.ReportsService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Alejandro Duarte
 */
public class AnnualLegalReport {

    private ByteArrayOutputStream outputStream;

    public AnnualLegalReport() {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            DynamicReportBuilder reportBuilder = new FastReportBuilder()
                    .setUseFullPageWidth(true)
                    .setTitle("Annual Legal Report")
                    .setWhenNoData("(no data)", new Style())
                    .addAutoText(LocalDateTime.now().toString(), AutoText.POSITION_HEADER, AutoText.ALIGNMENT_RIGHT, 200, new Style())
                    .addColumn(ColumnBuilder.getNew()
                            .setColumnProperty("client", String.class)
                            .setTitle("Client")
                            .build())
                    .addColumn(ColumnBuilder.getNew()
                            .setColumnProperty("city", City.class)
                            .setTitle("City")
                            .build())
                    .addColumn(ColumnBuilder.getNew()
                            .setColumnProperty("phoneNumber", String.class)
                            .setTitle("Phone number")
                            .build())
                    .addColumn(ColumnBuilder.getNew()
                            .setColumnProperty("calls", Integer.class)
                            .setTitle("Call count")
                            .build());

            DynamicReport report = reportBuilder.build();

            List<ClientCountDto> clients = ReportsService.countYearCallsByClient();
            JasperPrint print = DynamicJasperHelper.generateJasperPrint(report, new ClassicLayoutManager(), clients);

            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
            exporter.setExporterInput(new SimpleExporterInput(print));
            exporter.exportReport();

            outputStream.flush();
            this.outputStream = outputStream;

        } catch (JRException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ByteArrayOutputStream getOutputStream() {
        return outputStream;
    }

}
