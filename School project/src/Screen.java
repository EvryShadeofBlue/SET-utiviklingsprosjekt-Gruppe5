import models.Weather;

import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Screen {
    public static void main(String[] args) {

        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();   //Henter maskinens resolution

        JFrame jframe = new JFrame("Screen");           //lager selve GUI'en
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //stopper 'RUN' om du krysser ut GUI'en
        jframe.setSize(size);       //bruker maskinens resolution

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
        SimpleDateFormat time = new SimpleDateFormat("HH:mm");      //dd=dag, MM=måned, HH=24timers, mm=minutt
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

        String temp = Weather.getTemperature();             //bruker metode fra Weather klassen for å hente informasjon med API
        String weather = Weather.getWeatherCondition();
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

        jframe.add(gridPanel);      //Legger til grid cellene i GUI'en
        jframe.setVisible(true);    //Gjør at selve GUI'en vises på maskinen


        //testing push n pull
    }
}
