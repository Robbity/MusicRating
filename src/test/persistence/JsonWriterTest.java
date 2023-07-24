package persistence;

import model.Album;
import model.AlbumDirectory;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// CITATION: Taken from JsonSerializationDemo writable file
class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            AlbumDirectory ad = new AlbumDirectory( "My album directory");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyAlbumDirectory.json");
            writer.open();
            writer.write(ad);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyAlbumDirectory.json");
            ad = reader.read();
            assertEquals("My album directory", ad.getName());
            assertEquals(0, ad.getAlbums().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            AlbumDirectory ad = new AlbumDirectory("My album directory");
            ad.addNewAlbum(new Album("DAMN", 7, 0));
            ad.addNewAlbum(new Album("The White Album", -1, 1));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralAlbumDirectory.json");
            writer.open();
            writer.write(ad);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralAlbumDirectory.json");
            ad = reader.read();
            assertEquals("My album directory", ad.getName());
            List<Album> albums = ad.getAlbums();
            assertEquals(2, albums.size());
            checkAlbum("DAMN", 7, 0, albums.get(0));
            checkAlbum("The White Album", -1, 1, albums.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}