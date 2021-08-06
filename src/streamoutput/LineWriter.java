package streamoutput;

public class LineWriter implements StreamWriter
{


        @Override
        public boolean create() {
            return true;
        }

        @Override
    public void writeLine(String linetowrite) {

    }

    @Override
    public void close() {
    }
}

