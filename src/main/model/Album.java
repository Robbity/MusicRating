package model;

// Represents an album with a name and a rating
public class Album {
    private String name;
    private int rating;
    private int id;

    // REQUIRES: initialRating has -1 <= val <= 10
    // EFFECTS: name of album is set to albumName;
    //          rating of album is set to the
    //          initialRating;
    // A rating of -1 is present if the album has yet to be listened to;
    public Album(String albumName, int initialRating, int id) {
        this.name = albumName;
        this.rating = initialRating;
        this.id = id;

    }

    public Album(String albumName, int initialRating) {
        this.name = albumName;
        this.rating = initialRating;
        this.id = -1;

    }

    // EFFECTS: name of album is set to albumName;
    //          rating has default value of -1 to
    //          show that it has been unrated
//    public Album(String albumName) {
//        name = albumName;
//        rating = -1;
//        id = nextAlbumId++;
//
//    }

    public String getName() {
        return name;
    }

    public int getRating() {
        return rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int num) {
        id = num;
    }


    // REQUIRES: newRating has 0 <= val <= 10
    // MODIFIES: this
    // EFFECTS:  sets a new rating value for the
    //           album
    public void rateAlbum(int newRating) {
        rating = newRating;
    }
}
