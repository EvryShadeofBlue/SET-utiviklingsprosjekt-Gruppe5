package org.app.core.files;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileHandler {

    public static void writeToFile(String fileName, String content, JFrame parentFrame) {
        try {
            String currentDir = System.getProperty("user.dir");
            File parentDir = new File(currentDir).getParentFile();
            File file = new File(parentDir, fileName);

            try (FileWriter writer = new FileWriter(file)) {
                writer.write(content);
            }

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(parentFrame, "Kunne ikke lagre til fil: "
                    + fileName, "Feil", JOptionPane.ERROR_MESSAGE);
        }
    }

}
