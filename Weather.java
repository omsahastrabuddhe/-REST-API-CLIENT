import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

public class Weather {

    private static final String API_KEY = "2a5f8c64c718741227fb7900b5ea4522"; // Replace with your key if needed
    private static final String CITY = "Pune";

    public static void main(String[] args) {
        try {
            // Build the API URL
            String apiUrl = String.format(
                    "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric",
                    CITY, API_KEY);

            // Open HTTP connection
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Get response code
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read data
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Parse JSON response
                JSONObject json = new JSONObject(response.toString());
                String weather = json.getJSONArray("weather").getJSONObject(0).getString("description");
                double temperature = json.getJSONObject("main").getDouble("temp");
                int humidity = json.getJSONObject("main").getInt("humidity");
                double windSpeed = json.getJSONObject("wind").getDouble("speed");

                // Output
                System.out.println("====== Weather Info for " + CITY + " ======");
                System.out.println("Weather: " + weather);
                System.out.println("Temperature: " + temperature + " °C");
                System.out.println("Humidity: " + humidity + " %");
                System.out.println("Wind Speed: " + windSpeed + " m/s");

            } else {
                System.out.println("Error: HTTP " + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
