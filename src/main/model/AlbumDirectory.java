package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Represents the album directory
public class AlbumDirectory implements Writable {
    private final String name;
    private final ArrayList<Album> albumList;
    private int idCount;

    // EFFECTS: Lists of listened and unlistened albums
    public AlbumDirectory(String name) {
        this.name = name;
        albumList = new ArrayList<>();
        idCount = 0;
    }

    public String getName() {
        return name;
    }

    // MODIFIES: this
    // EFFECTS: Creates album with name and rating and adds it to list
    //          this has an incrementing id
    public void addNewAlbum(String name, int rating) {
        Album addedAlbum = new Album(name, rating, idCount++);
        albumList.add(addedAlbum);
        EventLog.getInstance().logEvent(new Event("Album: "
                + name + " added to album list with rating: " + rating + "."));
    }

    // MODIFIES: this
    // EFFECTS: Adds album to list, setting incrementing id
    public void addNewAlbum(Album album) {
        album.setId(idCount++);
        albumList.add(album);
        EventLog.getInstance().logEvent(new Event("Album: "
                + album.getName() + " added to album list with rating: " + album.getRating() + "."));
    }

    // MODIFIES: this
    // EFFECTS: Removes the album from the album list
    public void removeNewAlbum(Album album) {
        albumList.remove(album);
        EventLog.getInstance().logEvent(new Event("Album: "
                + album.getName() + " removed from album list."));
    }

    // MODIFIES: this
    // EFFECTS: Removes the album from the album list
    public void removeIndex(int i) {
        EventLog.getInstance().logEvent(new Event("Album: "
                + albumList.get(i).getName() + " removed from album list."));
        albumList.remove(i);
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

    // CITATION: Taken from JsonSerializationDemo writable file
    // EFFECTS:  Creates Json object with album data
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("albums", albumsToJson());
        return json;
    }

    // CITATION: Taken from JsonSerializationDemo writable file
    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray albumsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Album a : albumList) {
            jsonArray.put(a.toJson());
        }
        return jsonArray;
    }

}
