package org.screen.gui;

import org.screen.core.files.FileHandling;
import org.screen.core.logikk.DataLogikk;
import org.screen.core.logikk.TimerLogikk;
import org.screen.core.models.*;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

import org.screen.core.repositories.DataExportInterface;
import org.screen.core.utils.UIUtils;
import org.screen.database.Export;

public class Screen {

    public static void main(String[] args) throws Exception {

        int pleietrengende_id = FileHandling.readPleietrengendeIdFromFile();

        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();

        JFrame jframe = new JFrame("Screen");
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setSize(size);

        jframe.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                Weather.stopWeatherUpdater();
                System.exit(0);
            }
        });

        JPanel gridPanel = new JPanel(new GridLayout(3, 3));
        JPanel weatherPanel = new JPanel();
        JPanel centeredPanel = new JPanel(new GridBagLayout());
        weatherPanel.setLayout(new BoxLayout(weatherPanel, BoxLayout.Y_AXIS));

        for (int i = 0; i < 9; i++) {
            JLabel cell = new JLabel("" + i, SwingConstants.CENTER);
            cell.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            gridPanel.add(cell);
        }

        // Use LocalDate to get today and tomorrow
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);

        // Formatter for the date (dd.MM.yyyy)
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        // Get the day names in Norwegian
        String todayDayName = today.getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("no"));
        String tomorrowDayName = tomorrow.getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("no"));

        // Combine the day names and dates
        String currentDay = todayDayName + " " + today.format(dateFormatter);
        String tmrDay = tomorrowDayName + " " + tomorrow.format(dateFormatter);

        // Get the current time
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String currentTime = java.time.LocalTime.now().format(timeFormatter);

        // Update JLabels with formatted text
        JLabel timeLabel = UIUtils.createStyledLabel("<html>I dag:<br>" + currentDay + "</html>",
                "Day", Font.BOLD, 40, SwingConstants.CENTER);
        JLabel clockLabel = UIUtils.createStyledLabel(currentTime, "Clock", Font.BOLD, 60, SwingConstants.CENTER);
        JLabel tmrLabel = UIUtils.createStyledLabel("<html>I Morgen:<br>" + tmrDay + "</html>",
                "Tomorrow", Font.BOLD, 40, SwingConstants.CENTER);

        String temp = Weather.getCurrentTemperature();
        String weather = Weather.getCurrentWeatherCondition();
        ImageIcon weatherIcon = Weather.getWeatherIcon(weather, 200, 200);

        JLabel tempLabel = new JLabel("<html>Temperatur inne: " + "" + "°C" + "<br>Temperatur ute: "
                + temp + "</html>", SwingConstants.CENTER);
        tempLabel.setFont(new Font("Temp", Font.BOLD, 30));
        JLabel weatherLabel = new JLabel("Været i dag: " + weather, SwingConstants.CENTER);
        weatherLabel.setFont(new Font("Weather", Font.BOLD, 30));

        JLabel iconLabel = new JLabel(weatherIcon, SwingConstants.CENTER);

        weatherLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        weatherPanel.add(weatherLabel);
        weatherPanel.add(iconLabel);
        centeredPanel.add(weatherPanel);

        DataExportInterface exporter = new Export();
        DataLogikk dataLogikk = new DataLogikk(exporter);

        JLabel beskjedLabel = UIUtils.createStyledLabel(dataLogikk.getBeskjederHtml(pleietrengende_id),
                "Beskjeder", Font.BOLD, 40, SwingConstants.CENTER);
        JLabel avtaleLabel = UIUtils.createStyledLabel(dataLogikk.getAvtalerHtml(pleietrengende_id, true),
                "Avtaler", Font.BOLD, 40, SwingConstants.CENTER);
        JLabel avtaleLabelTmr = UIUtils.createStyledLabel(dataLogikk.getAvtalerHtml(pleietrengende_id, false),
                "AvtalerTmr", Font.BOLD, 40, SwingConstants.CENTER);

        ImageIcon shoppingIcon = UIUtils.createScaledImageIcon("/images/shopping.png", 350, 350);
        JLabel shoppingLabel = new JLabel(shoppingIcon, SwingConstants.CENTER);

        UIUtils.addToGrid(gridPanel, timeLabel, 0);
        UIUtils.addToGrid(gridPanel, clockLabel, 1);
        UIUtils.addToGrid(gridPanel, tmrLabel, 2);
        UIUtils.addToGrid(gridPanel, avtaleLabel, 3);
        UIUtils.addToGrid(gridPanel, beskjedLabel, 4);
        UIUtils.addToGrid(gridPanel, avtaleLabelTmr, 5);
        UIUtils.addToGrid(gridPanel, tempLabel, 6);
        UIUtils.addToGrid(gridPanel, shoppingLabel, 7);
        UIUtils.addToGrid(gridPanel, centeredPanel, 8);

        jframe.add(gridPanel);
        jframe.setVisible(true);

        Weather.startWeatherUpdater();

        TimerLogikk.startWeatherUpdateTimer(tempLabel, weatherLabel, iconLabel, weatherIcon);
        TimerLogikk.startClockUpdateTimer(timeLabel, clockLabel, tmrLabel);
        TimerLogikk.startDataUpdateTimer(beskjedLabel, avtaleLabel, avtaleLabelTmr, dataLogikk, pleietrengende_id);
    }
}
