package models;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.swing.*;

public class Weather {

    public static String getTemperature() {
        try {
            String urlString = String.format("http://api.openweathermap.org/data/2.5/weather?q=Halden,no&appid="
                    + API.getApiWeather());        //laget egen fil med API-key lagt inn i gitignore
            URL url = new URL(urlString);  //henter temperatur fra halden med skjult API-key

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET"); //for å hente data

            if (connection.getResponseCode() == 200) {      //sjekker om responsen er vellykket
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {    //leser responsen
                    response.append(line);
                }
                reader.close();

                JsonObject weatherData = JsonParser.parseString(response.toString()).getAsJsonObject(); //parser responsen

                double tempKelvin = weatherData.getAsJsonObject("main").get("temp").getAsDouble();  //henter temperaturen i kelvin
                double tempCelsius = tempKelvin - 273.15;   //omgjør til celsius

                return String.format("%.2f°C", tempCelsius); //returnerer temperaturen som en string
            } else {
                return "Error: Klarte ikke få tak i vær data."; //feilmelding dersom respons ikke ble vellykket
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Error: Kunne ikke hente data."; //feilmelding ved andre unntak
        }
    }

    public static String getWeatherCondition() {
        try {
            String urlString = String.format("http://api.openweathermap.org/data/2.5/weather?q=Halden,no" +
                    "&units=metric&lang=no&appid=" + API.getApiWeather());
            URL url = new URL(urlString);       //henter værtilstand i halden

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

                JsonObject weatherData = JsonParser.parseString(response.toString()).getAsJsonObject();

                String weatherDescription = weatherData.getAsJsonArray("weather").get(0).getAsJsonObject()
                        .get("description").getAsString();  //henter værbeskrivelse fra responsen

                return weatherDescription;
            } else {
                return "Error: Klarte ikke få tak i vær data.";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Error: Kunne ikke hente data.";
        }
    }

    public static ImageIcon getWeatherIcon(String weatherDescription, int width, int height) {
        String iconPath = "/images/weather.png";        //standard ikon dersom en beskjed ingen strings matcher

        if (weatherDescription.contains("himmel") || weatherDescription.contains("klar")) {
            iconPath = "/images/sunny.png";     //viser bilde om string matcher
        }
        else if (weatherDescription.contains("skyer") || weatherDescription.contains("overskyet")) {
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

        ImageIcon icon = new ImageIcon(Weather.class.getResource(iconPath));    //henter bildet fra resources

        Image img = icon.getImage();
        Image scaled = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);    //scaler bilde for å få det større

        return new ImageIcon(scaled);   //returnerer de scalede bildene
    }
}
