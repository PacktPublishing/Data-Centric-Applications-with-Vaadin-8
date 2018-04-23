package packt.vaadin.datacentric.chapter09.config;

import packt.vaadin.datacentric.chapter09.domain.Call;
import packt.vaadin.datacentric.chapter09.domain.CallRepository;
import packt.vaadin.datacentric.chapter09.domain.City;
import packt.vaadin.datacentric.chapter09.domain.Status;

import java.time.LocalDateTime;
import java.util.Random;

/**
 * @author Alejandro Duarte
 */
public class DataGenerator {

    private static final String[] firstNames = {"John", "Peter", "Alice", "Joshua", "Mike", "Olivia", "Nina", "Alex", "Rita", "Dan", "Nicole", "Henrik", "Marian", "Edgar", "Juan"};
    private static final String[] lastNames = {"Smith", "Gordon", "Simpson", "Brown", "Clavel", "Simons", "Verne", "Scott", "Allison", "Gates", "Mattsson", "Barks", "Ross", "Schneider", "Duarte"};
    private static final Random random = new Random(System.currentTimeMillis());

    private static Thread thread = new Thread(() -> produceData());
    private static boolean stop;

    private static int callsPerMonth;
    private static int initialMonths;

    public static void start(int callsPerMonth, int initialMonths) {
        DataGenerator.callsPerMonth = callsPerMonth;
        DataGenerator.initialMonths = initialMonths;
        if (CallRepository.isEmpty()) {
            createInitialData();
        } else {
            generateFillingData();
        }

        thread.start();
    }

    public static void stop() {
        stop = true;
    }

    private static void createInitialData() {
        generateData(LocalDateTime.now().minusMonths(initialMonths), LocalDateTime.now());
    }

    private static void generateFillingData() {
        System.out.println("Fetching last call...");
        Call last = CallRepository.lastCall();
        generateData(last.getStartTime(), LocalDateTime.now());
    }

    private static void generateData(LocalDateTime start, LocalDateTime end) {
        long incrementInSeconds = 30l * 24l * 60l * 60l / callsPerMonth;
        LocalDateTime time = start.plusSeconds(incrementInSeconds);

        System.out.println("Generating data from " + start + " to " + end + " ...");

        int count = 0;
        while (time.isBefore(end)) {
            saveNewRandomCall(time);
            time = time.plusSeconds(incrementInSeconds);
            count++;
        }

        System.out.println(count + " calls created.");
    }

    private static void produceData() {
        try {
            while (!stop) {
                Thread.sleep(30l * 24l * 60l * 60l * 1000l / callsPerMonth);
                saveNewRandomCall(LocalDateTime.now());
                System.out.println("New call generated.");
            }
        } catch (InterruptedException ignored) {
        }
    }

    public static void saveNewRandomCall(LocalDateTime startTime) {
        Call call = new Call();
        call.setClient(firstNames[random.nextInt(firstNames.length)] + " " + lastNames[random.nextInt(lastNames.length)]);
        call.setPhoneNumber("555 01" + random.nextInt(10) + " " + random.nextInt(10) + random.nextInt(10) + random.nextInt(10));
        call.setCity(City.values()[random.nextInt(City.values().length)]);
        call.setStartTime(startTime);
        call.setStatus(Status.values()[random.nextInt(Status.values().length)]);
        call.setDuration(call.getStatus().equals(Status.MISSED) ? 0 : random.nextInt(21));
        CallRepository.save(call);
    }

}
