package streamoutput;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class BufferWriter implements StreamWriter{

    private int buffersize;
    private File fp;
    private FileWriter writer;
    private char[] buffer;

    public BufferWriter(String fileName, int bufferSize) {
        this.fp = new File(fileName);
        this.buffersize = bufferSize;
        this.buffer = new char[bufferSize];
        checkFileExistence(this.fp);
        initiliazeFileWriter(this.fp);
    }

    public BufferWriter(File bufferFile, int bufferSize) {
        this.fp = bufferFile;
        this.buffersize = bufferSize;
        this.buffer = new char[bufferSize];
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

    private void loadBuffer(String text, int offset)
    {
        for(int i = offset, j=0; i<offset+buffersize &&  i < text.length();i++,j++)
        {
            buffer[j] = text.charAt(i);
        }
    }
    @Override
    public void writeLine(String lineToWrite) throws IOException {
        checkFileExistence(fp);
        lineToWrite += "\r\n";
        boolean lineWritten = false;
        int characterWritten = 0;
        while(!lineWritten)
        {
            loadBuffer(lineToWrite, characterWritten);
            characterWritten += buffersize;
            writer.write(buffer);
            writer.flush();

            if(characterWritten >= lineToWrite.length()) lineWritten = true;
        }

     }

    @Override
    public void close() {
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
