package org.screen.gui;

import org.screen.core.models.*;

import javax.swing.*;
import java.awt.*;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import org.screen.database.Export;

public class Screen {
    public static void main(String[] args) throws Exception {

        int pleietrengende_id = FileHandling.readPleietrengendeIdFromFile();

        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();   //Henter maskinens resolution

        JFrame jframe = new JFrame("Screen");           //lager selve GUI'en
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //stopper 'RUN' om du krysser ut GUI'en
        jframe.setSize(size);       //bruker maskinens resolution

        jframe.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                Weather.stopWeatherUpdater();
                System.exit(0);
            }
        });

        JPanel gridPanel = new JPanel(new GridLayout(3, 3));    //lager en 3x3 grid for design
        JPanel weatherPanel = new JPanel();     //extra panel for å tillatte 2 componenter inn i samme grid cell
        JPanel centeredPanel = new JPanel(new GridBagLayout());     //laget for å sentrere compnenter i grid cell vertikalt
        weatherPanel.setLayout(new BoxLayout(weatherPanel, BoxLayout.Y_AXIS)); //også sentrering men horisontalt

        for (int i = 0; i < 9; i++) {                                        //kode kunn for å visualisere grid cellene
            JLabel cell = new JLabel("" + i, SwingConstants.CENTER);    //dette kan slettes når koding er ferdig
            cell.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            gridPanel.add(cell);
        }

        Date date = new Date();     //dato for idag
        Date clock = new Date();    //klokkeslett akkuratt nå

        Calendar calendar = Calendar.getInstance();     //sekvens for å hente dato imorgen
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tmr = calendar.getTime();

        SimpleDateFormat day = new SimpleDateFormat("dd.MM.yyyy");   //format for visning av dato/klokkeslett
        SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");      //dd=dag, MM=måned, HH=24timers, mm=minutt, ss=sekund
        SimpleDateFormat tomorrow = new SimpleDateFormat("dd.MM.yyyy");

        String currentDay = day.format(date);       //bruker laget format
        String currentTime = time.format(clock);
        String tmrDay = tomorrow.format(tmr);

        JLabel timeLabel = new JLabel("<html>I dag:<br>" + currentDay + "<html>", SwingConstants.CENTER);   //for å vise componentene og sentrere den
        timeLabel.setFont(new Font("Day", Font.BOLD, 40));      //enkel redigering av visningen
        JLabel clockLabel = new JLabel(currentTime, SwingConstants.CENTER);
        clockLabel.setFont(new Font("Clock", Font.BOLD, 60));
        JLabel tmrLabel = new JLabel("<html>I Morgen:<br>" + tmrDay + "<html>", SwingConstants.CENTER);
        tmrLabel.setFont(new Font("Tomorrow", Font.BOLD, 40));

        String temp = Weather.getCurrentTemperature();             //bruker metode fra Weather klassen for å hente informasjon med API
        String weather = Weather.getCurrentWeatherCondition();
        ImageIcon weatherIcon = Weather.getWeatherIcon(weather, 200, 200);

        JLabel tempLabel = new JLabel("<html>Temperatur inne: " + "" + "°C" + "<br>Temperatur ute: "
                + temp + "<html>", SwingConstants.CENTER);  //html brukes for å gi tilgang til <br> som gjør ett linjeskift i visningen
        tempLabel.setFont(new Font("Temp", Font.BOLD, 30));
        JLabel weatherLabel = new JLabel("Været i dag: " + weather, SwingConstants.CENTER);
        weatherLabel.setFont(new Font("Weather", Font.BOLD, 30));

        JLabel iconLabel = new JLabel(weatherIcon, SwingConstants.CENTER);

        weatherLabel.setAlignmentX(Component.CENTER_ALIGNMENT); //sentrerer horisontalt
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        weatherPanel.add(weatherLabel);     //tilatter 2 komponenter i samme grid cell
        weatherPanel.add(iconLabel);
        centeredPanel.add(weatherPanel);    //sentrerer verticalt

        Export exporter = new Export();     //lager en instans av Export klassen
        List<Beskjed> beskjederList = exporter.exportBeskjeder(pleietrengende_id);    //henter beskjeder fra databasen

        StringBuilder beskjeder = new StringBuilder("<html>");    //lager en stringbuilder for å legge til beskjeder
        for (Beskjed beskjed : beskjederList) {     //for hver beskjed i listen
            beskjeder.append("").append(beskjed.getDateTime()).append("<br>");  //legger til beskjed og linjeskift
            beskjeder.append("Beskjed: ").append(Cryption.decrypt(beskjed.getDescription(), Cryption.getAESKey())).append("<br>");
        }
        beskjeder.append("</html>");     //avslutter html format

        JLabel beskjedLabel = new JLabel(beskjeder.toString(), SwingConstants.CENTER);    //lager en label med beskjedene
        beskjedLabel.setFont(new Font("Beskjeder", Font.BOLD, 40));    //redigerer fonten

        List<Avtale> avtaleList = exporter.exportAvtalerToday(pleietrengende_id);
        StringBuilder avtaler = new StringBuilder("<html>");
        for (Avtale avtale : avtaleList) {
            avtaler.append("").append(avtale.getDateTime()).append("<br>");
            avtaler.append("Avtale: ").append(Cryption.decrypt(avtale.getDescription(), Cryption.getAESKey())).append("<br>");
        }
        avtaler.append("</html>");

        JLabel avtaleLabel = new JLabel(avtaler.toString(), SwingConstants.CENTER);
        avtaleLabel.setFont(new Font("Avtaler", Font.BOLD, 40));

        List<Avtale> avtaleListTmr = exporter.exportAvtalerTomorrow(pleietrengende_id);
        StringBuilder avtalerTmr = new StringBuilder("<html>");
        for (Avtale avtale : avtaleListTmr) {
            avtalerTmr.append("").append(avtale.getDateTime()).append("<br>");
            avtalerTmr.append("Avtale: ").append(Cryption.decrypt(avtale.getDescription(), Cryption.getAESKey())).append("<br>");
        }
        avtalerTmr.append("</html>");

        JLabel avtaleLabelTmr = new JLabel(avtalerTmr.toString(), SwingConstants.CENTER);
        avtaleLabelTmr.setFont(new Font("AvtalerTmr", Font.BOLD, 40));


        gridPanel.remove(0);        //fjerner tallene som visualiserer grid cellene for å unngå overlapping, kan fjernes når koding er ferdig
        gridPanel.add(timeLabel, 0);    //legger til komponent i grid celle
        gridPanel.remove(1);
        gridPanel.add(clockLabel, 1);
        gridPanel.remove(2);
        gridPanel.add(tmrLabel, 2);
        gridPanel.remove(6);
        gridPanel.add(tempLabel, 6);
        gridPanel.remove(8);
        gridPanel.add(centeredPanel, 8);
        gridPanel.remove(4);
        gridPanel.add(beskjedLabel, 4);
        gridPanel.remove(3);
        gridPanel.add(avtaleLabel, 3);
        gridPanel.remove(5);
        gridPanel.add(avtaleLabelTmr, 5);

        jframe.add(gridPanel);      //Legger til grid cellene i GUI'en
        jframe.setVisible(true);    //Gjør at selve GUI'en vises på maskinen

        Weather.startWeatherUpdater();

        TimerService.startWeatherUpdateTimer(tempLabel, weatherLabel, iconLabel, weatherIcon);
        TimerService.startClockUpdateTimer(timeLabel, clockLabel, tmrLabel);
    }
}