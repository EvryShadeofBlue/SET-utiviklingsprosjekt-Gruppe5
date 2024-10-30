package org.screen.core.models;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimerService {

    public static void startWeatherUpdateTimer(JLabel tempLabel, JLabel weatherLabel, JLabel iconLabel, ImageIcon weatherIcon) {
        Timer timer = new Timer(60000, e -> {
            tempLabel.setText("<html>Temperatur inne: " + "" + "°C" + "<br>Temperatur ute: "
                    + Weather.getCurrentTemperature() + "<html>");
            weatherLabel.setText("Været i dag: " + Weather.getCurrentWeatherCondition());
            weatherIcon.setImage(Weather.getWeatherIcon(Weather.getCurrentWeatherCondition(), 200, 200).getImage());
            iconLabel.setIcon(weatherIcon);
        });
        timer.start();
    }

    public static void startClockUpdateTimer(JLabel timeLabel, JLabel clockLabel, JLabel tmrLabel) {
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat tomorrowFormat = new SimpleDateFormat("dd.MM.yyyy");

        javax.swing.Timer clockTimer = new javax.swing.Timer(1000, e -> {
            Date now = new Date();

            // Update labels with the current date and time
            String currentDay = dayFormat.format(now);
            String currentTime = timeFormat.format(now);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(now);
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            String tmrDay = tomorrowFormat.format(calendar.getTime());

            timeLabel.setText("<html>I dag:<br>" + currentDay + "<html>");
            clockLabel.setText(currentTime);
            tmrLabel.setText("<html>I Morgen:<br>" + tmrDay + "<html>");
        });
        clockTimer.start();
    }

}
