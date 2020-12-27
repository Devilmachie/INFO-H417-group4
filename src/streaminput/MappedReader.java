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
    private boolean eos = false;

    public MappedReader(File fp) {
        this.fp = fp;
        this.map_size = 1024;
        try {
            this.channel = new RandomAccessFile(fp, "r");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public MappedReader(File fp, int map_size) {
        this.fp = fp;
        this.map_size = map_size;
        try {
            this.channel = new RandomAccessFile(fp, "r");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String readln() {
        StringBuilder line = new StringBuilder();
        boolean refill = true;
        int i = 0;
        char last_c = 0;
        try {
            while (refill)
            {
                map = channel.getChannel().map(FileChannel.MapMode.READ_ONLY, nc_read, map_size);
                for(i = 0; i<map_size && (last_c = (char) map.get(i)) != '\n' && last_c != 0; i++)
                {
                    line.append(last_c);
                    nc_read++;
                }
                if(last_c == '\n' || last_c == 0) refill = false;
            }
            nc_read++;

        } catch (IOException e) {
            e.printStackTrace();
        }
        setPointerPosition(nc_read);
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
}
