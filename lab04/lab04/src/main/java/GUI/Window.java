package GUI;

import ClassLoader.CustomClassLoader;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.lang.reflect.Field;
import java.util.List;
import javax.swing.SwingWorker;



public class Window extends JFrame {
    private JButton loadClassButton, deleteClassButton, executeButton;
    private JTable classTable;
    private DefaultTableModel model;
    private JScrollPane scrollPane;
    private JTextField inputField;
    private JLabel inputLabel;
    private CustomClassLoader classLoader;
    private JTextArea resultArea;
    private JScrollPane resultScrollPane;
    private JList<String> taskMonitorList;
    private DefaultListModel<String> taskMonitorListModel;

    public Window() {
        setTitle("Class Loader");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initComponents();

        try {
            File classDir = new File("C:\\Users\\moner\\Klasy");
            classLoader = new CustomClassLoader(Paths.get(classDir.getPath()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initComponents() {
        getContentPane().setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel tablePanel = new JPanel(new BorderLayout());
        classTable = new JTable();
        model = new DefaultTableModel(new Object[]{"Class Name", "Info"}, 0);
        classTable.setModel(model);
        scrollPane = new JScrollPane(classTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(tablePanel);


        JPanel loadingPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel loadingLabel = new JLabel("Loading");
        loadingPanel.add(loadingLabel);
        mainPanel.add(loadingPanel);

        taskMonitorListModel = new DefaultListModel<>();
        taskMonitorList = new JList<>(taskMonitorListModel);
        JScrollPane taskMonitorScrollPane = new JScrollPane(taskMonitorList);
        taskMonitorScrollPane.setPreferredSize(new Dimension(200, 100));
        mainPanel.add(taskMonitorScrollPane);


        resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);
        resultScrollPane = new JScrollPane(resultArea);
        resultScrollPane.setBorder(BorderFactory.createTitledBorder("Results"));
        mainPanel.add(resultScrollPane);


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        loadClassButton = new JButton("Load Class");
        deleteClassButton = new JButton("Delete Class");
        executeButton = new JButton("Execute Task");
        buttonPanel.add(loadClassButton);
        buttonPanel.add(deleteClassButton);
        buttonPanel.add(executeButton);
        mainPanel.add(buttonPanel);


        add(mainPanel, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        inputField = new JTextField(20);
        inputLabel = new JLabel("Input:");
        inputPanel.add(inputLabel);
        inputPanel.add(inputField);
        add(inputPanel, BorderLayout.SOUTH);


        loadClassButton.addActionListener(e -> loadClassesAction(e));
        deleteClassButton.addActionListener(e -> deleteSelectedClass());
        executeButton.addActionListener(e -> executeTask(inputField.getText()));
    }


    private void loadClassesAction(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("C:\\Users\\moner\\Klasy"));
        fileChooser.setDialogTitle("Select a directory or class file");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        fileChooser.setAcceptAllFileFilterUsed(false);

        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            loadOrDisplayClasses(selectedFile);
        }
    }

    private void loadOrDisplayClasses(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File subFile : files) {
                    loadOrDisplayClasses(subFile);
                }
            }
        } else if (file.isFile() && file.getName().endsWith(".class")) {

            String className = convertPathToClassName(file.getPath());
            try {
                Class<?> cls = classLoader.loadClass(className);
                String actionInfo = getActionInfo(cls);
                model.addRow(new Object[]{cls.getName(), actionInfo});
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }


        }
    }

    private String convertPathToClassName(String path) {
        String classPath = Paths.get("C:\\Users\\moner\\Klasy").toString();
        String className = path.substring(classPath.length() + 1).replace(File.separatorChar, '.');
        return className.substring(0, className.length() - ".class".length());
    }

    private String getActionInfo(Class<?> cls) {
        try {
            Field descriptionField = cls.getField("DESCRIPTION");
            return (String) descriptionField.get(null);
        } catch (NoSuchFieldException e) {
            return "Cannot find description.";
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return "Error with access to description.";
        }
    }


    private void deleteSelectedClass() {

        int selectedRow = classTable.getSelectedRow();

        if (selectedRow >= 0) {
            model.removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(this, "Choose class to delete");
        }
    }

    private void executeTask(String task) {
        int selectedRow = classTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a class first.", "Selection Missing", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String className = (String) model.getValueAt(selectedRow, 0);

        SwingWorker<String, Integer> worker = new SwingWorker<String, Integer>() {
            @Override
            protected String doInBackground() throws Exception {
                int totalSteps = 10;
                for (int i = 1; i <= totalSteps; i++) {
                    Thread.sleep(100);
                    int progress = (i * 100) / totalSteps;
                    publish(progress);
                }

                Class<?> cls = classLoader.loadClass(className);
                Method method = cls.getMethod("executeTask", String.class);
                Object instance = cls.getDeclaredConstructor().newInstance();
                return (String) method.invoke(instance, task);
            }

            @Override
            protected void process(List<Integer> chunks) {
                int latestProgress = chunks.get(chunks.size() - 1);
                taskMonitorListModel.addElement(className + " progress: " + latestProgress + "%");
            }

            @Override
            protected void done() {
                try {
                    String result = get();
                    resultArea.append(className + ": " + result + "\n");
                    taskMonitorListModel.addElement(className + " task completed.");
                } catch (Exception e) {
                    e.printStackTrace();
                    taskMonitorListModel.addElement("Error performing task for: " + className);
                }
            }
        };

        worker.execute();
    }
}
