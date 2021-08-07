package streamoutput;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import streaminput.LineReader;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class LineWriterTest {

    private LineWriter lwriter;
    private LineReader lreader;
    private File lineTestFile;

    @BeforeEach
    void setUp() {
        lineTestFile = new File("testfile/test_write_line_writer");
        lwriter = new LineWriter(lineTestFile);
        lreader = new LineReader(lineTestFile);
    }

    @Test
    void create() {
        assertTrue(lwriter.create());
    }


    @Test
    void writeLine() {
        String line1 = "Ligne 1";
        String line2 = "Ligne 2";
        assertDoesNotThrow(()-> lwriter.writeLine(line1));
        assertDoesNotThrow(()-> lwriter.writeLine(line2));
        lreader.setPointerPosition(0);
        String readLine1 = lreader.readln();
        String readLine2 = lreader.readln();
        assertEquals("Ligne 1", readLine1);
        assertEquals("Ligne 2", readLine2);
    }

    @Test
    void close() {
        lwriter.close();
        assertThrows(IOException.class, () -> lwriter.writeLine("test"));
    }
}