package persistence;

import model.Album;

import static org.junit.jupiter.api.Assertions.*;

// CITATION: Taken from JsonSerializationDemo writable file
public class JsonTest {
    protected void checkAlbum(String name, int rating, int id, Album album) {
        assertEquals(name, album.getName());
        assertEquals(rating, album.getRating());
        assertEquals(id, album.getId());
    }

}
