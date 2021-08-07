package streamoutput;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import streaminput.BufferReader;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class BufferWriterTest {

    private BufferWriter bwriter;
    private BufferReader breader;
    private File bufferTestFile;

    @BeforeEach
    void setUp() {
        bufferTestFile = new File("testfile/test_write_buffer_writer");
        bwriter = new BufferWriter(bufferTestFile, 3);
        breader = new BufferReader(bufferTestFile, 3);
    }

    @Test
    void create() {
        assertTrue(bwriter.create());
    }


    @Test
    void writeLine() {
        String line1 = "Ligne 1";
        String line2 = "Ligne 2";
        assertDoesNotThrow(()-> bwriter.writeLine(line1));
        assertDoesNotThrow(()-> bwriter.writeLine(line2));
        breader.setPointerPosition(0);
        String readLine1 = breader.readln();
        String readLine2 = breader.readln();
        assertEquals("Ligne 1", readLine1);
        assertEquals("Ligne 2", readLine2);
    }

    @Test
    void close() {
        bwriter.close();
        assertThrows(IOException.class, () -> bwriter.writeLine("test"));
    }
}