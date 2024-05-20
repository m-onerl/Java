package pl.edu.pwr.swoloszyn.jvatz.biblio;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CsvFileManager {
    public static Object[] loadCsvFile(File csvFile) throws IOException {
        List<String> timeStamps = new ArrayList<>();
        List<Double> pressureValues = new ArrayList<>();
        List<Double> temperatureValues = new ArrayList<>();
        List<Double> humidityValues = new ArrayList<>();

        Files.lines(Paths.get(csvFile.getAbsolutePath())).forEach(line -> {
            String[] data = line.split(";");
            for (int i = 0; i < data.length; i++) {
                data[i] = data[i].replaceAll("^\"|\"$", "").replace(',', '.');
            }

            try {
                timeStamps.add(data[0]);
                double pressure = Double.parseDouble(data[1]);
                double temperature = Double.parseDouble(data[2]);
                double humidity = Double.parseDouble(data[3]);

                pressureValues.add(pressure);
                temperatureValues.add(temperature);
                humidityValues.add(humidity);
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                System.err.println("Error parsing line: " + e.getMessage());
            }
        });

        return new Object[]{timeStamps, pressureValues, temperatureValues, humidityValues};
    }
}

