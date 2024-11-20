package org.screen.core.logikk;

import org.screen.core.models.Weather;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimerLogikk {

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

        Timer clockTimer = new Timer(1000, e -> {
            Date now = new Date();

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

    public static void startDataUpdateTimer(JLabel beskjedLabel, JLabel avtaleLabel, JLabel avtaleLabelTmr, Runnable updateTask) {
        Timer dataUpdateTimer = new Timer(10000, e -> {
            updateTask.run();
        });
        dataUpdateTimer.start();
    }
}
