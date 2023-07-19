package model;

import java.util.ArrayList;

// Represents the album directory
public class AlbumDirectory {
    private ArrayList<Album> albumList;

    // EFFECTS: Lists of listened and unlistened albums
    public AlbumDirectory() {
        albumList = new ArrayList<>();
    }

    // EFFECTS: Adds the album to the rated list
    public void addNewAlbum(Album album) {
        albumList.add(album);
    }

    // EFFECTS: Adds the album to the rated list
    public void removeNewAlbum(Album album) {
        albumList.remove(album);
    }

    public ArrayList<Album> getAlbums() {
        return albumList;
    }


}
