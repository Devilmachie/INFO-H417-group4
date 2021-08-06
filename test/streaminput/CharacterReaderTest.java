package streaminput;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class CharacterReaderTest {

    private CharacterReader cread;

    @BeforeEach
    void setUp() {
        File fp = new File("testfile/test1");
        cread = new CharacterReader(fp);
    }

    @org.junit.jupiter.api.Test
    void readln() {
        String lineread1 = cread.readln();
        String lineread2= cread.readln();
        cread.close();
        assertEquals("Ligne 1", lineread1);
        assertEquals("Ligne 2", lineread2);
    }

    @org.junit.jupiter.api.Test
    void eos_reached() {
        for(int i=0; i<10; i++) cread.readln();
        assertTrue(cread.eos_reached());
    }

    @org.junit.jupiter.api.Test
    void setPointerPosition() {
        cread.setPointerPosition(1);
        String lineread = cread.readln();
        assertEquals("igne 1", lineread);
        cread.setPointerPosition(72);
        String lineread9 = cread.readln();
        assertEquals("Ligne 9", lineread9);
    }


    @Test
    void close() {
        assertTrue(cread.isStreamOpen());
        cread.close();
        assertFalse(cread.isStreamOpen());
    }

    @AfterEach
    void tearDown() {
        cread.close();
    }
}