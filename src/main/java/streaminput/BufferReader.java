package streaminput;

import java.io.*;


public class BufferReader implements StreamReader {
    private BufferedReader reader;
    private File fp;


    private int buf_size = 1024;
    private int bufferpos = 0;
    private boolean eos;
    private char[] buffer;
    private FileReader wrappedReader;
    private long nchar_read = 0;

    public BufferReader(File fp) {
        this.fp = fp;
        try {
            wrappedReader = new FileReader(fp);
            reader = new BufferedReader(wrappedReader, buf_size);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        buffer = new char[buf_size];

    }

    public BufferReader(File fp, int buffer_size) {
        this.fp = fp;
        this.buf_size = buffer_size;

        try {
            reader = new BufferedReader(new FileReader(fp), buf_size);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        buffer = new char[buf_size];


    }

    private int fillBuffer()
    {
        int readResult = 0;
        try {
            readResult = reader.read(buffer);
            if (readResult == -1 || readResult < buf_size) eos = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        nchar_read += buf_size;
        bufferpos = 0;
        return readResult;
    }

    @Override
    public String readln() {
        StringBuilder line = new StringBuilder();
        boolean line_read = false;
        int readedChar = fillBuffer();
        while (! line_read)
        {
            if(bufferpos == buf_size)
                readedChar = fillBuffer();
            if(buffer[bufferpos] != 13 && buffer[bufferpos] != -1 && readedChar == buf_size)
                line.append((char) buffer[bufferpos++]);
            else if(buffer[bufferpos] == 13 && readedChar == buf_size)
            {
                bufferpos += 2; // WE READ CR AND LF SYMBOL
                line_read=true;
            }
            else
            {
                line_read = true; // eos reached
                for(int i =0; i<readedChar; i++) line.append(buffer[i]);
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
            reader = new BufferedReader(new FileReader(fp), buf_size);
            reader.skip(pos);
            bufferpos = 0;
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

    @Override
    public long getFileSize() {
        return fp.length();
    }
}
