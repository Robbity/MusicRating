package model;

import java.util.ArrayList;

// Represents the album directory
public class AlbumDirectory {
    private final ArrayList<Album> albumList;
    private int idCount;

    // EFFECTS: Lists of listened and unlistened albums
    public AlbumDirectory() {
        albumList = new ArrayList<>();
        idCount = 0;
    }

    // MODIFIES: this
    // EFFECTS: Creates album with name and rating and adds it to list
    //          this has an incrementing id
    public void addNewAlbum(String name, int rating) {
        Album addedAlbum = new Album(name, rating, idCount++);
        albumList.add(addedAlbum);
    }

    // MODIFIES: this
    // EFFECTS: Adds album to list, setting incrementing id
    public void addNewAlbum(Album album) {
        album.setId(idCount++);
        albumList.add(album);
    }

    // MODIFIES: this
    // EFFECTS: Adds the album to the rated list
    public void removeNewAlbum(Album album) {
        albumList.remove(album);
    }

    // EFFECTS: returns the list of albums thus far
    public ArrayList<Album> getAlbums() {
        return albumList;
    }

    // MODIFIES: this
    // EFFECTS: sorts the albums by rating in descending order
    //          (lowest rating at index 0)
    public void rateSortAlbums() {
        for (int i = 0; i < albumList.size(); i++) {
            for (int j = i + 1; j < albumList.size(); j++) {
                if (albumList.get(i).getRating() > albumList.get(j).getRating()) {
                    Album temp = albumList.get(i);
                    albumList.set(i, albumList.get(j));
                    albumList.set(j, temp);
                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: sorts albums by ids in ascending order
    //          (lowest id at index 0, represents first added)
    public void recentSortAlbums() {
        for (int i = 0; i < albumList.size(); i++) {
            for (int j = i + 1; j < albumList.size(); j++) {
                if (albumList.get(i).getId() > albumList.get(j).getId()) {
                    Album temp = albumList.get(i);
                    albumList.set(i, albumList.get(j));
                    albumList.set(j, temp);
                }
            }
        }
    }


}
