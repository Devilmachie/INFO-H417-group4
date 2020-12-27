package streaminput;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class LineReaderTest {

    private LineReader lread;

    @BeforeEach
    void setUp() {
        File fp = new File("testfile/test1");
        lread = new LineReader(fp);
    }

    @Test
    void readln() {
        String l1 = lread.readln();
        String l2 = lread.readln();
        assertEquals("Ligne 1", l1);
        assertEquals("Ligne 2", l2);
    }

    @Test
    void eos_reached() {
        assertEquals(false, lread.eos_reached());
        StringBuilder res = new StringBuilder();
        for (int i =0; i<11; i++) res.append(lread.readln());
        assertEquals(true, lread.eos_reached());

    }

    @Test
    void setPointerPosition() {
        lread.setPointerPosition(1);
        String partialLine = lread.readln();
        assertEquals("igne 1", partialLine);
    }

    @Test
    void close() {
        assertEquals(true, lread.isStreamOpen());
        lread.close();
        assertEquals(false, lread.isStreamOpen());
    }

    @AfterEach
    void tearDown() {
        lread.close();
    }
}