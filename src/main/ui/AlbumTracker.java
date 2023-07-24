package ui;

import model.Album;
import model.AlbumDirectory;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

// CITATION: Lines 9 -> 85 based on TellerApp application design
// Album Tracker application
public class AlbumTracker {
    private static final String JSON_STORE = "./data/albumtracker.json";
    private Scanner input;
    private AlbumDirectory albumDirectory;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;


    // EFFECTS: runs album tracker application
    public AlbumTracker() {
        runTracker();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runTracker() {
        boolean keepGoing = true;
        String command;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: initializes album list
    private void init() {
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        albumDirectory = new AlbumDirectory("Robbie's Album List");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("a")) {
            addAlbum();
        } else if (command.equals("r")) {
            removeAlbum();
        } else if (command.equals("v")) {
            viewRecent();
        } else if (command.equals("b")) {
            viewHighest();
        } else if (command.equals("8")) {
            rateAlbum();
        } else if (command.equals("s")) {
            saveAlbumDirectory();
        } else if (command.equals("l")) {
            loadAlbumDirectory();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> add new album");
        System.out.println("\tr -> remove logged album");
        System.out.println("\tv -> view log");
        System.out.println("\tb -> view rating sorted log");
        System.out.println("\t8 -> rate album");
        System.out.println("\ts -> save album directory to file");
        System.out.println("\tl -> load album directory from file");
        System.out.println("\tq -> quit application");
    }

    // EFFECTS: determines correct input / if album has already been added
    private void addAlbum() {
        System.out.println("Enter album name:");
        String name = input.next();
        if (invalidString(name)) {
            System.out.println("Input not valid...");
        } else if (doesAlbumExist(name)) {
            System.out.println("Album has already been added!");
        } else {
            validAlbumInput(name);
        }
    }

    // MODIFIES: this
    // EFFECTS: adds album from using album name
    private void validAlbumInput(String name) {
        int rating;
        String listened;
        System.out.println("Have you listened to it yet? (y/n)");
        listened = input.next();
        if (listened.equals("y") || listened.equals("yes")) {
            System.out.println("What would you rate the album?");
            rating = input.nextInt();
            if (allowedRating(rating)) {
                albumDirectory.addNewAlbum(name, rating);
                System.out.println("Your album has been added to the list!");
            } else {
                System.out.println("Please enter a valid rating from 0-10!");
            }
        } else if (listened.equals("n") || listened.equals("no")) {
            rating = -1;
            albumDirectory.addNewAlbum(name, rating);
            System.out.println("Your album has been added to the list!");
        } else {
            System.out.println("Please make a valid input!");
        }
    }

    // MODIFIES: this
    // EFFECTS: removes album input (by name) from list of albums
    private void removeAlbum() {
        ArrayList<Album> recentAlbums = albumDirectory.getAlbums();

        System.out.println("Enter album name for removal:");
        String name = input.next();
        if (invalidString(name)) {
            System.out.println("Input not valid...");
        } else if (doesAlbumExist(name)) {
            for (int i = recentAlbums.size() - 1; i > -1; i--) {
                Album album = recentAlbums.get(i);
                if (name.equals(album.getName())) {
                    albumDirectory.removeNewAlbum(album);
                }
            }
            System.out.println("Your album has been removed.");
        } else {
            System.out.println("That album does not exist!");
        }
    }

    // MODIFIES: this
    // EFFECTS: views the list of albums starting with the most recent album
    private void viewRecent() {
        albumDirectory.recentSortAlbums();
        ArrayList<Album> recentAlbums = albumDirectory.getAlbums();
        for (int i = recentAlbums.size() - 1; i > -1; i--) {
            Album album = recentAlbums.get(i);
            String albumString;
            if (album.getRating() == -1) {
                albumString = String.format("%s - UNLISTENED", album.getName());
            } else {
                albumString = String.format("%s - %d/10", album.getName(), album.getRating());
            }
            System.out.println(albumString);
        }
    }

    // MODIFIES: this
    // EFFECTS: views the list of albums starting with the highest rated album
    private void viewHighest() {
        albumDirectory.rateSortAlbums();
        ArrayList<Album> recentAlbums = albumDirectory.getAlbums();
        for (int i = recentAlbums.size() - 1; i > -1; i--) {
            Album album = recentAlbums.get(i);
            String albumString;
            if (album.getRating() == -1) {
                albumString = String.format("%s - UNLISTENED", album.getName());
            } else {
                albumString = String.format("%s - %d/10", album.getName(), album.getRating());
            }
            System.out.println(albumString);
        }
    }

    // MODIFIES: this
    // EFFECTS: adjusts the rating of input album (by name)
    private void rateAlbum() {
        ArrayList<Album> recentAlbums = albumDirectory.getAlbums();

        System.out.println("Enter album name to be rated:");
        String name = input.next();
        if (invalidString(name)) {
            System.out.println("Input not valid...");
        } else if (!doesAlbumExist(name)) {
            System.out.println("Album does not exist.");
        } else {
            System.out.println("Enter it's new rating:");
            int rating = input.nextInt();

            if (allowedRating(rating)) {

                for (Album album : recentAlbums) {
                    if (name.equals(album.getName())) {
                        album.rateAlbum(rating);
                    }
                }
                System.out.println("Your rating has been changed.");
            } else {
                System.out.println("Please enter a valid rating from 0-10!");
            }
        }
    }

    // CITATION: Modified from JsonSerializationDemo JsonWriter file
    // EFFECTS: saves the album directory to file
    private void saveAlbumDirectory() {
        albumDirectory.recentSortAlbums();
        try {
            jsonWriter.open();
            jsonWriter.write(albumDirectory);
            jsonWriter.close();
            System.out.println("Saved " + albumDirectory.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // CITATION: Modified from JsonSerializationDemo JsonWriter file
    // MODIFIES: this
    // EFFECTS: loads album directory from file
    private void loadAlbumDirectory() {
        try {
            albumDirectory = jsonReader.read();
            System.out.println("Loaded " + albumDirectory.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }





    // VARIOUS HELPERS AND GETTERS

    // EFFECTS: determines whether the number input is within the bounds
    private Boolean allowedRating(int num) {
        return (num >= 0 && num <= 10);
    }

    // EFFECTS: checks to see if the string input is valid and not empty or blank
    private Boolean invalidString(String str) {
        return (str.replaceAll("\\s", "").equals(""));
    }

    // EFFECTS: determines if the album is already in the album list
    private Boolean doesAlbumExist(String albumName) {
        List<String> names = new ArrayList<>();
        for (Album album : albumDirectory.getAlbums()) {
            names.add(album.getName());
        }
        return names.contains(albumName);
    }

}
