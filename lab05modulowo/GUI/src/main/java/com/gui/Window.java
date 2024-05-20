package com.gui;

import com.accuracy.AccuracyStatistic;
import com.adapter.AnalysisAdapter;
import com.kappa.KappaStatistic;
import ex.api.AnalysisException;
import ex.api.AnalysisService;
import ex.api.DataSet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class Window extends JFrame {
    private final JTextArea dataArea = new JTextArea(10, 30);
    private final JComboBox<String> algorithmBox = new JComboBox<>(new String[]{"Kappa", "Accuracy"});
    private final JButton calculateButton = new JButton("Calculate");
    private final JTextArea resultArea = new JTextArea(5, 30);

    public Window() {
        super("Matrix Analyzer");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);

        resultArea.setEditable(false);

        JPanel dataPanel = new JPanel();
        dataPanel.add(new JScrollPane(dataArea));
        add(dataPanel, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        controlPanel.add(algorithmBox);
        controlPanel.add(calculateButton);
        add(controlPanel, BorderLayout.NORTH);

        JPanel resultPanel = new JPanel();
        resultPanel.add(new JScrollPane(resultArea));
        add(resultPanel, BorderLayout.SOUTH);

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedAlgorithm = (String) algorithmBox.getSelectedItem();
                performCalculation(selectedAlgorithm);
            }
        });

        setVisible(true);
    }

    private DataSet createDataSetFromInput(String text) {
        String[] lines = text.split("\n");
        String[][] matrix = new String[lines.length][];
        for (int i = 0; i < lines.length; i++) {
            matrix[i] = lines[i].split(",");
            System.out.println(Arrays.toString(matrix[i]));
        }
        DataSet dataSet = new DataSet();
        dataSet.setData(matrix);
        return dataSet;
    }

    private void performCalculation(String algorithmName) {
        try {
            DataSet dataSet = createDataSetFromInput(dataArea.getText());
            AnalysisService algorithm = new AnalysisAdapter(getAlgorithmInstance(algorithmName));
            algorithm.submit(dataSet);
            new Thread(() -> {
                try {
                    DataSet results;
                    do {
                        results = algorithm.retrieve(false);
                        Thread.sleep(1000);
                    } while (results == null);

                    double result = ((AnalysisAdapter) algorithm).analyze();
                    String formattedResult = String.format("Wynik: %.3f", result);
                    SwingUtilities.invokeLater(() -> resultArea.setText(formattedResult));
                } catch (Exception e) {
                    SwingUtilities.invokeLater(() -> resultArea.setText("Błąd przy pobieraniu wyników: " + e.getMessage()));
                }
            }).start();
        } catch (AnalysisException e) {
            resultArea.setText("Error: " + e.getMessage());
        }
    }



    private AnalysisService getAlgorithmInstance(String name) throws IllegalArgumentException {
        switch (name) {
            case "Kappa":
                return new KappaStatistic();
            case "Accuracy":
                return new AccuracyStatistic();
            default:
                throw new IllegalArgumentException("Unknown algorithm: " + name);
        }
    }


    public static void main(String[] args) {
        new Window();
    }
}
