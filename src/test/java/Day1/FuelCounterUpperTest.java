package Day1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FuelCounterUpperTest {

    FuelCounterUpper counter;

    @BeforeEach
    void setUp() {
        counter = new FuelCounterUpper();
    }

    @Test
    void fuelRequiredCalculated() {
        Module module = new Module(12);
        assertEquals(2, counter.fuelRequiredForModule(module.getMass()));
    }

    @Test
    void fuelRequiredCalculated2() {
        Module module = new Module(14);
        assertEquals(2, counter.fuelRequiredForModule(module.getMass()));
    }

    @Test
    void fuelRequiredCalculated3() {
        Module module = new Module(1969);
        assertEquals(654, counter.fuelRequiredForModule(module.getMass()));
    }

    @Test
    void fuelRequiredForMultipleModules() {
        List<Module> modules = new ArrayList<>();
        Module moduleOne = new Module(12);
        Module moduleTwo = new Module(100756);
        modules.add(moduleOne);
        modules.add(moduleTwo);
        assertEquals(33585, counter.moduleFuelRequiredAcrossModules(modules));
    }

    @Test
    void moduleFuelRequiredForMultipleModulesFromCSV() throws IOException {
        List<Module> modules = readFromCSV();
        assertEquals(3420719, counter.moduleFuelRequiredAcrossModules(modules));
    }

    private List<Module> readFromCSV() throws IOException {
        List<Module> records = new ArrayList<>();
        List<List<Module>> allRecords = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Personal_Development\\adventOfCode2019\\src\\test\\resources\\Day1\\Day1_input1.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                allRecords.add(convertStringArrayToModuleArray(line.split(",")));
            }
        }
        for (List<Module> value : allRecords) {
            records.add(value.get(0));
        }
        return records;
    }

    private List<Module> convertStringArrayToModuleArray(String[] stringArray) {
        int size = stringArray.length;
        List<Module> arr = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            arr.add(i, new Module(Integer.parseInt(stringArray[i])));
        }
        return arr;
    }

    @Test
    void negativeFuelReturnsZero() {
        assertEquals(0, counter.fuelRequiredForFuel(2));
    }

    @Test
    void positiveFuelReturnsFuel() {
        assertEquals(312, counter.fuelRequiredForFuel(654));
    }

    @Test
    void totalFuelForModuleAndFuel() {
        assertEquals(966, counter.totalFuelRequired(1969));
    }

    @Test
    void totalFuelForModuleAndFuel2() {
        assertEquals(50346, counter.totalFuelRequired(100756));
    }

    @Test
    void totalFuelRequiredForMultipleModulesFromCSV() throws IOException {
        List<Module> modules = readFromCSV();
        assertEquals(5128195, counter.totalFuelRequiredAcrossModules(modules));
    }
}