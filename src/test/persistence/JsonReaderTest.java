package persistence;

import model.Album;
import model.AlbumDirectory;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// CITATION: Taken from JsonSerializationDemo writable file
class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyAlbumDirectory() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyAlbumDirectory.json");
        try {
            AlbumDirectory ad = reader.read();
            assertEquals("My album directory", ad.getName());
            assertEquals(0, ad.getAlbums().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralAlbumDirectory() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralAlbumDirectory.json");
        try {
            AlbumDirectory ad = reader.read();
            assertEquals("My album directory", ad.getName());
            List<Album> albums = ad.getAlbums();
            assertEquals(2, albums.size());
            checkAlbum("DAMN", 7, 0, albums.get(0));
            checkAlbum("The White Album", -1, 1, albums.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}