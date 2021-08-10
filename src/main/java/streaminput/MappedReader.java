package streaminput;

import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MappedReader implements StreamReader {
    private int map_size;
    private MappedByteBuffer map;
    private File fp;
    private RandomAccessFile channel;
    private long nc_read = 0;
    private int mappos=0;
    private boolean eos = false;
    private long max_size;

    public MappedReader(File fp) {
        this.fp = fp;
        this.map_size = 1024;
        try {
            this.channel = new RandomAccessFile(fp, "r");
            max_size = channel.getChannel().size();
        } catch (IOException e) {
            e.printStackTrace();
        }

        fillMap();
    }

    public MappedReader(File fp, int map_size) {
        this.fp = fp;
        this.map_size = map_size;
        try {
            this.channel = new RandomAccessFile(fp, "r");
            max_size = channel.getChannel().size();
        } catch (IOException e) {
            e.printStackTrace();
        }
        fillMap();
    }

    private void fillMap()
    {
        try {
            int size_to_read;
            if (nc_read + map_size < max_size) size_to_read = map_size;
            else size_to_read = (int) (max_size - nc_read);
            map = channel.getChannel().map(FileChannel.MapMode.READ_ONLY, nc_read, size_to_read);
            mappos = 0;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String readln() {
        StringBuilder line = new StringBuilder();
        boolean line_read = false;
        int char_read;
        while(!line_read)
        {
            if(mappos >= map_size)
            {
                nc_read += map_size;
                fillMap();
            }
            if(nc_read + mappos < max_size) char_read = map.get(mappos);
            else {
                eos = true;
                return line.toString();
            }
            if(char_read != 13 && char_read != 0)
            {
                line.append((char) char_read);
                mappos += 1;
            }
            else if(char_read == 13)
            {
                line_read = true;
                mappos += 2;
                //nc_read ++; // because we virtually need to "read" the LF SYMBOL
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
        nc_read = pos;
        try {
            channel.seek(pos);
            mappos = 0;
            nc_read = pos;
            fillMap();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        try {
            channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isStreamOpen()
    {
        return channel.getChannel().isOpen();
    }
}
