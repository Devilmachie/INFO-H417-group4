package streaminput;

import java.io.*;

public class LineReader implements StreamReader {
    
    private BufferedReader reader;
    private File fp;
    private boolean eos = false;

    public LineReader(File fp) {
        this.fp = fp;
        try 
        {
            reader = new BufferedReader(new FileReader(fp));
            reader.mark(0);
        } catch (FileNotFoundException fnf) 
        {
            fnf.printStackTrace();
        } catch (IOException marknsupported) {
            marknsupported.printStackTrace();
        }
    }

    @Override
    public String readln() {
        String line = null;
        try 
        {
            line = reader.readLine();
            if(line == null){
                eos = true;
                line = "";
            }
        } catch (IOException e) 
        {
            e.printStackTrace();
        }
        return line;
    }

    @Override
    public boolean eos_reached() {
        return eos;
    }

    @Override
    public void setPointerPosition(long pos) {
        try {
            reader.reset();
            reader.skip(pos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        try {
            reader.close();
        } catch (IOException nc) {
            nc.printStackTrace();
        }
    }

    public boolean isStreamOpen()
    {
        boolean is_open = true;
        try{
            reader.ready();
        } catch (IOException e) {
            is_open = false;
        }
        return is_open;
    }
}
