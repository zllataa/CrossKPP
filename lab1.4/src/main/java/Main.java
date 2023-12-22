import java.io.Serializable;
import java.util.*;

class Autostation implements Serializable {
    private int flightNumber;
    private String departureTime;
    private List<String> daysOfWeek;
    private int availableSeats;
    private List<String> route;

    public Autostation(int flightNumber, String departureTime, List<String> daysOfWeek, int availableSeats, List<String> route) {
        this.flightNumber = flightNumber;
        this.departureTime = departureTime;
        this.daysOfWeek = daysOfWeek;
        this.availableSeats = availableSeats;
        this.route = route;
    }

    public int getFlightNumber() {
        return flightNumber;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    @Override
    public String toString() {
        return "Flight Number: " + flightNumber + ", Departure Time: " + departureTime +
                ", Days of Week: " + daysOfWeek + ", Available Seats: " + availableSeats +
                ", Route: " + route;
    }
}

class AutostationContainer implements Iterable<Autostation>, Serializable {
    private List<Autostation> autostations = new LinkedList<>();

    public void addAutostation(Autostation autostation) {
        autostations.add(autostation);
    }

    public void sortByFlightNumber() {
        autostations.sort(Comparator.comparingInt(Autostation::getFlightNumber));
    }

    public void sortByDepartureTime() {
        autostations.sort(Comparator.comparing(Autostation::getDepartureTime));
    }

    public void sortByAvailableSeats() {
        autostations.sort(Comparator.comparingInt(Autostation::getAvailableSeats));
    }

    @Override
    public Iterator<Autostation> iterator() {
        return autostations.iterator();
    }
}

class AutostationUtil {
    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("auto")) {
            // Автоматичний режим виконання
            AutostationContainer autostationContainer = generateAutostationContainer();
            processAutostationContainer(autostationContainer);
        } else {
            // Діалоговий режим виконання
            Scanner scanner = new Scanner(System.in);
            AutostationContainer autostationContainer = new AutostationContainer();

            boolean continueInput = true;

            while (continueInput) {
                System.out.println("Додайте нову автостанцію (Y/N): ");
                String input = scanner.nextLine();

                if (input.equalsIgnoreCase("Y")) {
                    // Зчитати дані про нову автостанцію від користувача та додати до контейнера
                    System.out.println("Введіть номер рейсу: ");
                    int flightNumber = scanner.nextInt();

                    // Очистка буфера введення
                    scanner.nextLine();

                    System.out.println("Введіть час відправлення: ");
                    String departureTime = scanner.nextLine();

                    System.out.println("Введіть дні тижня (через кому): ");
                    List<String> daysOfWeek = Arrays.asList(scanner.nextLine().split(", "));

                    System.out.println("Введіть кількість доступних місць: ");
                    int availableSeats = scanner.nextInt();

                    // Очистка буфера введення
                    scanner.nextLine();

                    System.out.println("Введіть маршрут (через кому): ");
                    List<String> route = Arrays.asList(scanner.nextLine().split(", "));

                    // Додати нову автостанцію до контейнера
                    autostationContainer.addAutostation(new Autostation(
                            flightNumber,
                            departureTime,
                            daysOfWeek,
                            availableSeats,
                            route
                    ));


            } else if (input.equalsIgnoreCase("N")) {
                    continueInput = false;
                } else {
                    System.out.println("Невірний ввід. Спробуйте ще раз.");
                }
            }

            // Завершити введення та обробити контейнер
            processAutostationContainer(autostationContainer);
        }
    }

    private static AutostationContainer generateAutostationContainer() {
        AutostationContainer autostationContainer = new AutostationContainer();
        // Генерація даних для автоматичного режиму
        autostationContainer.addAutostation(new Autostation(3, "10:15", Arrays.asList("Monday", "Thursday"), 8, Arrays.asList("Station5, 11:30", "Station6, 13:45")));
        autostationContainer.addAutostation(new Autostation(4, "15:45", Arrays.asList("Wednesday", "Friday"), 12, Arrays.asList("Station7, 17:00", "Station8, 19:15")));
        return autostationContainer;
    }

    private static void processAutostationContainer(AutostationContainer autostationContainer) {
        // Виведення даних контейнера
        System.out.println("Autostation Container:");
        for (Autostation autostation : autostationContainer) {
            System.out.println(autostation);
        }

        // Сортування та виведення відсортованих даних
        autostationContainer.sortByFlightNumber();
        System.out.println("\nSorted by Flight Number:");
        for (Autostation autostation : autostationContainer) {
            System.out.println(autostation);
        }

        autostationContainer.sortByDepartureTime();
        System.out.println("\nSorted by Departure Time:");
        for (Autostation autostation : autostationContainer) {
            System.out.println(autostation);
        }

        autostationContainer.sortByAvailableSeats();
        System.out.println("\nSorted by Available Seats:");
        for (Autostation autostation : autostationContainer) {
            System.out.println(autostation);
        }
    }
}
