package org.screen.core.models;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileHandling {

    public static int readPleietrengendeIdFromFile() {
        String currentDir = System.getProperty("user.dir");
        File parentDir = new File(currentDir).getParentFile();
        File file = new File(parentDir, "pleietrengende_id.txt");

        if (!file.exists()) {
            System.err.println("File not found: " + file.getAbsolutePath());
            return -1;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            return Integer.parseInt(line);
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            return -1;
        }
    }

}
