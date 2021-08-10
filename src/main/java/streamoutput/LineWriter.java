package streamoutput;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LineWriter implements StreamWriter
{

    private File fp;
    private FileWriter wrappedWriter;
    private BufferedWriter writer;

    public LineWriter(String fileName)
    {
        this.fp = new File(fileName);
        checkFileExistence(fp);
        initiliazeFileWriter(fp);
        writer = new BufferedWriter(wrappedWriter);
    }

    public LineWriter(File fp) {
        this.fp = fp;
        checkFileExistence(fp);
        initiliazeFileWriter(fp);
        writer = new BufferedWriter(wrappedWriter);

    }

    private void initiliazeFileWriter(File file_to_write) {
        try {
            wrappedWriter = new FileWriter(file_to_write);
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
    public void writeLine(String linetowrite) throws IOException {
        linetowrite += "\r\n";
        writer.write(linetowrite);
        writer.flush();
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

