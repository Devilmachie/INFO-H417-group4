package streamoutput;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class MappedWriterTest {

    private MappedWriter mwriter;
    private File mappedTestFile;

    @BeforeEach
    void setUp() {
        mappedTestFile = new File("testfile/test_write_mapped_writer");
        mwriter = new MappedWriter(mappedTestFile);
    }

    @Test
    void create() {
    }

    @Test
    void testCreate() {
    }

    @Test
    void writeLine() {
    }

    @Test
    void close() {
    }
}