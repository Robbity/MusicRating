package ui;

import model.Album;
import model.AlbumDirectory;

import java.util.*;

// Album Tracker application
public class AlbumTracker {
    private Scanner input;
    private AlbumDirectory albumDirectory;


    // EFFECTS: runs album tracker application
    public AlbumTracker() {
        runTracker();
    }

    // MODIFIES: THIS
    // EFFECTS: processes user input
    private void runTracker() {
        boolean keepGoing = true;
        String command = null;

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
        albumDirectory = new AlbumDirectory();
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("l")) {
            addAlbum();
        } else if (command.equals("r")) {
            removeAlbum();
        } else if (command.equals("v")) {
            viewRecent();
        } else if (command.equals("b")) {
            viewHighest();
        } else if (command.equals("8")) {
            rateAlbum();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tl -> log new album");
        System.out.println("\tr -> remove logged album");
        System.out.println("\tv -> view log");
        System.out.println("\tb -> view rating sorted log");
        System.out.println("\t8 -> rate album");
        System.out.println("\tq -> quit application");
    }

    // EFFECTS: adds album to end of list of current albums
    private void addAlbum() {
        Boolean listened = false;
        int rating = -1;
        System.out.println("Enter album name:");
        String name = input.next();
        System.out.println("Have you listened to it yet?");
        listened = input.nextBoolean();
        if (listened) {
            System.out.println("What would you rate the album?");
            rating = input.nextInt();
        }
        albumDirectory.addNewAlbum(name, rating);
        System.out.println("Your album has been added to the list!");
    }

    // EFFECTS: removes album input (by name) from list of albums
    private void removeAlbum() {
        ArrayList<Album> recentAlbums = albumDirectory.getAlbums();

        System.out.println("Enter album name for removal:");
        String name = input.next();

        for (Album album : recentAlbums) {
            if (name.equals(album.getName())) {
                albumDirectory.removeNewAlbum(album);
            }
        }
    }

    // EFFECTS: views the list of albums starting with
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

    // EFFECTS: views the list of albums starting with
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

    // EFFECTS: removes album input (by name) from list of albums
    private void rateAlbum() {
        ArrayList<Album> recentAlbums = albumDirectory.getAlbums();

        System.out.println("Enter album name to be rated:");
        String name = input.next();
        System.out.println("Enter it's new rating:");
        int rating = input.nextInt();

        for (Album album : recentAlbums) {
            if (name.equals(album.getName())) {
                album.rateAlbum(rating);
            }
        }
    }





    // VARIOUS HELPERS AND GETTERS

//    private Boolean nextYesNo(String str) {
//        if (str == "y" || str == "yes") {
//            return true;
//        }
//        if (str == "n" || str == "no") {
//            return false;
//        }
//    }

//    // EFFECTS: checks to see if the string input is valid and not empty or blank
//    private Boolean validString(String str) {
//        return (str.replaceAll("\\s", "") == "");
//    }
//
//    // EFFECTS: determines if the album is already in the album list
//    private Boolean uniqueAlbum(String albumName) {
//        return albumList.contains(albumName);
//    }



}
