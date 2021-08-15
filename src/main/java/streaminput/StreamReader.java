package streaminput;

public interface StreamReader {
    String readln();
    boolean eos_reached();
    void setPointerPosition(long pos);
    void close();

    long getFileSize();
}
