package streaminput;

import org.openjdk.jmh.annotations.Benchmark;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CharacterReader implements StreamReader {
    private FileReader fileReader;
    private File fp;
    private boolean eos;

    public CharacterReader(File fp)  {
        try {
            this.fp = fp;
            fileReader = new FileReader(fp);
        } catch (FileNotFoundException fnf) {
            System.out.println("File " + fp.getPath() + "was not found.");
            fnf.printStackTrace();
        }
    }


    @Override
    public String readln() {
        StringBuilder line = new StringBuilder();
        try {
            int nextc;
            while( (nextc=fileReader.read()) != -1 && nextc != 13)
            {
                line.append((char) nextc);
            }
            fileReader.skip(1); // in windows the EOL is /r/n (two character long)
            if(nextc == -1) eos = true;
        } catch (IOException readerr) {
            readerr.printStackTrace();
        }
        return line.toString();
    }

    @Override
    public boolean eos_reached() {
        return eos;
    }

    @Override
    public void setPointerPosition(long pos) {
        try {
            fileReader.close();
            fileReader = new FileReader(fp);
            fileReader.skip(pos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isStreamOpen()
    {
        boolean is_open = true;
        try {
            fileReader.ready();
        } catch (IOException e) {
            is_open = false;
        }
        return is_open;
    }
    @Override
    public void close() {
        try {
            fileReader.close();
        } catch (IOException nc) {
            nc.printStackTrace();
        }
    }

    @Override
    public long getFileSize() {
        return fp.length();
    }
}
