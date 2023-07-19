package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AlbumDirectoryTest {
    private AlbumDirectory testAlbumDirectory;
    private Album testRatedAlbum;
    private Album testAlbum;

    @BeforeEach
    void runBefore() {
        testAlbumDirectory = new AlbumDirectory();
        testRatedAlbum = new Album("Iridescence", 10);
        testAlbum = new Album("DAMN");
    }

    @Test
    void testRatedEmpty() {

        assertEquals(0, testAlbumDirectory.getAlbums().size());

    }

    @Test
    void testRatedOneAlbum() {

        testAlbumDirectory.addNewAlbum(testRatedAlbum);
        assertEquals(1, testAlbumDirectory.getAlbums().size());

    }

    @Test
    void testRatedandNonRatedAlbum() {

        testAlbumDirectory.addNewAlbum(testRatedAlbum);
        testAlbumDirectory.addNewAlbum(testAlbum);
        assertEquals(2, testAlbumDirectory.getAlbums().size());

    }

    @Test
    void testAddThenRemoveAlbum() {

        testAlbumDirectory.addNewAlbum(testRatedAlbum);
        testAlbumDirectory.addNewAlbum(testAlbum);
        assertEquals(2, testAlbumDirectory.getAlbums().size());
        testAlbumDirectory.removeNewAlbum(testRatedAlbum);
        assertEquals(1, testAlbumDirectory.getAlbums().size());

    }
}
