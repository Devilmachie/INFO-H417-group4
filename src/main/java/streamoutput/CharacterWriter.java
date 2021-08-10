package streamoutput;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

public class CharacterWriter implements StreamWriter{

    private File fp;
    private FileWriter writer;

    public CharacterWriter(String fpName) {
        fp = new File(fpName);
        checkFileExistence(fp);
        initiliazeFileWriter(fp);
    }

    public CharacterWriter(File fp) {
        this.fp = fp;
        checkFileExistence(fp);
        initiliazeFileWriter(fp);
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
    public void writeLine(String linetowrite) throws IOException {
        checkFileExistence(fp);
        linetowrite += "\r\n";
        CharacterIterator it = new StringCharacterIterator(linetowrite);

        while(it.current() != CharacterIterator.DONE){
            writer.write(it.current());
            it.next();
        }
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
