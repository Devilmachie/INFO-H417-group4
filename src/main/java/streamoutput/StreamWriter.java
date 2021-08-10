package streamoutput;

import java.io.IOException;

public interface StreamWriter {
    boolean create();
    void writeLine(String linetowrite) throws IOException;
    void close();
}
