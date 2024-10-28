package org.screen.core.models;

public class Resources {
    public static final String API_WEATHER = "b21e588b0b143dc98fdc0834fb90535e";
    public static final String url = "jdbc:mysql://itstud.hiof.no:3306/tek2024_g5";
    public static final String user = "tobiashg";
    public static final String password = "Sommer2025";

    public static String getApiWeather() {
        return API_WEATHER;
    }

    public static String getUrl() {
        return url;
    }

    public static String getUser() {
        return user;
    }

    public static String getPassword() {
        return password;
    }
}