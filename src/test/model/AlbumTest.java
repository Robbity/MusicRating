package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;



class AlbumTest {
    private Album testRatedAlbum;
    private Album testAlbum;

    @BeforeEach
    void runBefore() {
        testRatedAlbum = new Album("Iridescence", 10);
        testAlbum = new Album("DAMN");
    }

    @Test
    void testConstructor() {
        assertEquals("Iridescence", testRatedAlbum.getName());
        assertEquals(10, testRatedAlbum.getRating());
        assertEquals("DAMN", testAlbum.getName());
        assertEquals(-1, testAlbum.getRating());
    }

    @Test
    void testRateAlbum() {
        testRatedAlbum.rateAlbum(9);
        assertEquals(9, testRatedAlbum.getRating());
        testAlbum.rateAlbum(7);
        assertEquals(7, testAlbum.getRating());
    }
}