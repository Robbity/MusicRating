package persistence;

import model.Album;

import static org.junit.jupiter.api.Assertions.*;

// CITATION: Taken from JsonSerializationDemo writable file
public class JsonTest {
    // EFFECTS:  Checks that album has matching name, rating and id
    protected void checkAlbum(String name, int rating, int id, Album album) {
        assertEquals(name, album.getName());
        assertEquals(rating, album.getRating());
        assertEquals(id, album.getId());
    }

}
