package streamoutput;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class BufferWriter implements StreamWriter{

    private int buffersize;
    private File fp;
    private FileWriter wrappedWriter;
    private BufferedWriter writer;
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
            wrappedWriter = new FileWriter(file_to_write);
            writer = new BufferedWriter(wrappedWriter, buffersize);
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

    private int loadBuffer(String text, int offset)
    {
        int characterWritten = 0;
        for(int i = offset, j=0; i<offset+buffersize &&  i < text.length();i++,j++)
        {
            buffer[j] = text.charAt(i);
            characterWritten++;
        }
        return characterWritten;
    }
    @Override
    public void writeLine(String lineToWrite) throws IOException {
        checkFileExistence(fp);
        lineToWrite += "\r\n";
        boolean lineWritten = false;
        int characterWritten = 0;
        int nc_to_write;
        while(!lineWritten)
        {
            nc_to_write = loadBuffer(lineToWrite, characterWritten);
            characterWritten += buffersize;
            writer.write(buffer, 0, nc_to_write);
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
