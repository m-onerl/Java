package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class FileChooserPanel extends JPanel {
    private JTextField filePathField;
    private JButton browseButton;
    private File selectedFile;

    public FileChooserPanel() {
        setLayout(new BorderLayout());

        filePathField = new JTextField();
        filePathField.setEditable(false);
        add(filePathField, BorderLayout.CENTER);

        browseButton = new JButton("Browse");
        browseButton.addActionListener(new BrowseButtonListener());
        add(browseButton, BorderLayout.EAST);
    }

    private class BrowseButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser("C:\\Users\\moner\\Documents\\Projekty\\Java\\sebwol_javatz_2024\\lab09\\football");
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                selectedFile = fileChooser.getSelectedFile();
                filePathField.setText(selectedFile.getAbsolutePath());
            }
        }
    }

    public File getSelectedFile() {
        return selectedFile;
    }
}
