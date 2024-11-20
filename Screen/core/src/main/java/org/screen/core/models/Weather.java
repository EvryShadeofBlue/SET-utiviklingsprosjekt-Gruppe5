package org.screen.core.models;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.screen.core.logikk.WeatherLogikk;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.*;

public class Weather {

    private static String currentTemperature = "N/A";
    private static String currentWeatherCondition = "N/A";
    private static ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    static {
        startWeatherUpdater();
    }

    public static String getTemperature() {
        try {
            String urlString = String.format("http://api.openweathermap.org/data/2.5/weather?q=Halden,no&appid="
                    + Resources.getApiWeather());
            JsonObject weatherData = WeatherLogikk.fetchWeatherData(urlString);

                double tempKelvin = weatherData.getAsJsonObject("main").get("temp").getAsDouble();
                double tempCelsius = tempKelvin - 273.15;

                return String.format("%.2f°C", tempCelsius);
            } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getWeatherCondition() {
        try {
            String urlString = String.format("http://api.openweathermap.org/data/2.5/weather?q=Halden,no" +
                    "&units=metric&lang=no&appid=" + Resources.getApiWeather());
            JsonObject weatherData = WeatherLogikk.fetchWeatherData(urlString);

            return weatherData.getAsJsonArray("weather").get(0).getAsJsonObject().get("description").getAsString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: Kunne ikke hente data.";
        }
    }

    public static ImageIcon getWeatherIcon(String weatherDescription, int width, int height) {
        String iconPath = "/images/weather.png";

        if (weatherDescription.contains("himmel") || weatherDescription.contains("klar")) {
            iconPath = "/images/sunny.png";
        }
        else if (weatherDescription.contains("skyer") || weatherDescription.contains("overskyet") ||
        weatherDescription.contains("sky")) {
            iconPath = "/images/cloudy.png";
        }
        else if (weatherDescription.contains("regn")) {
            iconPath = "/images/rainy.png";
        }
        else if (weatherDescription.contains("snø")) {
            iconPath = "/images/snowy.png";
        }
        else if (weatherDescription.contains("vind")) {
            iconPath = "/images/windy.png";
        }
        else if (weatherDescription.contains("torden")) {
            iconPath = "/images/thunder.png";
        }

        return WeatherLogikk.loadImageIcon(iconPath, width, height);
    }

    public static void startWeatherUpdater() {
        currentTemperature = getTemperature();
        currentWeatherCondition = getWeatherCondition();

        Runnable task = () -> {
            currentTemperature = getTemperature();
            currentWeatherCondition = getWeatherCondition();
        };

        executor.scheduleAtFixedRate(task, 0, 1, TimeUnit.MINUTES);
    }

    public static void stopWeatherUpdater() {
        executor.shutdown();
    }

    public static String getCurrentTemperature() {
        return currentTemperature;
    }

    public static String getCurrentWeatherCondition() {
        return currentWeatherCondition;
    }
}
