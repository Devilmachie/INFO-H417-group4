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
        mread.setPointerPosition(64);
        String l9 = mread.readln();
        assertEquals("Ligne 9", l9);
        assertEquals(true, mread.eos_reached());
    }

    @Test
    void setPointerPosition() {
    }

    @Test
    void close() {
    }
}