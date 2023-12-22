import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class Toy {
    String name;
    int costInKopecks;
    String ageRange;
    int sizeInCentimeters;

    public Toy(String name, int costInKopecks, String ageRange, int sizeInCentimeters) {
        this.name = name;
        this.costInKopecks = costInKopecks;
        this.ageRange = ageRange;
        this.sizeInCentimeters = sizeInCentimeters;
    }

    @Override
    public String toString() {
        return "Toy{" +
                "name='" + name + '\'' +
                ", costInKopecks=" + costInKopecks +
                ", ageRange='" + ageRange + '\'' +
                ", sizeInCentimeters=" + sizeInCentimeters +
                '}';
    }
}

class ToyFilter {
    public static void main(String[] args) {
        List<Toy> toys = readToysFromFile("toys.txt");
        List<Toy> filteredToys = filterToys(toys, 3, 22);

        // Сортування ляльок за розміром у порядку збільшення
        Collections.sort(filteredToys, Comparator.comparingInt(toy -> toy.sizeInCentimeters));

        // Виведення переліку ляльок
        for (Toy toy : filteredToys) {
            System.out.println(toy);
        }
    }

    private static List<Toy> readToysFromFile(String filename) {
        List<Toy> toys = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(", ");
                String name = parts[0];
                int costInKopecks = Integer.parseInt(parts[1]);
                String ageRange = parts[2];
                int sizeInCentimeters = Integer.parseInt(parts[3]);
                toys.add(new Toy(name, costInKopecks, ageRange, sizeInCentimeters));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return toys;
    }

    private static List<Toy> filterToys(List<Toy> toys, int targetAge, int maxSize) {
        List<Toy> filteredToys = new ArrayList<>();
        for (Toy toy : toys) {
            String[] ageLimits = toy.ageRange.split("-");
            int minAge = Integer.parseInt(ageLimits[0]);
            int maxAge = Integer.parseInt(ageLimits[1]);
            if (targetAge >= minAge && targetAge <= maxAge && toy.sizeInCentimeters <= maxSize) {
                filteredToys.add(toy);
            }
        }
        return filteredToys;
    }
}
