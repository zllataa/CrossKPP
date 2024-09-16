import java.io.*;
import java.util.*;

class Route implements Serializable {
    private String stationName;
    private String arrivalTime;

    public Route(String stationName, String arrivalTime) {
        this.stationName = stationName;
        this.arrivalTime = arrivalTime;
    }

    public String getStationName() {
        return stationName;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    @Override
    public String toString() {
        return stationName + " - " + arrivalTime;
    }
}

class BusSchedule implements Serializable {
    private static final long serialVersionUID = 1L;

    private int busNumber;
    private String departureTime;
    private String[] daysOfWeek;
    private int availableSeats;
    private List<Route> route;

    public BusSchedule(int busNumber, String departureTime, String[] daysOfWeek, int availableSeats, List<Route> route) {
        this.busNumber = busNumber;
        this.departureTime = departureTime;
        this.daysOfWeek = daysOfWeek;
        this.availableSeats = availableSeats;
        this.route = route;
    }

    // getters and setters

    @Override
    public String toString() {
        return "Bus #" + busNumber + " - Departure Time: " + departureTime + " - Available Seats: " + availableSeats;
    }
}

class BusStationContainer implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<BusSchedule> busSchedules;

    public BusStationContainer() {
        this.busSchedules = new ArrayList<>();
    }

    public void addBusSchedule(BusSchedule busSchedule) {
        busSchedules.add(busSchedule);
    }

    public void removeBusSchedule(int index) {
        if (index >= 0 && index < busSchedules.size()) {
            busSchedules.remove(index);
        } else {
            System.out.println("Невірний індекс рейсу для видалення.");
        }
    }

    public BusSchedule getBusSchedule(int index) {
        if (index >= 0 && index < busSchedules.size()) {
            return busSchedules.get(index);
        } else {
            System.out.println("Невірний індекс рейсу.");
            return null;
        }
    }

    public List<BusSchedule> getBusSchedules() {
        return busSchedules;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BusStationContainer container;

        // Зчитуємо дані з файлу або створюємо новий контейнер
        try {
            container = deserialize("bus_station.ser");
        } catch (FileNotFoundException e) {
            container = new BusStationContainer();
        }

        boolean exit = false;

        while (!exit) {
            System.out.println("1. Додати новий рейс");
            System.out.println("2. Видалити рейс");
            System.out.println("3. Переглянути розклад");
            System.out.println("4. Зберегти та вийти");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Очищення буфера

            switch (choice) {
                case 1:
                    // Додавання нового рейсу
                    addNewBusSchedule(container, scanner);
                    break;
                case 2:
                    // Видалення рейсу
                    removeBusSchedule(container, scanner);
                    break;
                case 3:
                    // Перегляд розкладу
                    displayBusSchedules(container);
                    break;
                case 4:
                    // Зберегти та вийти
                    serialize(container, "bus_station.ser");
                    exit = true;
                    break;
                default:
                    System.out.println("Невірний вибір. Спробуйте ще раз.");
            }
        }
    }

    private static void addNewBusSchedule(BusStationContainer container, Scanner scanner) {
        System.out.println("Введіть номер рейсу:");
        int busNumber = scanner.nextInt();
        scanner.nextLine(); // Очищення буфера

        System.out.println("Введіть час відправлення:");
        String departureTime = scanner.nextLine();

        System.out.println("Введіть дні тижня (через кому, наприклад, Monday,Wednesday):");
        String[] daysOfWeek = scanner.nextLine().split(",");

        System.out.println("Введіть кількість вільних місць:");
        int availableSeats = scanner.nextInt();
        scanner.nextLine(); // Очищення буфера

        System.out.println("Введіть маршрут (назва станції та час прибуття, через кому):");
        String[] routeInput = scanner.nextLine().split(",");
        List<Route> route = new ArrayList<>();

        for (int i = 0; i < routeInput.length; i += 2) {
            route.add(new Route(routeInput[i], routeInput[i + 1]));
        }

        BusSchedule newBus = new BusSchedule(busNumber, departureTime, daysOfWeek, availableSeats, route);
        container.addBusSchedule(newBus);

        System.out.println("Новий рейс додано!");
    }

    private static void removeBusSchedule(BusStationContainer container, Scanner scanner) {
        System.out.println("Введіть номер рейсу для видалення:");
        int index = scanner.nextInt();
        scanner.nextLine(); // Очищення буфера

        container.removeBusSchedule(index - 1);
        System.out.println("Рейс видалено!");
    }

    private static void displayBusSchedules(BusStationContainer container) {
        List<BusSchedule> schedules = container.getBusSchedules();
        if (schedules.isEmpty()) {
            System.out.println("Розклад порожній.");
        } else {
            System.out.println("Розклад автостанції:");
            for (int i = 0; i < schedules.size(); i++) {
                System.out.println((i + 1) + ". " + schedules.get(i));
            }
        }
    }

    private static void serialize(Object obj, String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(obj);
            System.out.println("Серіалізація завершена.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static <T> T deserialize(String fileName) throws FileNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            return (T) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new FileNotFoundException("Файл не знайдено");
        }
    }
}
