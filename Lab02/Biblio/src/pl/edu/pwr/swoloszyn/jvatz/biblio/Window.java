package pl.edu.pwr.swoloszyn.jvatz.biblio;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.table.DefaultTableModel;
import java.lang.ref.WeakReference;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.HashMap;
public class Window extends JFrame {
    private JTree directoryTree;
    private JTable csvTable;
    private JLabel averageValueLabel;
    private JLabel dataSourceLabel;
    private JPanel rightPanel;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private HashMap<String, WeakReference<Object[]>> dataCache = new HashMap<>();


    public Window() {
        super("Average");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);


        DefaultMutableTreeNode rootNode;
        DefaultTreeModel treeModel;

        File directoryRoot = new File("C:\\");
        rootNode = new DefaultMutableTreeNode(new WeakDirectoryNode(directoryRoot));
        treeModel = new DefaultTreeModel(rootNode);

        dataSourceLabel = new JLabel("Data Source: Not loaded");
        dataSourceLabel.setHorizontalAlignment(JLabel.CENTER);
        directoryTree = new JTree(treeModel);
        directoryTree.setShowsRootHandles(true);
        directoryTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        directoryTree.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) directoryTree.getLastSelectedPathComponent();
            if (node != null && node.getUserObject() instanceof WeakDirectoryNode) {
                WeakDirectoryNode selectedNode = (WeakDirectoryNode) node.getUserObject();
                File file = selectedNode.getFile();
                if (file.isFile()) {
                    if (file.getName().toLowerCase().endsWith(".csv")) {
                        loadCsvFileInBackground(file);
                    } else {
                        JOptionPane.showMessageDialog(Window.this, "The selected file is not a file with the CSV extension.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        JScrollPane directoryScrollPane = new JScrollPane(directoryTree);
        directoryScrollPane.setPreferredSize(new Dimension(300, getHeight()));


        DefaultTableModel tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableModel.addColumn("Measurement time");
        tableModel.addColumn("Pressure [hPa]");
        tableModel.addColumn("Temperature [°C]");
        tableModel.addColumn("Humidity [%]");

        csvTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(csvTable);

        averageValueLabel = new JLabel("Average value:");

        setLayout(new BorderLayout());

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(directoryScrollPane, BorderLayout.CENTER);

        JPanel labelPanel = new JPanel(new GridLayout(2, 1));
        labelPanel.add(averageValueLabel);
        labelPanel.add(dataSourceLabel);

        rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(tableScrollPane, BorderLayout.CENTER);
        rightPanel.add(labelPanel, BorderLayout.SOUTH);

        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);

        setVisible(true);

        populateTree(directoryRoot, rootNode);
    }

    private void populateTree(File directoryRoot, DefaultMutableTreeNode node) {
        File[] files = directoryRoot.listFiles();
        if (files != null) {
            for (File file : files) {
                DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(new WeakDirectoryNode(file));
                node.add(childNode);
                if (file.isDirectory()) {
                    populateTree(file, childNode);
                }
            }
        }
    }

    private void loadCsvFileInBackground(File csvFile) {
        executor.submit(() -> {
            try {
                loadCsvFile(csvFile);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    private void loadCsvFile(File csvFile) throws IOException {
        String filePath = csvFile.getAbsolutePath();
        WeakReference<Object[]> cachedDataRef = dataCache.get(filePath);

        if (cachedDataRef != null && cachedDataRef.get() != null) {
            Object[] cachedData = cachedDataRef.get();
            dataSourceLabel.setText("Data source: Cache memory");
            List<Double> pressure = (List<Double>) cachedData[1];
            List<Double> temperature = (List<Double>) cachedData[2];
            List<Double> humidity = (List<Double>) cachedData[3];
            List<String> timeStamps = (List<String>) cachedData[0];
            updateTableAndLabel(pressure, temperature, humidity);
            displayInTable(timeStamps, pressure, temperature, humidity);
        } else {
            dataSourceLabel.setText("Data Source: New load from file");
            Object[] data = CsvFileManager.loadCsvFile(csvFile);
            List<String> timeStamps = (List<String>) data[0];
            List<Double> pressure = (List<Double>) data[1];
            List<Double> temperature = (List<Double>) data[2];
            List<Double> humidity = (List<Double>) data[3];
            dataCache.put(filePath, new WeakReference<>(data));
            updateTableAndLabel(pressure, temperature, humidity);
            displayInTable(timeStamps, pressure, temperature, humidity);
        }
    }

    private void updateTableAndLabel(List<Double> pressureValues, List<Double> temperatureValues, List<Double> humidityValues) {
        double averagePressure = calculateAverage(pressureValues);
        double averageTemperature = calculateAverage(temperatureValues);
        double averageHumidity = calculateAverage(humidityValues);

        String formattedLabel = String.format("Average pressure: %.3f hPa, Average temperature: %.3f °C, Average humidity: %.3f%%", averagePressure, averageTemperature, averageHumidity);
        averageValueLabel.setText(formattedLabel);


    }

    private double calculateAverage(List<Double> values) {
        if (values.isEmpty()) return 0.0;
        double sum = 0.0;
        for (Double value : values) {
            sum += value;
        }
        return sum / values.size();
    }


    private void displayInTable(List<String> timeStamps, List<Double> pressure, List<Double> temperature, List<Double> humidity) {
        SwingUtilities.invokeLater(() -> {
            DefaultTableModel model = (DefaultTableModel) csvTable.getModel();
            model.setRowCount(0);
            for (int i = 0; i < timeStamps.size(); i++) {
                model.addRow(new Object[]{
                        timeStamps.get(i),
                        pressure.get(i),
                        temperature.get(i),
                        humidity.get(i)
                });
            }
            csvTable.setModel(model);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Window::new);
    }
}

class WeakDirectoryNode {
    private final File file;

    public WeakDirectoryNode(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    @Override
    public String toString() {
        return file.getName().isEmpty() ? file.getAbsolutePath() : file.getName();
    }
}
