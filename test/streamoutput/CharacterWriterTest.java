package streamoutput;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import streaminput.CharacterReader;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class CharacterWriterTest {

    private CharacterWriter cwriter;
    private CharacterReader creader;
    private File characterTestFile;

    @BeforeEach
    void setUp() {
        characterTestFile = new File("testfile/test_write_character_writer");
        cwriter = new CharacterWriter(characterTestFile);
        creader = new CharacterReader(characterTestFile);
    }

    @Test
    void create() {
        assertTrue(cwriter.create());
    }


    @Test
    void writeLine() {
        String line1 = "Ligne 1";
        String line2 = "Ligne 2";
        assertDoesNotThrow(()-> cwriter.writeLine(line1));
        assertDoesNotThrow(()-> cwriter.writeLine(line2));
        String readLine1 = creader.readln();
        String readLine2 = creader.readln();
        assertEquals("Ligne 1", readLine1);
        assertEquals("Ligne 2", readLine2);
    }

    @Test
    void close() {
        cwriter.close();
        assertThrows(IOException.class, () -> cwriter.writeLine("test"));
    }
}