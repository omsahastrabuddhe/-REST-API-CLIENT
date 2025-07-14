import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

public class Weather {

    // Replace with your actual API key
    private static final String API_KEY = "2a5f8c64c718741227fb7900b5ea4522";
    private static final String CITY = "India";

    public static void main(String[] args) {
        try {
            // Build URL
            String urlString = String.format(
                    "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric",
                    CITY, API_KEY);
            URL url = new URL(urlString);

            // Open connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read response
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Parse JSON
                JSONObject json = new JSONObject(response.toString());

                // Extract data
                String weatherDescription = json
                        .getJSONArray("weather")
                        .getJSONObject(0)
                        .getString("description");
                double temp = json
                        .getJSONObject("main")
                        .getDouble("temp");
                int humidity = json
                        .getJSONObject("main")
                        .getInt("humidity");
                double windSpeed = json
                        .getJSONObject("wind")
                        .getDouble("speed");

                // Display in structured format
                System.out.println("=== Weather Data for " + CITY + " ===");
                System.out.println("Description: " + weatherDescription);
                System.out.println("Temperature: " + temp + "°C");
                System.out.println("Humidity: " + humidity + "%");
                System.out.println("Wind Speed: " + windSpeed + " m/s");

            } else {
                System.out.println("GET request failed. Response Code: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
