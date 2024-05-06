package engine;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonOutput {

    // Method to save JSON representation of data into a file.
    private void saveJsonInFile(String data, String file) {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(data);
        } catch (IOException e) {
            System.out.println("Error while saving the Json object to a file: " + e.getMessage());
        }
    }

    // Generates JSON output from the current data and saves it.
    public String generateJsonOutput(List<Airport> airportList) {

        // Configures Gson for pretty printing and registers a custom adapter for
        // LocalDateTime.

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();

        LocalDateTime date = LocalDateTime.now();
        TimeStamp timeStamp = new TimeStamp(date);

        // Creates a JSON output object containing timestamps and airport list.
        JsonObject jsonOutput = new JsonObject(timeStamp, airportList);
        String jsonData = gson.toJson(jsonOutput);

        // Prints the data within the console
        System.out.println(jsonData);

        // Saves the JSON data into two files, a primary and a backup.
        saveJsonInFile(jsonData, "data.json");
        saveJsonInFile(jsonData, "data.json.bak");

        return jsonData;
    }

    // Inner class to structure the JSON output.
    static class JsonObject {
        private TimeStamp timeStamp;
        private List<Airport> airportList;

        public JsonObject(TimeStamp timeStamp, List<Airport> airportList) {
            this.timeStamp = timeStamp;
            this.airportList = airportList;
        }

        // Getter methods for accessing properties.
        public List<Airport> getAirportList() {
            return this.airportList;
        }

        public TimeStamp getTimeStamp() {
            return this.timeStamp;
        }
    }
}
