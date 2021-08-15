package benchmark;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import streaminput.BufferReader;
import streaminput.CharacterReader;
import streaminput.LineReader;
import streaminput.StreamReader;
import streamoutput.BufferWriter;
import streamoutput.CharacterWriter;
import streamoutput.LineWriter;
import streamoutput.MappedWriter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(value = 1)
@Warmup(iterations = 3)
@Measurement(iterations = 8)
public class RRMerge {
    @State(Scope.Benchmark)
    public static class NoSizeParameters {

        //FILES ARE NOW ORDERED BY FILE SIZE ORDER
        public String[] fileName = {"C:\\Users\\lenge\\IdeaProjects\\INFO-H417-group4\\imdb\\comp_cast_type.csv", //45 bytes
                "C:\\Users\\lenge\\IdeaProjects\\INFO-H417-group4\\imdb\\kind_type.csv",      //85 bytes
                "C:\\Users\\lenge\\IdeaProjects\\INFO-H417-group4\\imdb\\role_type.csv",      //160 bytes
                "C:\\Users\\lenge\\IdeaProjects\\INFO-H417-group4\\imdb\\link_type.csv",      //261 bytes
                "C:\\Users\\lenge\\IdeaProjects\\INFO-H417-group4\\imdb\\info_type.csv",      //1928 bytes
                "C:\\Users\\lenge\\IdeaProjects\\INFO-H417-group4\\imdb\\movie_link.csv",     //656584
                "C:\\Users\\lenge\\IdeaProjects\\INFO-H417-group4\\imdb\\keyword.csv",        //3791536
                "C:\\Users\\lenge\\IdeaProjects\\INFO-H417-group4\\imdb\\company_name.csv",   //17802021
                "C:\\Users\\lenge\\IdeaProjects\\INFO-H417-group4\\imdb\\cast_info.csv"};     //1418137141

        public String outputFilePath = "rrmerge.out";

        @Param({"2", "4", "8"})
        public int k;
    }

    @State(Scope.Benchmark)
    public static class WithSizeParameters {
        //FILES ARE NOW ORDERED BY FILE SIZE ORDER
        public String[] fileName = {"C:\\Users\\lenge\\IdeaProjects\\INFO-H417-group4\\imdb\\comp_cast_type.csv", //45 bytes
                                    "C:\\Users\\lenge\\IdeaProjects\\INFO-H417-group4\\imdb\\kind_type.csv",      //85 bytes
                                    "C:\\Users\\lenge\\IdeaProjects\\INFO-H417-group4\\imdb\\role_type.csv",      //160 bytes
                                    "C:\\Users\\lenge\\IdeaProjects\\INFO-H417-group4\\imdb\\link_type.csv",      //261 bytes
                                    "C:\\Users\\lenge\\IdeaProjects\\INFO-H417-group4\\imdb\\info_type.csv",      //1928 bytes
                                    "C:\\Users\\lenge\\IdeaProjects\\INFO-H417-group4\\imdb\\movie_link.csv",     //656584
                                    "C:\\Users\\lenge\\IdeaProjects\\INFO-H417-group4\\imdb\\keyword.csv",        //3791536
                                    "C:\\Users\\lenge\\IdeaProjects\\INFO-H417-group4\\imdb\\company_name.csv",   //17802021
                                    "C:\\Users\\lenge\\IdeaProjects\\INFO-H417-group4\\imdb\\cast_info.csv"};     //1418137141

        public String outputFilePath = "rrmerge.out";

        @Param({"1024",
                "4096",
                "16384",
                "65536",
                "131072",
                "262144"
        })
        public int bufferSize;

        @Param({"2", "4", "6"})
        public int k;
    }

    @Benchmark
    public void rrmerge_LineReader_singleCharacterWriter(Blackhole bh, WithSizeParameters parameters)
    {
        boolean allFiledCopied = false;
        ArrayList<StreamReader> filesToMerge = new ArrayList<>();
        String line;
        File out = new File(parameters.outputFilePath);
        CharacterWriter cwriter = new CharacterWriter(out);
        if(parameters.k == 2)
        {
            filesToMerge.add(new LineReader(new File(parameters.fileName[8])));
            filesToMerge.add(new LineReader(new File(parameters.fileName[7])));
        }
        else if (parameters.k == 4)
        {
            filesToMerge.add(new LineReader(new File(parameters.fileName[6])));
            filesToMerge.add(new LineReader(new File(parameters.fileName[5])));
            filesToMerge.add(new LineReader(new File(parameters.fileName[4])));
            filesToMerge.add(new LineReader(new File(parameters.fileName[3])));
        }
        else if (parameters.k == 6)
        {
            filesToMerge.add(new LineReader(new File(parameters.fileName[0])));
            filesToMerge.add(new LineReader(new File(parameters.fileName[1])));
            filesToMerge.add(new LineReader(new File(parameters.fileName[2])));
            filesToMerge.add(new LineReader(new File(parameters.fileName[3])));
            filesToMerge.add(new LineReader(new File(parameters.fileName[4])));
            filesToMerge.add(new LineReader(new File(parameters.fileName[5])));
        }
        while (! allFiledCopied)
        {
            allFiledCopied = true;
            for (StreamReader reader : filesToMerge)
            {
                if(! reader.eos_reached())
                {
                    line = reader.readln();
                    try {
                        cwriter.writeLine(line);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                allFiledCopied = allFiledCopied && reader.eos_reached();
            }
        }

    }

    @Benchmark
    public void rrmerge_LineReader_LineWriter(Blackhole bh, WithSizeParameters parameters)
    {
        boolean allFiledCopied = false;
        ArrayList<StreamReader> filesToMerge = new ArrayList<>();
        String line;
        File out = new File(parameters.outputFilePath);
        LineWriter lwriter = new LineWriter(out);
        if(parameters.k == 2)
        {
            filesToMerge.add(new LineReader(new File(parameters.fileName[8])));
            filesToMerge.add(new LineReader(new File(parameters.fileName[7])));
        }
        else if (parameters.k == 4)
        {
            filesToMerge.add(new LineReader(new File(parameters.fileName[6])));
            filesToMerge.add(new LineReader(new File(parameters.fileName[5])));
            filesToMerge.add(new LineReader(new File(parameters.fileName[4])));
            filesToMerge.add(new LineReader(new File(parameters.fileName[3])));
        }
        else if (parameters.k == 6)
        {
            filesToMerge.add(new LineReader(new File(parameters.fileName[0])));
            filesToMerge.add(new LineReader(new File(parameters.fileName[1])));
            filesToMerge.add(new LineReader(new File(parameters.fileName[2])));
            filesToMerge.add(new LineReader(new File(parameters.fileName[3])));
            filesToMerge.add(new LineReader(new File(parameters.fileName[4])));
            filesToMerge.add(new LineReader(new File(parameters.fileName[5])));
        }
        while (! allFiledCopied)
        {
            allFiledCopied = true;
            for (StreamReader reader : filesToMerge)
            {
                if(! reader.eos_reached())
                {
                    line = reader.readln();
                    try {
                        lwriter.writeLine(line);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                allFiledCopied = allFiledCopied && reader.eos_reached();
            }
        }

    }

    @Benchmark
    public void rrmerge_LineReader_BufferedWriter(Blackhole bh, WithSizeParameters parameters)
    {
        boolean allFiledCopied = false;
        ArrayList<StreamReader> filesToMerge = new ArrayList<>();
        String line;
        File out = new File(parameters.outputFilePath);
        BufferWriter bwriter = new BufferWriter(out, parameters.bufferSize);
        if(parameters.k == 2)
        {
            filesToMerge.add(new LineReader(new File(parameters.fileName[8])));
            filesToMerge.add(new LineReader(new File(parameters.fileName[7])));
        }
        else if (parameters.k == 4)
        {
            filesToMerge.add(new LineReader(new File(parameters.fileName[6])));
            filesToMerge.add(new LineReader(new File(parameters.fileName[5])));
            filesToMerge.add(new LineReader(new File(parameters.fileName[4])));
            filesToMerge.add(new LineReader(new File(parameters.fileName[3])));
        }
        else if (parameters.k == 6)
        {
            filesToMerge.add(new LineReader(new File(parameters.fileName[0])));
            filesToMerge.add(new LineReader(new File(parameters.fileName[1])));
            filesToMerge.add(new LineReader(new File(parameters.fileName[2])));
            filesToMerge.add(new LineReader(new File(parameters.fileName[3])));
            filesToMerge.add(new LineReader(new File(parameters.fileName[4])));
            filesToMerge.add(new LineReader(new File(parameters.fileName[5])));
        }
        while (! allFiledCopied)
        {
            allFiledCopied = true;
            for (StreamReader reader : filesToMerge)
            {
                if(! reader.eos_reached())
                {
                    line = reader.readln();
                    try {
                        bwriter.writeLine(line);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                allFiledCopied = allFiledCopied && reader.eos_reached();
            }
        }

    }

    @Benchmark
    public void rrmerge_LineReader_MappedWriter(Blackhole bh, WithSizeParameters parameters)
    {
        boolean allFiledCopied = false;
        ArrayList<StreamReader> filesToMerge = new ArrayList<>();
        String line;
        File out = new File(parameters.outputFilePath);
        MappedWriter mappedWriter = new MappedWriter(out, parameters.bufferSize);
        if(parameters.k == 2)
        {
            filesToMerge.add(new LineReader(new File(parameters.fileName[8])));
            filesToMerge.add(new LineReader(new File(parameters.fileName[7])));
        }
        else if (parameters.k == 4)
        {
            filesToMerge.add(new LineReader(new File(parameters.fileName[6])));
            filesToMerge.add(new LineReader(new File(parameters.fileName[5])));
            filesToMerge.add(new LineReader(new File(parameters.fileName[4])));
            filesToMerge.add(new LineReader(new File(parameters.fileName[3])));
        }
        else if (parameters.k == 6)
        {
            filesToMerge.add(new LineReader(new File(parameters.fileName[0])));
            filesToMerge.add(new LineReader(new File(parameters.fileName[1])));
            filesToMerge.add(new LineReader(new File(parameters.fileName[2])));
            filesToMerge.add(new LineReader(new File(parameters.fileName[3])));
            filesToMerge.add(new LineReader(new File(parameters.fileName[4])));
            filesToMerge.add(new LineReader(new File(parameters.fileName[5])));
        }
        while (! allFiledCopied)
        {
            allFiledCopied = true;
            for (StreamReader reader : filesToMerge)
            {
                if(! reader.eos_reached())
                {
                    line = reader.readln();
                    try {
                        mappedWriter.writeLine(line);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                allFiledCopied = allFiledCopied && reader.eos_reached();
            }
        }

    }
}
