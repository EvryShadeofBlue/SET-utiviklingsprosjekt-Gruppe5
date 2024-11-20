package org.screen.core.logikk;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherLogikk {
    public static JsonObject fetchWeatherData(String apiUrl) throws Exception {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        if (connection.getResponseCode() == 200) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            return JsonParser.parseString(response.toString()).getAsJsonObject();
        } else {
            throw new Exception("Failed to get weather data. Response code: " + connection.getResponseCode());
        }
    }

    public static ImageIcon loadImageIcon(String iconPath, int width, int height) {
        try {
            ImageIcon icon = new ImageIcon(WeatherLogikk.class.getResource(iconPath));

            if (icon.getImageLoadStatus() != MediaTracker.COMPLETE) {
                throw new Exception("Image could not be loaded");
            }

            Image img = icon.getImage();
            Image scaled = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaled);
        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "Kunne ikke laste opp bilder",
                    "Feil", JOptionPane.ERROR_MESSAGE);


            return new ImageIcon(new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB));
        }
    }



}
