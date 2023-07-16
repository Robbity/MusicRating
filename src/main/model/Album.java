package model;

// Represents an album with a name and a rating
public class Album {
    private String name;
    private int rating;
    private Boolean listened;

    // REQUIRES: initialRating has 0 <= val <= 10
    // EFFECTS: name of album is set to albumName;
    //          rating of album is set to the
    //          initialRating;
    public Album(String albumName, int initialRating, Boolean haveListened) {
        name = albumName;
        rating = initialRating;
        listened = haveListened;
    }

    // EFFECTS: name of album is set to albumName;
    //          rating has default value of -1 to
    //          show that it has been unrated
    public Album(String albumName) {
        name = albumName;
        rating = -1;
        listened = false;

    }

    public String getName() {
        return name;
    }

    public int getRating() {
        return rating;
    }

    public Boolean getListened() {
        return listened;
    }

    // REQUIRES: newRating has 0 <= val <= 10
    // MODIFIES: this
    // EFFECTS:  sets a new rating value for the
    //           album
    public void rateAlbum(int newRating) {
        if (listened) {
            rating = newRating;
        } else {
            rating = -1;
        }
    }
}
