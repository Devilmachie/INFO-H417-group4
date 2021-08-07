package streamoutput;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MappedWriter implements StreamWriter
{
    private RandomAccessFile writer;
    private MappedByteBuffer map;
    private File fp;
    private FileChannel channel;
    private int characterWritten;
    private int map_size;

    public MappedWriter(String fileName, int map_size) {
        this.map_size = map_size;
        this.fp = new File(fileName);
        checkFileExistence(this.fp);
        try {
            writer = new RandomAccessFile(fp, "rw");
            channel = writer.getChannel();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public MappedWriter(File file, int map_size) {
        this.map_size = map_size;
        this.fp = file;
        checkFileExistence(this.fp);
        try {
            writer = new RandomAccessFile(file, "rw");
            channel = writer.getChannel();
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
    public void writeLine(String linetowrite) throws IOException {
        int offsetBuffer = 0;
        boolean lineWritten = false;
        linetowrite += "\r\n";


        while(!lineWritten)
        {
        }
    }

    @Override
    public void close() {
        try {
            writer.close();
            map = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
