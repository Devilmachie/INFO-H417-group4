package streaminput;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class MappedReaderTest {

    private MappedReader mread;

    @BeforeEach
    void setUp() {
        File fp = new File("testfile/test1");
        mread = new MappedReader(fp, 3);
    }

    @AfterEach
    void tearDown() {
        mread.close();
    }

    @Test
    void readln() {
        String l1 = mread.readln();
        assertEquals("Ligne 1", l1);
        String l2 = mread.readln();
        assertEquals("Ligne 2", l2);
    }

    @Test
    void eos_reached() {
        mread.setPointerPosition(72);
        String l9 = mread.readln();
        assertEquals("Ligne 9", l9);
        assertTrue(mread.eos_reached());
    }

    @Test
    void setPointerPosition() 
    {
        mread.setPointerPosition(1);
        assertEquals("igne 1", mread.readln());
        mread.setPointerPosition(73);
        assertEquals("igne 9", mread.readln());
    }

    @Test
    void close() {
        assertTrue(mread.isStreamOpen());
        mread.close();
        assertFalse(mread.isStreamOpen());
    }
}