using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;

class Toy
{
    public string Name { get; set; }
    public int CostInKopecks { get; set; }
    public string AgeRange { get; set; }
    public int SizeInCentimeters { get; set; }

    public Toy(string name, int costInKopecks, string ageRange, int sizeInCentimeters)
    {
        Name = name;
        CostInKopecks = costInKopecks;
        AgeRange = ageRange;
        SizeInCentimeters = sizeInCentimeters;
    }

    public override string ToString()
    {
        return $"Toy{{ name='{Name}', costInKopecks={CostInKopecks}, ageRange='{AgeRange}', sizeInCentimeters={SizeInCentimeters} }}";
    }
}

class ToyFilter
{
    static void Main()
    {
        List<Toy> toys = ReadToysFromFile("toys.txt");
        List<Toy> filteredToys = FilterToys(toys, 3, 22);

        // Sorting toys by size in ascending order
        filteredToys = filteredToys.OrderBy(toy => toy.SizeInCentimeters).ToList();

        // Displaying the list of toys
        foreach (Toy toy in filteredToys)
        {
            Console.WriteLine(toy);
        }
    }

    private static List<Toy> ReadToysFromFile(string filename)
    {
        List<Toy> toys = new List<Toy>();
        try
        {
            using (StreamReader sr = new StreamReader(filename))
            {
                string line;
                while ((line = sr.ReadLine()) != null)
                {
                    string[] parts = line.Split(", ");
                    string name = parts[0];
                    int costInKopecks = int.Parse(parts[1]);
                    string ageRange = parts[2];
                    int sizeInCentimeters = int.Parse(parts[3]);
                    toys.Add(new Toy(name, costInKopecks, ageRange, sizeInCentimeters));
                }
            }
        }
        catch (IOException e)
        {
            Console.WriteLine(e.Message);
        }
        return toys;
    }

    private static List<Toy> FilterToys(List<Toy> toys, int targetAge, int maxSize)
    {
        return toys.Where(toy =>
        {
            string[] ageLimits = toy.AgeRange.Split("-");
            int minAge = int.Parse(ageLimits[0]);
            int maxAge = int.Parse(ageLimits[1]);
            return targetAge >= minAge && targetAge <= maxAge && toy.SizeInCentimeters <= maxSize;
        }).ToList();
    }
}
