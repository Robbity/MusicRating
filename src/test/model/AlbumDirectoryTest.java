package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AlbumDirectoryTest {
    private AlbumDirectory testAlbumDirectory;
    private Album testRatedAlbum;
    private Album testAlbum;
    private Album testRatedAlbum2;
    private Album testRatedAlbum3;

    @BeforeEach
    void runBefore() {
        testAlbumDirectory = new AlbumDirectory("Test Directory");
        testRatedAlbum = new Album("Iridescence", 10);
        testRatedAlbum2 = new Album("Certified Lover Boy", 3);
        testRatedAlbum3 = new Album("SOS", 5);
        testAlbum = new Album("DAMN", -1);
    }

    @Test
    void testRatedEmpty() {

        assertEquals(0, testAlbumDirectory.getAlbums().size());

    }

    @Test
    void testRatedOneAlbum() {

        testAlbumDirectory.addNewAlbum(testRatedAlbum);
        assertEquals(1, testAlbumDirectory.getAlbums().size());
        assertEquals("Test Directory", testAlbumDirectory.getName());

    }

    @Test
    void testRatedandNonRatedAlbum() {

        testAlbumDirectory.addNewAlbum(testRatedAlbum);
        testAlbumDirectory.addNewAlbum(testAlbum);
        testAlbumDirectory.addNewAlbum("Drake", 0);
        assertEquals(3, testAlbumDirectory.getAlbums().size());
        assertEquals("Test Directory", testAlbumDirectory.getName());

    }

    @Test
    void testAddThenRemoveAlbum() {

        testAlbumDirectory.addNewAlbum(testRatedAlbum);
        testAlbumDirectory.addNewAlbum(testAlbum);
        assertEquals(2, testAlbumDirectory.getAlbums().size());
        testAlbumDirectory.removeNewAlbum(testRatedAlbum);
        assertEquals(1, testAlbumDirectory.getAlbums().size());
        testAlbumDirectory.addNewAlbum(testRatedAlbum);
        assertEquals(2, testAlbumDirectory.getAlbums().size());
        testAlbumDirectory.removeIndex(1);
        assertEquals(testAlbum, testAlbumDirectory.getAlbums().get(0));
        assertEquals(1, testAlbumDirectory.getAlbums().size());

        ArrayList<Album> newTest = new ArrayList<>();
        newTest.add(testAlbum);
        assertEquals(newTest, testAlbumDirectory.getAlbums());

        assertEquals("Test Directory", testAlbumDirectory.getName());

    }

    @Test
    void testSortingByRate() {

        testAlbumDirectory.addNewAlbum(testRatedAlbum);
        testAlbumDirectory.addNewAlbum(testAlbum);
        testAlbumDirectory.addNewAlbum(testRatedAlbum2);
        testAlbumDirectory.addNewAlbum(testRatedAlbum3);

        ArrayList<Album> albums = testAlbumDirectory.getAlbums();

        assertEquals(testRatedAlbum, albums.get(0));
        assertEquals(testAlbum, albums.get(1));
        assertEquals(testRatedAlbum2, albums.get(2));
        assertEquals(testRatedAlbum3, albums.get(3));

        testAlbumDirectory.rateSortAlbums();

        ArrayList<Album> albums2 = testAlbumDirectory.getAlbums();

        assertEquals(testRatedAlbum, albums2.get(3));
        assertEquals(testRatedAlbum3, albums2.get(2));
        assertEquals(testRatedAlbum2, albums2.get(1));
        assertEquals(testAlbum, albums2.get(0));

        assertEquals("Test Directory", testAlbumDirectory.getName());


    }

    @Test
    void testSortingByRecent() {

        testAlbumDirectory.addNewAlbum(testRatedAlbum);
        testAlbumDirectory.addNewAlbum(testAlbum);
        testAlbumDirectory.addNewAlbum(testRatedAlbum2);
        testAlbumDirectory.addNewAlbum(testRatedAlbum3);

        testAlbumDirectory.rateSortAlbums();
        testAlbumDirectory.recentSortAlbums();

        ArrayList<Album> albums = testAlbumDirectory.getAlbums();

        assertEquals(testRatedAlbum, albums.get(0));
        assertEquals(testAlbum, albums.get(1));
        assertEquals(testRatedAlbum2, albums.get(2));
        assertEquals(testRatedAlbum3, albums.get(3));

        assertEquals("Test Directory", testAlbumDirectory.getName());


    }
}
