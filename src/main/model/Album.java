package model;

// Represents an album with a name and a rating
public class Album {
    private String name;
    private int rating;

    // REQUIRES: initialRating has 0 <= val <= 10
    // EFFECTS: name of album is set to albumName;
    //          rating of album is set to the
    //          initialRating;
    // A rating of -1 is present if the album has yet to be listened to;
    public Album(String albumName, int initialRating) {
        name = albumName;
        rating = initialRating;
    }

    // EFFECTS: name of album is set to albumName;
    //          rating has default value of -1 to
    //          show that it has not been listened
    public Album(String albumName) {
        name = albumName;
        rating = -1;

    }

    public String getName() {
        return name;
    }

    public int getRating() {
        return rating;
    }


    // REQUIRES: newRating has 0 <= val <= 10
    // MODIFIES: this
    // EFFECTS:  sets a new rating value for the
    //           album
    public void rateAlbum(int newRating) {
        rating = newRating;
    }
}
