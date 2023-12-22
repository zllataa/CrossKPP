import java.util.*;

class Bus {
    private int busNumber;
    private String departureTime;
    private String daysOfWeek;
    private int availableSeats;
    private List<String> route;

    public Bus(int busNumber, String departureTime, String daysOfWeek, int availableSeats, List<String> route) {
        this.busNumber = busNumber;
        this.departureTime = departureTime;
        this.daysOfWeek = daysOfWeek;
        this.availableSeats = availableSeats;
        this.route = route;
    }

    // Getters and setters

    public int getBusNumber() {
        return busNumber;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    // Other getters and setters...

    @Override
    public String toString() {
        return "Bus{" +
                "busNumber=" + busNumber +
                ", departureTime='" + departureTime + '\'' +
                ", daysOfWeek='" + daysOfWeek + '\'' +
                ", availableSeats=" + availableSeats +
                ", route=" + route +
                '}';
    }
}

public class BusStation {
    private List<Bus> buses;

    public BusStation() {
        this.buses = new ArrayList<>();
    }

    // Додавання рейсу
    public void addBus(Bus bus) {
        buses.add(bus);
    }

    // Видалення рейсу за номером
    public void removeBus(int busNumber) {
        buses.removeIf(bus -> bus.getBusNumber() == busNumber);
    }

    // Пошук рейсу за номером
    public Bus findBus(int busNumber) {
        return buses.stream()
                .filter(bus -> bus.getBusNumber() == busNumber)
                .findFirst()
                .orElse(null);
    }

    // Сортування рейсів за різними полями
    public void sortBusesByNumber() {
        buses.sort(Comparator.comparingInt(Bus::getBusNumber));
    }

    public void sortBusesByDepartureTime() {
        buses.sort(Comparator.comparing(Bus::getDepartureTime));
    }

    public void sortBusesByAvailableSeats() {
        buses.sort(Comparator.comparingInt(Bus::getAvailableSeats));
    }

    // Демонстрація функціональності
    public static void main(String[] args) {
        BusStation busStation = new BusStation();

        busStation.addBus(new Bus(1, "08:00", "Mon", 10, Arrays.asList("Station1", "10:00", "Station2", "11:30")));
        busStation.addBus(new Bus(3, "10:30", "Tue", 5, Arrays.asList("Station1", "12:00", "Station2", "13:30")));
        busStation.addBus(new Bus(2, "09:15", "Wed", 15, Arrays.asList("Station1", "11:00", "Station2", "12:30")));

        System.out.println("Before sorting:");
        busStation.buses.forEach(System.out::println);

        busStation.sortBusesByNumber();

        System.out.println("\nAfter sorting by bus number:");
        busStation.buses.forEach(System.out::println);

        
    }
}
