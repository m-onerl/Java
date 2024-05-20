package gui;

import jaxb.JAXBParser;
import jaxp.DOMParser;
import jaxp.SAXParser;
import xslt.XSLTTransformer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MainFrame extends JFrame {
    private JTextArea textArea;
    private JComboBox<String> methodComboBox;
    private JButton loadButton, transformButton;
    private FileChooserPanel fileChooserPanel;

    public MainFrame() {
        setTitle("XML Processor");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        textArea = new JTextArea();
        textArea.setEditable(false);
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BorderLayout());

        fileChooserPanel = new FileChooserPanel();
        controlPanel.add(fileChooserPanel, BorderLayout.NORTH);

        JPanel methodPanel = new JPanel();
        methodPanel.setLayout(new FlowLayout());

        methodComboBox = new JComboBox<>(new String[]{"JAXB", "DOM", "SAX"});
        methodPanel.add(methodComboBox);

        loadButton = new JButton("Load XML");
        loadButton.addActionListener(new LoadButtonListener());
        methodPanel.add(loadButton);

        transformButton = new JButton("Transform XML");
        transformButton.addActionListener(new TransformButtonListener());
        methodPanel.add(transformButton);

        controlPanel.add(methodPanel, BorderLayout.SOUTH);
        add(controlPanel, BorderLayout.SOUTH);
    }

    private class LoadButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            File selectedFile = fileChooserPanel.getSelectedFile();
            if (selectedFile != null) {
                String method = (String) methodComboBox.getSelectedItem();
                if (method != null) {
                    switch (method) {
                        case "JAXB":
                            textArea.setText(JAXBParser.parse(selectedFile));
                            break;
                        case "DOM":
                            textArea.setText(DOMParser.parse(selectedFile));
                            break;
                        case "SAX":
                            textArea.setText(SAXParser.parse(selectedFile));
                            break;
                    }
                }
            }
        }
    }

    private class TransformButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            File selectedFile = fileChooserPanel.getSelectedFile();
            if (selectedFile != null) {
                String result = XSLTTransformer.transform(selectedFile, "transform.xsl");
                textArea.setText(result);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
