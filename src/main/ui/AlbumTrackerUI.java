package ui;

import model.Album;
import model.AlbumDirectory;
import model.Event;
import model.EventLog;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;

// Album Tracker Application with a User Interface
public class AlbumTrackerUI extends JFrame implements ActionListener {
    private AlbumDirectory albumDirectory;
    private JButton addAlbum;
    private JButton removeAlbum;
    private JButton loadDirectory;
    private JButton saveDirectory;
    private JTextField albumName;
    private JTextField albumRating;
    private JCheckBox albumListened;
    private JTable table;
    private static final String JSON_STORE = "./data/albumtrackerUI.json";
    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;

    // EFFECTS: runs UI for album tracker program
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
        showSplash();
        loadTable();
        loadInput();
        setVisible(true);

        eventListener();
    }

    // EFFECTS: provides splash screen, with duration of 3000ms
    private void showSplash() {
        JPanel content = (JPanel)getContentPane();
        JLabel label = new JLabel(new ImageIcon("./data/splashscreen.png"));
        content.add(label, BorderLayout.CENTER);
        setVisible(true);

        // Wait time on splash screen
        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            //pass
        }

        content.remove(label);
        setVisible(false);

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
    // EFFECTS: loads an empty table of albums with a name, listened status, and rating
    private void loadTable() {
        String[] columns = {"Name", "Listened", "Rating"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);

        table = new JTable(tableModel);
        table.setPreferredScrollableViewportSize(new Dimension(750, 400));
        table.setFillsViewportHeight(true);

        JScrollPane scroll = new JScrollPane(table);
        this.add(scroll);
        table.setBounds(0, 0, 400, 400);
        table.setDefaultEditor(Object.class, null);

    }

    // MODIFIES: this
    // EFFECTS: determines based on the input whether an album can be added, if it can be
    //          then the album is added to both the table and the album directory
    private void setupAlbum() {
        // Album has not been listened to
        if (!albumListened.isSelected()) {
            if (!invalidString(albumName.getText())) {
                Album a = new Album(albumName.getText(), -1);
                albumDirectory.addNewAlbum(a);
                addToTable(a);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid album name input!");
            }
            // Album has been listened to
        } else {
            if (ratingCheck(albumRating)) {
                if (!invalidString(albumName.getText())) {
                    Album a = new Album(albumName.getText(), Integer.parseInt(albumRating.getText()));
                    albumDirectory.addNewAlbum(a);
                    addToTable(a);
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid album name input!");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid rating input!");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: removes the album from both the album directory and the table
    private void deleteAlbum() {

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int[] rows = table.getSelectedRows();

        for (int i = 0; i < rows.length; i++) {
            albumDirectory.removeIndex(rows[i] - i);
            model.removeRow(rows[i] - i);
        }
    }

    // MODIFIES: this
    // EFFECTS: creates new buttons for each of the JButton fields, adding an action listener
    //          and adding each button to this
    private void addButtons() {
        addAlbum = new JButton("Add Album");
        addAlbum.addActionListener(this);
        this.add(addAlbum);

        removeAlbum = new JButton("Remove Album");
        removeAlbum.addActionListener(this);
        this.add(removeAlbum);

        saveDirectory = new JButton("Save");
        saveDirectory.addActionListener(this);
        this.add(saveDirectory);

        loadDirectory = new JButton("Load");
        loadDirectory.addActionListener(this);
        this.add(loadDirectory);
    }

    // MODIFIES: this
    // EFFECTS: determines the how to display the album data on the table, depending on rating value
    //          then adds the album to the table
    private void addToTable(Album a) {
        Object[] albumdata;
        if (a.getRating() == -1) {
            albumdata = new Object[]{a.getName(), "No", "N/A"};
        } else {
            albumdata = new Object[]{a.getName(), "Yes", a.getRating()};
        }
        DefaultTableModel model = (DefaultTableModel) table.getModel();

        model.addRow(albumdata);
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
    }

    // CITATION: Modified from JsonSerializationDemo JsonWriter file
    // MODIFIES: this
    // EFFECTS: loads album directory from JSON file
    private void loadDirectory() {
        clearTable();
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
    // EFFECTS: saves the album directory to JSON file
    private void saveDirectory() {
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

    // MODIFIES: this
    // EFFECTS: removes all assessments in the grading scheme
    private void clearTable() {
        DefaultTableModel dtm = (DefaultTableModel) table.getModel();
        dtm.setRowCount(0);
    }

    // EFFECTS: check that integer is within specific bounds
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
            Double.parseDouble(strNum);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    // EFFECTS: performs multiple rating checks on fields to see that rating is valid
    private Boolean ratingCheck(JTextField albumRating) {
        if (!isNumeric(albumRating.getText())) {
            return false;
        } else {
            try {
                return allowedRating(Integer.parseInt(albumRating.getText())) && !invalidString(albumRating.getText())
                        && albumRating.getText().matches("^[0-9]*$");
            } catch (NumberFormatException e) {
                return false;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a window listener for if the window is closed, then exits with code 0
    private void eventListener() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                printLog(EventLog.getInstance());
                System.exit(0);
            }
        });
    }

    // EFFECTS: prints log of events occurred during program instance
    private void printLog(EventLog log) {
        System.out.println("All Events Logged: ");
        for (Event e : log) {
            System.out.println(e);
        }
    }

}
