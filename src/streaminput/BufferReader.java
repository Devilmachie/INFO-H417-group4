package streaminput;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class BufferReader implements StreamReader
{
    private FileReader reader;
    private File fp;



    private int buf_size = 1024;
    private char[] buffer;
    private int last_c = 0;
    private boolean eos;
    private long nchar_read = 0;

    public BufferReader(File fp) {
        this.fp = fp;
        setBufferSize(buf_size);
        try {
            reader = new FileReader(fp);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public BufferReader(File fp, int buffer_size) {
        this.fp = fp;
        setBufferSize(buffer_size);

        try {
            reader = new FileReader(fp);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void setBufferSize(int buffer_size) {
        if(buffer_size < 1) throw new IllegalArgumentException("the buffer size must be greater than zero.");
        this.buf_size = buffer_size;
        buffer = new char[buffer_size];
    }

    @Override
    public String readln() {
        StringBuilder line = new StringBuilder();
        boolean refill = true;
        int length = 0;
        char last_c = 0;
        try
        {
            int i = 0;
            while (refill) {
                length = reader.read(buffer);
                if(length == -1) eos = true;
                for (i = 0; i<length && (last_c = buffer[i]) != '\n'; i++)
                {
                    line.append(last_c);
                    nchar_read++;
                }
                if(last_c == '\n' || length == -1) refill = false;

            }
            nchar_read++;
        }
        catch (IOException readp)
        {
            readp.printStackTrace();
        }
        setPointerPosition(nchar_read);
        return line.toString();
    }

    @Override
    public boolean eos_reached() {
        return eos;
    }

    @Override
    public void setPointerPosition(long pos) {
        try {
            reader.close();
            reader = new FileReader(fp);
            reader.skip(pos);
            nchar_read = pos;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isStreamOpen()
    {
        boolean is_open = true;
        try {
            reader.ready();
        } catch (IOException e) {
            is_open = false;
        }
        return is_open;
    }
    @Override
    public void close() {
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
