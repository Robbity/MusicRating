package ui;

import model.Album;
import model.AlbumDirectory;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class AlbumTrackerUI extends JFrame implements ActionListener {
    private AlbumDirectory albumDirectory;
    private JButton addAlbum;
    private JButton removeAlbum;
//    private JButton clearAlbums;
    private JButton loadDirectory;
    private JButton saveDirectory;
    private JTextField albumName;
    private JTextField albumRating;
    private JCheckBox albumListened;
    private JTable table;
    private static final String JSON_STORE = "./data/albumtrackerUI.json";
    private static final String IMG_STORE = "./data/img.png";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private BufferedImage myPicture;

    // EFFECTS: runs GUI for album tracker program
    public AlbumTrackerUI() {
        super("Album Tracker");
        albumDirectory = new AlbumDirectory("Album Directory");

        setPreferredSize(new Dimension(800, 600));
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        setLayout(new FlowLayout());
        pack();
        setLocationRelativeTo(null);
        setResizable(false);



        // calls to methods
        loadTable();
        loadInput();
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: loads the text boxes on frame
    private void loadInput() {

        JLabel nameLabel = new JLabel("Album Name");
        this.add(nameLabel);
        albumName = new JTextField(15);
        this.add(albumName);

        JLabel ratingLabel = new JLabel("Rating");
        this.add(ratingLabel);
        albumRating = new JTextField(15);
        this.add(albumRating);

        JLabel listenedLabel = new JLabel("Listened?");
        this.add(listenedLabel);
        albumListened = new JCheckBox();
        this.add(albumListened);

        addButtons();

    }

    // MODIFIES: this
    // EFFECTS:
    private void loadTable() {
        String[] columns = {"Name", "Listened", "Rating"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        table.setPreferredScrollableViewportSize(new Dimension(750, 400));
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);
        table.setBounds(50, 0, 450, 500);
        this.add(scrollPane);

        table.setDefaultEditor(Object.class, null);

    }

    // MODIFIES: this
    // EFFECTS: adds a single assessment into a grading scheme
//    private void setupAlbum() {
//        if (if (allowedRating(Integer.parseInt(albumRating.getText())) && !invalidString(albumRating.getText())
//                && !invalidString(albumName.getText()) && albumRating.getText().matches("^[0-9]*$")) {
//            if (albumListened.isSelected()) {
//                Album a = new Album(albumName.getText(), Integer.parseInt(albumRating.getText()));
//                albumDirectory.addNewAlbum(albumName.getText(), Integer.parseInt(albumRating.getText()));
//                addToTable(a);
//            } else {
//                Album a = new Album(albumName.getText(), -1);
//                albumDirectory.addNewAlbum(albumName.getText(), -1);
//                addToTable(a);
//            }
//        } else {
//            JOptionPane.showMessageDialog(this, "Invalid input!");
//        }
//    }

    private void setupAlbum() {
        if (!albumListened.isSelected()) {
            if (!invalidString(albumRating.getText())) {
                Album a = new Album(albumName.getText(), -1);
                albumDirectory.addNewAlbum(a);
                addToTable(a);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid album name input!");
            }
        } else {
            if (ratingCheck(albumRating)) {
                Album a = new Album(albumName.getText(), Integer.parseInt(albumRating.getText()));
                albumDirectory.addNewAlbum(a);
                addToTable(a);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid input!");
            }
        }
    }

    private void deleteAlbum() {
        DefaultTableModel model = (DefaultTableModel) this.table.getModel();
        int[] rows = table.getSelectedRows();
        for (int i = 0; i < rows.length; i++) {
            model.removeRow(rows[i] - i);
        }
    }

    // MODIFIES: this
    // EFFECTS:
    private void addButtons() {
        addAlbum = new JButton("Add Album");
        addAlbum.addActionListener(this);
        this.add(addAlbum);

        removeAlbum = new JButton("Remove Album");
        removeAlbum.addActionListener(this);
        this.add(removeAlbum);

//        clearAlbums = new JButton("Clear");
//        clearAlbums.addActionListener(this);
//        this.add(clearAlbums);

        saveDirectory = new JButton("Save");
        saveDirectory.addActionListener(this);
        this.add(saveDirectory);

        loadDirectory = new JButton("Load");
        loadDirectory.addActionListener(this);
        this.add(loadDirectory);
    }

    // MODIFIES: this
    // EFFECTS: adds the assessment to the table
    private void addToTable(Album a) {
        Object[] data;
        if (a.getRating() == -1) {
            data = new Object[]{a.getName(), "No", "N/A"};
        } else {
            data = new Object[]{a.getName(), "Yes", a.getRating()};
        }
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addRow(data);
    }

    private void removeFromTable(Album a) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        for (int i = model.getRowCount() - 1; i >= 0; i--) {
            if (model.getValueAt(i, 0).equals(a.getName())) {
                albumDirectory.removeNewAlbum(a);
                model.removeRow(i);
//                i -= 1;
            }
        }
    }

    // EFFECTS: finds the action performed on the GUI
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loadDirectory) {
            loadDirectory();
        }
        if (e.getSource() == saveDirectory) {
            saveDirectory();
        }
        if (e.getSource() == addAlbum) {
            setupAlbum();
        }

        if (e.getSource() == removeAlbum) {
            deleteAlbum();
        }

//        if (e.getSource() == clearButton) {
//            clearAllAssessments();
//        }
    }

    // CITATION: Modified from JsonSerializationDemo JsonWriter file
    // MODIFIES: this
    // EFFECTS: loads album directory from file
    private void loadDirectory() {
        // need to clear table
        try {
            albumDirectory = jsonReader.read();
            for (Album a : albumDirectory.getAlbums()) {
                addToTable(a);
            }
            JOptionPane.showMessageDialog(this, "Loaded " + albumDirectory.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Unable to read from file: " + JSON_STORE);
        }
    }


    // CITATION: Modified from JsonSerializationDemo JsonWriter file
    // MODIFIES: jsonWriter
    // EFFECTS: saves the album directory to file
    private void saveDirectory() {
//        albumDirectory.recentSortAlbums();
        try {
            jsonWriter.open();
            jsonWriter.write(albumDirectory);
            jsonWriter.close();
            JOptionPane.showMessageDialog(this, "Saved " + albumDirectory.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Unable to write to file: " + JSON_STORE);
        }
    }

    // Helpers

    private Boolean allowedRating(int num) {
        return (num >= 0 && num <= 10);
    }

    // EFFECTS: checks to see if the string input is valid and not empty or blank
    private Boolean invalidString(String str) {
        return (str.replaceAll("\\s", "").equals(""));
    }

    // EFFECTS: checks to see if string is a number or contains text
    private Boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private Boolean ratingCheck(JTextField albumRating) {
        if (!isNumeric(albumRating.getText())) {
            return false;
        } else {
            return allowedRating(Integer.parseInt(albumRating.getText())) && !invalidString(albumRating.getText())
                   && albumRating.getText().matches("^[0-9]*$");
        }
    }

}
