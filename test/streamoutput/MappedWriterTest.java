package streamoutput;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import streaminput.MappedReader;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class MappedWriterTest {

    private MappedWriter mwriter;
    private MappedReader mreader;
    private File mappedTestFile;

    @BeforeEach
    void setUp() {
        mappedTestFile = new File("testfile/test_write_mapped_writer");
        mwriter = new MappedWriter(mappedTestFile, 2);
        mreader = new MappedReader(mappedTestFile, 2);
        mreader.setPointerPosition(0);
    }

    @Test
    void create() {
        assertTrue(mwriter.create());
    }

    @Test
    void writeLine() {
        String line1 = "Ligne 1";
        String line2 = "Ligne 2";
        String line3 = "Ligne 3";
        assertDoesNotThrow(()-> mwriter.writeLine(line1));
        assertDoesNotThrow(()-> mwriter.writeLine(line2));
        assertDoesNotThrow(()-> mwriter.writeLine(line3));
        mreader.setPointerPosition(0);
        String readLine1 = mreader.readln();
        String readLine2 = mreader.readln();
        String readLine3 = mreader.readln();
        assertEquals("Ligne 1", readLine1);
        assertEquals("Ligne 2", readLine2);
        assertEquals("Ligne 3", readLine3);
    }


    @Test
    void close()
    {
        mwriter.close();
        assertThrows(IOException.class, () -> mwriter.writeLine("test"));
    }
}