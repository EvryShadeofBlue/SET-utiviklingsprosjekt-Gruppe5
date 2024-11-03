package org.app.core.avtaler;

import jdk.jshell.JShellConsole;
import org.app.core.models.MainPage;
import org.app.core.models.Parorende;
import org.app.core.models.Pleietrengende;

import javax.swing.*;
import java.awt.*;

public class AvtalePage extends JFrame{
    private JTextArea beskrivelsesFelt;
    private JTextField datoFelt;
    private JTextField klokkeslettFelt;
    private JButton lagreKnapp;
    private JPanel avtaleListePanel;
    private AvtaleService avtaleService;
    private Parorende parorende;
    private Pleietrengende pleietrengende;
    private MainPage mainPage;
    private JButton tilbakeKnapp;


    public AvtalePage(AvtaleService avtaleService, Parorende parorende, Pleietrengende pleietrengende, MainPage mainPage) {
        this.avtaleService = avtaleService;
        this.parorende = parorende;
        this.pleietrengende = pleietrengende;
        this.mainPage = mainPage;

        setTitle("Avtaler");
        setSize(400, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel hovedInnholdPanel = new JPanel();
        hovedInnholdPanel.setLayout(new BoxLayout(hovedInnholdPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(hovedInnholdPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(6, 1, 10, 10));

        beskrivelsesFelt = new JTextArea(2, 10);
        beskrivelsesFelt.setLineWrap(true);
        beskrivelsesFelt.setWrapStyleWord(true);
        JScrollPane beskrivelsesPanel = new JScrollPane(beskrivelsesFelt);
        beskrivelsesPanel.setBorder(BorderFactory.createTitledBorder("Beskrivelse"));

        datoFelt = new JTextField();
        JPanel datoPanel = createInputPanel("Dato (yyyy-MM-dd): ", datoFelt);

        klokkeslettFelt = new JTextField();
        JPanel klokkeslettPanel = createInputPanel("Klokkeslett (HH:mm): ", klokkeslettFelt);

        lagreKnapp = new JButton("Lagre");
        lagreKnapp.setPreferredSize(new Dimension(100, 30));
        lagreKnapp.addActionListener(e -> {
            this.dispose();
            mainPage.setVisible(true);
        });

        JPanel tilbakePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tilbakePanel.add(tilbakeKnapp);

        setLayout(new BorderLayout());
        add(tilbakePanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        inputPanel.add(beskrivelsesPanel);
        inputPanel.add(datoPanel);
        inputPanel.add(klokkeslettPanel);
        inputPanel.add(lagreKnapp);
        inputPanel.add(tilbakeKnapp);
        add(inputPanel, BorderLayout.NORTH);

        avtaleListePanel = new JPanel();
        avtaleListePanel.setLayout(new BoxLayout(avtaleListePanel, BoxLayout.Y_AXIS));

        JScrollPane avtaleScrollPane = new JScrollPane(avtaleListePanel);
        avtaleScrollPane.setPreferredSize(new Dimension(400, 300));
        avtaleScrollPane.setBorder(BorderFactory.createTitledBorder("Opprettede avtaler"));
        add(avtaleScrollPane, BorderLayout.CENTER);

        setVisible(true);
    }


    private JPanel createInputPanel(String labelText, JComponent inputField) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel label = new JLabel(labelText);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        inputField.setPreferredSize(new Dimension(300, 30));
        inputField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        inputField.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(label);
        panel.add(inputField);

        return panel;
    }


}
