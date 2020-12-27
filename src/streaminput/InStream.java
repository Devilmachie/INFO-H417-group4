package streaminput;

import java.io.File;
import java.io.IOException;

public class InStream {
    private File fp;
    private boolean is_open = false;
    private StreamReader reader;

    public void open(String _path) throws IOException {
        fp = new File(_path);
        if(!fp.exists() || !fp.canRead())
        {
            String err_msg = String.format("File specified at %s does not exists or can not be read.\n", _path);
            throw new IOException(err_msg);
        }
        is_open = true;
    }

    public String readln()
    {
        return reader.readln();
    }
    public void seek(long pos)
    {
        reader.setPointerPosition(pos);
    }

    public boolean end_of_stream()
    {
        return reader.eos_reached();
    }
}
