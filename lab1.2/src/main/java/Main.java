import java.util.*;

// Клас для представлення розкладу автостанції
class BusSchedule {
    private int busNumber;
    private String departureTime;
    private Set<String> daysOfWeek;
    private int availableSeats;
    private List<BusRoute> routes;

    // Конструктор
    public BusSchedule(int busNumber, String departureTime, Set<String> daysOfWeek, int availableSeats) {
        this.busNumber = busNumber;
        this.departureTime = departureTime;
        this.daysOfWeek = new HashSet<>(daysOfWeek);
        this.availableSeats = availableSeats;
        this.routes = new ArrayList<>();
    }

    // Гетери та сетери

    public int getBusNumber() {
        return busNumber;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public Set<String> getDaysOfWeek() {
        return Collections.unmodifiableSet(daysOfWeek);
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public List<BusRoute> getRoutes() {
        return Collections.unmodifiableList(routes);
    }

    public void addRoute(String station, String arrivalTime) {
        routes.add(new BusRoute(station, arrivalTime));
    }
}

// Клас для представлення маршруту автостанції
class BusRoute {
    private String station;
    private String arrivalTime;

    // Конструктор
    public BusRoute(String station, String arrivalTime) {
        this.station = station;
        this.arrivalTime = arrivalTime;
    }

    // Гетери

    public String getStation() {
        return station;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }
}

// Клас-контейнер Generic Type
class BusScheduleContainer<T> {
    private List<T> items;

    // Конструктор
    public BusScheduleContainer() {
        this.items = new ArrayList<>();
    }

    // Додавання елемента до контейнера
    public void addItem(T item) {
        items.add(item);
    }

    // Гетер для отримання списку елементів
    public List<T> getItems() {
        return Collections.unmodifiableList(items);
    }
}

// Утилітарний клас для обробки контейнерів об'єктів
class BusScheduleUtil {
    // Метод для сортування розкладу за номером рейсу
    public static void sortByBusNumber(BusScheduleContainer<BusSchedule> container) {
        container.getItems().sort(Comparator.comparingInt(BusSchedule::getBusNumber));
    }

    // Метод для сортування розкладу за часом відправлення
    public static void sortByDepartureTime(BusScheduleContainer<BusSchedule> container) {
        container.getItems().sort(Comparator.comparing(BusSchedule::getDepartureTime));
    }

    // Метод для сортування розкладу за кількістю вільних місць
    public static void sortByAvailableSeats(BusScheduleContainer<BusSchedule> container) {
        container.getItems().sort(Comparator.comparingInt(BusSchedule::getAvailableSeats));
    }
}

public class Main {
    public static void main(String[] args) {
        // Створення розкладів
        BusSchedule schedule1 = new BusSchedule(101, "10:00", new HashSet<>(Arrays.asList("Mon", "Wed", "Fri")), 20);
        schedule1.addRoute("StationA", "12:00");
        schedule1.addRoute("StationB", "14:00");

        BusSchedule schedule2 = new BusSchedule(202, "12:30", new HashSet<>(Arrays.asList("Tue", "Thu", "Sat")), 15);
        schedule2.addRoute("StationC", "15:30");
        schedule2.addRoute("StationD", "17:00");

        // Створення контейнера та додавання розкладів
        BusScheduleContainer<BusSchedule> scheduleContainer = new BusScheduleContainer<>();
        scheduleContainer.addItem(schedule1);
        scheduleContainer.addItem(schedule2);

        // Використання об'єктів у циклі foreach
        for (BusSchedule schedule : scheduleContainer.getItems()) {
            System.out.println("Bus Number: " + schedule.getBusNumber());
            System.out.println("Departure Time: " + schedule.getDepartureTime());
            System.out.println("Days of Week: " + schedule.getDaysOfWeek());
            System.out.println("Available Seats: " + schedule.getAvailableSeats());

            System.out.println("Routes:");
            for (BusRoute route : schedule.getRoutes()) {
                System.out.println(" - " + route.getStation() + " (Arrival Time: " + route.getArrivalTime() + ")");
            }

            System.out.println();
        }

        // Сортування за номером рейсу та вивід результату
        BusScheduleUtil.sortByBusNumber(scheduleContainer);
        System.out.println("Sorted by Bus Number:");
        for (BusSchedule schedule : scheduleContainer.getItems()) {
            System.out.println("Bus Number: " + schedule.getBusNumber());
        }
    }
}

