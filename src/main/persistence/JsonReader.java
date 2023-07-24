package persistence;

import model.AlbumDirectory;
import model.Album;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// CITATION: Taken from JsonSerializationDemo writable file
public class JsonReader {
    private final String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public AlbumDirectory read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseAlbumDirectory(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses workroom from JSON object and returns it
    private AlbumDirectory parseAlbumDirectory(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        AlbumDirectory ad = new AlbumDirectory(name);
        addAlbums(ad, jsonObject);
        return ad;
    }

    // MODIFIES: wr
    // EFFECTS: parses thingies from JSON object and adds them to workroom
    private void addAlbums(AlbumDirectory ad, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("albums");
        for (Object json : jsonArray) {
            JSONObject nextAlbum = (JSONObject) json;
            addAlbum(ad, nextAlbum);
        }
    }

    // MODIFIES: wr
    // EFFECTS: parses thingy from JSON object and adds it to workroom
    private void addAlbum(AlbumDirectory ad, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int rating = jsonObject.getInt("rating");
        int id = jsonObject.getInt("id");
        Album album = new Album(name, rating, id);
        ad.addNewAlbum(album);
    }
}
