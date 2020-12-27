package streaminput;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class BufferReaderTest {

    private BufferReader bread;
    private File fp;

    @BeforeEach
    void setUp() {
        fp = new File("testfile/test1");
        bread = new BufferReader(fp, 2);
    }

    @AfterEach
    void tearDown() {
        bread.close();
    }

    @Test
    void readln() {
        bread = new BufferReader(fp, 3);
        String l1 = bread.readln();
        assertEquals("Ligne 1", l1);
        String l2 = bread.readln();
        assertEquals("Ligne 2", l2);

    }

    @Test
    void eos_reached() {
        assertEquals(false, bread.eos_reached());
        bread.setPointerPosition(64);
        String l9 = bread.readln();
        assertEquals("Ligne 9", l9);
        assertEquals(true, bread.eos_reached());

    }

    @Test
    void setPointerPosition() {
        bread.setPointerPosition(1);
        assertEquals("igne 1", bread.readln());
    }

    @Test
    void close() {
        assertEquals(true, bread.isStreamOpen());
        bread.close();
        assertEquals(false, bread.isStreamOpen());
    }
}