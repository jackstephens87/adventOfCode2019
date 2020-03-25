package Day1;

import java.util.List;

public class FuelCounterUpper {

    public int moduleFuelRequiredAcrossModules(List<Module> modules) {
        int totalFuel = 0;

        for (Module module : modules) {
            totalFuel = totalFuel + fuelRequiredForModule(module.getMass());
        }

        return totalFuel;
    }

    public int totalFuelRequiredAcrossModules(List<Module> modules) {
        int totalFuel = 0;

        for (Module module : modules) {
            totalFuel = totalFuel + totalFuelRequired(module.getMass());
        }

        return totalFuel;
    }

    public int totalFuelRequired(int moduleMass) {
        int moduleFuel = fuelRequiredForModule(moduleMass);
        int fuelFuel = fuelRequiredForFuel(moduleFuel);
        return moduleFuel + fuelFuel;
    }

    int fuelRequiredForModule(int mass) {
        return fuelRequiredForMass(mass);
    }

    private int fuelRequiredForMass(int mass) {
        Double fuel = Math.floor(mass / 3) - 2;
        return fuel.intValue();
    }

    int fuelRequiredForFuel(int mass) {
        int fuel = fuelRequiredForMass(mass);
        int totalFuel = 0;
        while (fuel > 0) {
            totalFuel = totalFuel + fuel;
            fuel = fuelRequiredForMass(fuel);
        }
        return totalFuel < 0 ? 0 : totalFuel;
    }

}
