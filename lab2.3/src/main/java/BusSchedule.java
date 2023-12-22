import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class BusSchedule {
    private int busNumber;
    private String departureTime;
    private String daysOfWeek;
    private int availableSeats;
    private List<String> route;

    public BusSchedule(int busNumber, String departureTime, String daysOfWeek, int availableSeats, List<String> route) {
        this.busNumber = busNumber;
        this.departureTime = departureTime;
        this.daysOfWeek = daysOfWeek;
        this.availableSeats = availableSeats;
        this.route = route;
    }

    public int getBusNumber() {
        return busNumber;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public String getDaysOfWeek() {
        return daysOfWeek;
    }

    public List<String> getRoute() {
        return route;
    }


    // Головний метод для обробки розкладу в паралельних потоках
    public static List<BusSchedule> processScheduleInParallel(List<BusSchedule> schedule) {
        int processors = Runtime.getRuntime().availableProcessors();
        ForkJoinPool forkJoinPool = new ForkJoinPool(processors);
        return forkJoinPool.invoke(new ScheduleProcessor(schedule, 0, schedule.size()));
    }

    private static class ScheduleProcessor extends RecursiveTask<List<BusSchedule>> {
        private static final int THRESHOLD = 5;
        private List<BusSchedule> schedule;
        private int start;
        private int end;

        public ScheduleProcessor(List<BusSchedule> schedule, int start, int end) {
            this.schedule = schedule;
            this.start = start;
            this.end = end;
        }

        @Override
        protected List<BusSchedule> compute() {
            if (end - start <= THRESHOLD) {
                return processSequentially();
            } else {
                int middle = (start + end) / 2;
                ScheduleProcessor leftTask = new ScheduleProcessor(schedule, start, middle);
                ScheduleProcessor rightTask = new ScheduleProcessor(schedule, middle, end);
                invokeAll(leftTask, rightTask);

                List<BusSchedule> leftResult = leftTask.join();
                List<BusSchedule> rightResult = rightTask.join();

                return merge(leftResult, rightResult);
            }
        }

        private List<BusSchedule> processSequentially() {
            List<BusSchedule> result = new ArrayList<>();
            for (int i = start; i < end; i++) {
                result.add(schedule.get(i));
            }
            return result;
        }

        private List<BusSchedule> merge(List<BusSchedule> left, List<BusSchedule> right) {
            List<BusSchedule> result = new ArrayList<>();
            result.addAll(left);
            result.addAll(right);
            return result;
        }
    }

    public static void main(String[] args) {
        List<BusSchedule> schedule = new ArrayList<>();
        schedule.add(new BusSchedule(2, "10:30", "Tue", 15, Collections.singletonList("StationB, 11:30")));
        schedule.add(new BusSchedule(1, "08:00", "Mon", 10, Collections.singletonList("StationA, 09:00")));
        schedule.add(new BusSchedule(3, "12:45", "Wed", 5, Collections.singletonList("StationC, 13:45")));

        System.out.println("Before sorting:");
        printSchedule(schedule);

        // Сортування розкладу за номером рейсу
        Collections.sort(schedule, (bus1, bus2) -> Integer.compare(bus1.getBusNumber(), bus2.getBusNumber()));
        System.out.println("\nAfter sorting by bus number:");
        printSchedule(schedule);

        // Сортування розкладу за часом відправлення
        Collections.sort(schedule, (bus1, bus2) -> bus1.getDepartureTime().compareTo(bus2.getDepartureTime()));
        System.out.println("\nAfter sorting by departure time:");
        printSchedule(schedule);

        // Сортування розкладу за кількістю вільних місць
        Collections.sort(schedule, (bus1, bus2) -> Integer.compare(bus1.getAvailableSeats(), bus2.getAvailableSeats()));
        System.out.println("\nAfter sorting by available seats:");
        printSchedule(schedule);

        // Паралельна обробка розкладу
        long parallelStartTime = System.currentTimeMillis();
        List<BusSchedule> parallelResult = BusSchedule.processScheduleInParallel(schedule);
        long parallelEndTime = System.currentTimeMillis();
        System.out.println("\nParallel processing time: " + (parallelEndTime - parallelStartTime) + " ms");

        // Послідовна обробка розкладу
        long sequentialStartTime = System.currentTimeMillis();
        List<BusSchedule> sequentialResult = processSequentially(schedule);
        long sequentialEndTime = System.currentTimeMillis();
        System.out.println("Sequential processing time: " + (sequentialEndTime - sequentialStartTime) + " ms");
    }

    private static List<BusSchedule> processSequentially(List<BusSchedule> schedule) {
        List<BusSchedule> result = new ArrayList<>();

        for (BusSchedule bus : schedule) {
            // Логіка обробки послідовно
            int previousRoutesCount = countPreviousRoutes(bus);

            result.add(new BusSchedule(
                    bus.getBusNumber(),
                    bus.getDepartureTime(),
                    bus.getDaysOfWeek(),
                    bus.getAvailableSeats(),
                    new ArrayList<>(bus.getRoute())
            ));

            // Вивід підрахунку маршрутів до 12:00
            System.out.println("Bus " + bus.getBusNumber() + " - Previous Routes Count (Before 12:00): " + previousRoutesCount);
        }

        return result;
    }

    private static int countPreviousRoutes(BusSchedule bus) {
        // Підрахунок кількості раніше пройдених маршрутів щоб збільшити час обробки
        int count = 0;
        for (String routeInfo : bus.getRoute()) {
            String[] routeDetails = routeInfo.split(", ");
            String arrivalTime = routeDetails[1];
            if (isBeforeNoon(arrivalTime)) {
                count++;
            }
        }
        return count;
    }

    private static boolean isBeforeNoon(String time) {
        // Перевірка часу на те, чи він до 12:00
        return Integer.parseInt(time.split(":")[0]) < 12;
    }


    private static void printSchedule(List<BusSchedule> schedule) {
        for (BusSchedule bus : schedule) {
            System.out.println("Bus " + bus.getBusNumber() + " - Departure Time: " +
                    bus.getDepartureTime() + ", Available Seats: " + bus.getAvailableSeats());
        }
    }
}
