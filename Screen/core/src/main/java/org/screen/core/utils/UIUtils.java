package org.screen.core.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class UIUtils {

    public static JLabel createStyledLabel(String text, String fontName, int style, int size, int alignment) {
        JLabel label = new JLabel(text, alignment);
        label.setFont(new Font(fontName, style, size));
        return label;
    }

    public static void addToGrid(JPanel gridPanel, Component component, int index) {
        gridPanel.remove(index);
        gridPanel.add(component, index);
    }

    public static ImageIcon createScaledImageIcon(String path, int width, int height) {
        try {
            ImageIcon icon = new ImageIcon(UIUtils.class.getResource(path));
            if (icon.getImageLoadStatus() != MediaTracker.COMPLETE) {
                throw new Exception("Image could not be loaded");
            }
            Image img = icon.getImage();
            Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImg);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Could not load image: " + path, "Error", JOptionPane.ERROR_MESSAGE);
            return new ImageIcon(new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB));
        }
    }

}
