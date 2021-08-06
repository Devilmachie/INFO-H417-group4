package streamoutput;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MappedWriter implements StreamWriter
{
    private RandomAccessFile writer;
    private MappedByteBuffer map;
    private File fp;
    private FileChannel channel;
    private int map_size;

    public MappedWriter(File file) {

    }

    public MappedWriter(File file, int map_size) {
        this.map_size = map_size;
        this.fp = file;
        checkFileExistence(this.fp);
        try {
            writer = new RandomAccessFile(file, "w");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void checkFileExistence(File file_to_write) {
        boolean fileExists = file_to_write.exists() && file_to_write.isFile();
        if (!fileExists) create();
    }





    @Override
    public boolean create() {
        boolean isCreatedForWriting = fp.exists() && fp.isFile();
        if(!isCreatedForWriting) {
            try {
                fp.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(!fp.canWrite()) {
            fp.setWritable(true);
            isCreatedForWriting = true;
        }
        else isCreatedForWriting = true;

        return isCreatedForWriting;

    }

    @Override
    public void writeLine(String linetowrite) {

    }

    @Override
    public void close() {
    }
}
