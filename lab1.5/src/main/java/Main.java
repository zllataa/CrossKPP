import java.io.Serializable;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public List<String> getDaysOfWeek() {
        return daysOfWeek;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public List<String> getRoute() {
        return route;
    }

    @Override
    public String toString() {
        return "Flight Number: " + flightNumber + ", Departure Time: " + departureTime +
                ", Days of Week: " + daysOfWeek + ", Available Seats: " + availableSeats +
                ", Route: " + route;
    }
    public static boolean isValidDepartureTime(String departureTime) {
        // Регулярний вираз для перевірки формату часу (години:хвилини)
        String regex = "^([01]\\d|2[0-3]):([0-5]\\d)$";

        // Створення об'єкту Pattern
        Pattern pattern = Pattern.compile(regex);

        // Створення об'єкту Matcher
        Matcher matcher = pattern.matcher(departureTime);

        // Перевірка відповідності регулярному виразу
        return matcher.matches();
    }
    public static boolean isValidInput(String input) {
        // Регулярний вираз для перевірки формату днів тижня (наприклад, Monday, Tuesday)
        String regex = "^(Monday|Tuesday|Wednesday|Thursday|Friday|Saturday|Sunday)(, (Monday|Tuesday|Wednesday|Thursday|Friday|Saturday|Sunday))*$";

        // Створення об'єкту Pattern
        Pattern pattern = Pattern.compile(regex);

        // Створення об'єкту Matcher
        Matcher matcher = pattern.matcher(input);

        // Перевірка відповідності регулярному виразу
        return matcher.matches();
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

                    // Використання методу для перевірки коректності часу відправлення за допомогою регулярного виразу
                    while (!Autostation.isValidDepartureTime(departureTime)) {
                        System.out.println("Невірний формат часу. Введіть ще раз (година:хвилина): ");
                        departureTime = scanner.nextLine();
                    }

                    System.out.println("Введіть дні тижня (через кому): ");
                    String inputDaysOfWeek = scanner.nextLine();

                    // Використання методу для перевірки коректності введених днів тижня за допомогою регулярного виразу
                    while (!Autostation.isValidInput(inputDaysOfWeek)) {
                        System.out.println("Невірний формат днів тижня. Введіть ще раз (наприклад, Monday, Tuesday): ");
                        inputDaysOfWeek = scanner.nextLine();
                    }

                    List<String> daysOfWeek = Arrays.asList(inputDaysOfWeek.split(", "));

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
        autostationContainer.addAutostation(new Autostation(1, "10:15", Arrays.asList("Saturday"), 8, Arrays.asList("Kharkiv, 08:45", "Poltava, 09:45", "Kyiv, 12:00")));
        autostationContainer.addAutostation(new Autostation(2, "09:30", Arrays.asList("Sunday"), 10, Arrays.asList("Kharkiv, 07:45", "Poltava, 08:45", "Kyiv, 11:00")));
        autostationContainer.addAutostation(new Autostation(3, "14:00", Arrays.asList("Saturday", "Sunday"), 12, Arrays.asList("Kharkiv, 12:15", "Poltava, 13:15", "Kyiv, 15:30")));
        autostationContainer.addAutostation(new Autostation(4, "11:45", Arrays.asList("Sunday"), 15, Arrays.asList("Kharkiv, 10:00", "Poltava, 11:00", "Kyiv, 13:15")));
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
        // Пошук ранкових рейсів у вихідні дні з Харкова до Києва з зупинкою в Полтаві
        System.out.println("\nMorning flights on weekends from Kharkiv to Kyiv with a stopover in Poltava:");
        for (Autostation autostation : autostationContainer) {
            // Розділення часу відправлення на години та хвилини
            int departureHour = Integer.parseInt(autostation.getDepartureTime().split(":")[0]);
            int departureMinute = Integer.parseInt(autostation.getDepartureTime().split(":")[1]);

            // Умова для ранкових рейсів (години відправлення до 12:00)
            if (departureHour < 12 &&
                    (autostation.getDaysOfWeek().contains("Saturday") || autostation.getDaysOfWeek().contains("Sunday")) &&
                    autostation.getRoute().stream().anyMatch(stop -> stop.contains("Poltava"))) {
                System.out.println(autostation);
            }
        }

    }
}
