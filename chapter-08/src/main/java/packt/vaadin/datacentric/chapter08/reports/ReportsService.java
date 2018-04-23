package packt.vaadin.datacentric.chapter08.reports;

import org.apache.ibatis.session.SqlSession;
import packt.vaadin.datacentric.chapter08.config.MyBatisService;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Alejandro Duarte
 */
public class ReportsService {

    public static List<CallDto> lastHourCalls() {
        try (SqlSession session = MyBatisService.getSqlSessionFactory().openSession()) {
            LocalDateTime startOfHour = LocalDateTime.now().minusHours(1);
            ReportsMapper mapper = session.getMapper(ReportsMapper.class);
            return mapper.findCallsBefore(startOfHour);
        }
    }

    public static List<CapacityDto> currentYearCapacity() {
        try (SqlSession session = MyBatisService.getSqlSessionFactory().openSession()) {
            LocalDateTime start = LocalDateTime.now().withDayOfYear(1).withHour(0).withMinute(0).withSecond(0);
            LocalDateTime end = LocalDateTime.now();
            ReportsMapper mapper = session.getMapper(ReportsMapper.class);
            return mapper.countCallsBetween(start, end);
        }
    }

    public static List<ClientCountDto> countYearCallsByClient() {
        try (SqlSession session = MyBatisService.getSqlSessionFactory().openSession()) {
            LocalDateTime start = LocalDateTime.now().withDayOfYear(1).withHour(0).withMinute(0).withSecond(0);
            LocalDateTime end = LocalDateTime.now();
            ReportsMapper mapper = session.getMapper(ReportsMapper.class);
            return mapper.countCallsByClientBetween(start, end);
        }
    }

}
