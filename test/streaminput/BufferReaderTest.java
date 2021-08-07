package streaminput;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.io.File;
import java.io.IOException;

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
        assertFalse(bread.eos_reached());
        bread.setPointerPosition(72);
        String l9 = bread.readln();
        assertEquals("Ligne 9", l9);
        assertTrue(bread.eos_reached());

    }

    @Test
    void setPointerPosition() {
        bread.setPointerPosition(1);
        assertEquals("igne 1", bread.readln());
        bread.setPointerPosition(73);
        assertEquals("igne 9", bread.readln());
    }

    @Test
    void close() {
        assertTrue(bread.isStreamOpen());
        bread.close();
        assertFalse(bread.isStreamOpen());
    }

    @Test
    void testOnDifferentFile() throws IOException {
        BufferReader breader = new BufferReader(new File("testfile/test_write_buffer_writer"));
        String l1 = breader.readln();
        String l2 = breader.readln();
        assertEquals("Ligne 1", l1);
        assertEquals("Ligne 2", l2);
    }
}