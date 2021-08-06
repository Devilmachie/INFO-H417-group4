package streamoutput;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class BufferWriter implements StreamWriter{

    private int buffersize;
    private File fp;
    private FileWriter writer;

    public BufferWriter(String fileName, int bufferSize) {
        this.fp = new File(fileName);
        this.buffersize = bufferSize;
        checkFileExistence(this.fp);
        initiliazeFileWriter(this.fp);
    }

    public BufferWriter(File bufferFile, int bufferSize) {
        this.fp = bufferFile;
        this.buffersize = bufferSize;
        checkFileExistence(this.fp);
        initiliazeFileWriter(this.fp);
    }

    private void initiliazeFileWriter(File file_to_write) {
        try {
            writer = new FileWriter(file_to_write);
        } catch (IOException e) {
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
