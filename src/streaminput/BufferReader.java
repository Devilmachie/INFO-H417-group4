package streaminput;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class BufferReader implements StreamReader {
    private FileReader reader;
    private File fp;


    private int buf_size = 1024;
    private int[] buffer;
    private int bufferpos = 0;
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
        fillBuffer();
    }

    public BufferReader(File fp, int buffer_size) {
        this.fp = fp;
        setBufferSize(buffer_size);

        try {
            reader = new FileReader(fp);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        fillBuffer();

    }

    public void setBufferSize(int buffer_size) {
        if (buffer_size < 1) throw new IllegalArgumentException("the buffer size must be greater than zero.");
        this.buf_size = buffer_size;
        buffer = new int[buffer_size];
    }

    private void fillBuffer()
    {
        int next_c;
        bufferpos = 0;
        try
        {
            for (int i=0; i<buf_size; i++)
            {
                next_c = reader.read();
                buffer[i] = next_c;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String readln() {
        StringBuilder line = new StringBuilder();
        boolean line_read = false;
        while (! line_read)
        {
            if(bufferpos == buf_size)
                fillBuffer();
            if(buffer[bufferpos] != 13 && buffer[bufferpos] != -1)
                line.append((char) buffer[bufferpos++]);
            else if(buffer[bufferpos] == 13)
            {
                bufferpos += 2; // WE READ CR AND LF SYMBOL
                line_read=true;
            }
            else if(buffer[bufferpos] == -1)
            {
                line_read = true;
                eos = true;
            }
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
            reader.close();
            reader = new FileReader(fp);
            reader.skip(pos);
            bufferpos = 0;
            fillBuffer();
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
