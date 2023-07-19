package model;

import java.util.ArrayList;
import java.util.Collections;

// Represents the album directory
public class AlbumDirectory {
    private ArrayList<Album> albumList;
    private int idCount;

    // EFFECTS: Lists of listened and unlistened albums
    public AlbumDirectory() {
        albumList = new ArrayList<>();
        idCount = 0;
    }

    // EFFECTS: Adds the album to the rated list
    public void addNewAlbum(String name, int rating) {
        Album addedAlbum = new Album(name, rating, idCount++);
        albumList.add(addedAlbum);
    }

    public void addNewAlbum(Album album) {
        album.setId(idCount++);
        albumList.add(album);
    }

    // EFFECTS: Adds the album to the rated list
    public void removeNewAlbum(Album album) {
        albumList.remove(album);
    }

    public ArrayList<Album> getAlbums() {
        return albumList;
    }

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
