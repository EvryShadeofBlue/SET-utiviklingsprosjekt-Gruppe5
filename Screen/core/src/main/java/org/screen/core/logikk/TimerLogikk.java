package org.screen.core.logikk;

import org.screen.core.models.Weather;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        Timer clockTimer = new Timer(1000, e -> {
            LocalDateTime now = LocalDateTime.now();

            // Henter dagens og morgendagens dato og dagnavn
            LocalDate today = now.toLocalDate();
            LocalDate tomorrow = today.plusDays(1);

            String todayDayName = today.getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("no"));
            String tomorrowDayName = tomorrow.getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("no"));

            String currentDay = todayDayName + " " + today.format(dateFormatter);
            String currentTime = now.format(timeFormatter);
            String tmrDay = tomorrowDayName + " " + tomorrow.format(dateFormatter);

            timeLabel.setText("<html>I dag:<br>" + currentDay + "</html>");
            clockLabel.setText(currentTime);
            tmrLabel.setText("<html>I Morgen:<br>" + tmrDay + "</html>");
        });
        clockTimer.start();
    }

    public static void startDataUpdateTimer(JLabel beskjedLabel, JLabel avtaleLabel, JLabel avtaleLabelTmr, DataLogikk dataLogikk, int pleietrengendeId) {
        Timer dataUpdateTimer = new Timer(10000, e -> {
            try {
                String beskjederHtml = dataLogikk.getBeskjederHtml(pleietrengendeId);
                String avtalerTodayHtml = dataLogikk.getAvtalerHtml(pleietrengendeId, true);
                String avtalerTomorrowHtml = dataLogikk.getAvtalerHtml(pleietrengendeId, false);

                beskjedLabel.setText(beskjederHtml);
                avtaleLabel.setText(avtalerTodayHtml);
                avtaleLabelTmr.setText(avtalerTomorrowHtml);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        dataUpdateTimer.start();
    }
}
