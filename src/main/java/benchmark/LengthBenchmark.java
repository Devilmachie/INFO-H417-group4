package benchmark;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import streaminput.BufferReader;
import streaminput.CharacterReader;
import streaminput.LineReader;
import streaminput.MappedReader;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class LengthBenchmark {

    @State(Scope.Thread)
    public static class NoSizeParameters
    {
        @Param({"C:\\Users\\lenge\\IdeaProjects\\INFO-H417-group4\\imdb\\aka_name.csv" ,
                "C:\\Users\\lenge\\IdeaProjects\\INFO-H417-group4\\imdb\\aka_title.csv" ,
                "C:\\Users\\lenge\\IdeaProjects\\INFO-H417-group4\\imdb\\cast_info.csv" ,
                "C:\\Users\\lenge\\IdeaProjects\\INFO-H417-group4\\imdb\\char_name.csv" ,
                "C:\\Users\\lenge\\IdeaProjects\\INFO-H417-group4\\imdb\\company_name.csv" ,
                "C:\\Users\\lenge\\IdeaProjects\\INFO-H417-group4\\imdb\\company_type.csv" ,
                "C:\\Users\\lenge\\IdeaProjects\\INFO-H417-group4\\imdb\\complete_cast.csv" ,
                "C:\\Users\\lenge\\IdeaProjects\\INFO-H417-group4\\imdb\\comp_cast_type.csv" ,
                "C:\\Users\\lenge\\IdeaProjects\\INFO-H417-group4\\imdb\\info_type.csv" ,
                "C:\\Users\\lenge\\IdeaProjects\\INFO-H417-group4\\imdb\\keyword.csv" ,
                "C:\\Users\\lenge\\IdeaProjects\\INFO-H417-group4\\imdb\\kind_type.csv" ,
                "C:\\Users\\lenge\\IdeaProjects\\INFO-H417-group4\\imdb\\link_type.csv" ,
                "C:\\Users\\lenge\\IdeaProjects\\INFO-H417-group4\\imdb\\movie_companies.csv" ,
                "C:\\Users\\lenge\\IdeaProjects\\INFO-H417-group4\\imdb\\movie_info.csv" ,
                "C:\\Users\\lenge\\IdeaProjects\\INFO-H417-group4\\imdb\\movie_info_idx.csv" ,
                "C:\\Users\\lenge\\IdeaProjects\\INFO-H417-group4\\imdb\\movie_keyword.csv" ,
                "C:\\Users\\lenge\\IdeaProjects\\INFO-H417-group4\\imdb\\movie_link.csv" ,
                "C:\\Users\\lenge\\IdeaProjects\\INFO-H417-group4\\imdb\\name.csv" ,
                "C:\\Users\\lenge\\IdeaProjects\\INFO-H417-group4\\imdb\\person_info.csv" ,
                "C:\\Users\\lenge\\IdeaProjects\\INFO-H417-group4\\imdb\\role_type.csv" ,
                "C:\\Users\\lenge\\IdeaProjects\\INFO-H417-group4\\imdb\\schematext.sql" ,
                "C:\\Users\\lenge\\IdeaProjects\\INFO-H417-group4\\imdb\\title.csv"
        })
        public String fileName;

    }

    @State(Scope.Thread)
    public static class WithSizeParameters
    {
        @Param({"C:\\Users\\lenge\\IdeaProjects\\INFO-H417-group4\\imdb\\aka_name.csv" ,
                "C:\\Users\\lenge\\IdeaProjects\\INFO-H417-group4\\imdb\\aka_title.csv" ,
                "C:\\Users\\lenge\\IdeaProjects\\INFO-H417-group4\\imdb\\cast_info.csv" ,
                "C:\\Users\\lenge\\IdeaProjects\\INFO-H417-group4\\imdb\\char_name.csv" ,
                "C:\\Users\\lenge\\IdeaProjects\\INFO-H417-group4\\imdb\\company_name.csv" ,
                "C:\\Users\\lenge\\IdeaProjects\\INFO-H417-group4\\imdb\\company_type.csv" ,
                "C:\\Users\\lenge\\IdeaProjects\\INFO-H417-group4\\imdb\\complete_cast.csv" ,
                "C:\\Users\\lenge\\IdeaProjects\\INFO-H417-group4\\imdb\\comp_cast_type.csv" ,
                "C:\\Users\\lenge\\IdeaProjects\\INFO-H417-group4\\imdb\\info_type.csv" ,
                "C:\\Users\\lenge\\IdeaProjects\\INFO-H417-group4\\imdb\\keyword.csv" ,
                "C:\\Users\\lenge\\IdeaProjects\\INFO-H417-group4\\imdb\\kind_type.csv" ,
                "C:\\Users\\lenge\\IdeaProjects\\INFO-H417-group4\\imdb\\link_type.csv" ,
                "C:\\Users\\lenge\\IdeaProjects\\INFO-H417-group4\\imdb\\movie_companies.csv" ,
                "C:\\Users\\lenge\\IdeaProjects\\INFO-H417-group4\\imdb\\movie_info.csv" ,
                "C:\\Users\\lenge\\IdeaProjects\\INFO-H417-group4\\imdb\\movie_info_idx.csv" ,
                "C:\\Users\\lenge\\IdeaProjects\\INFO-H417-group4\\imdb\\movie_keyword.csv" ,
                "C:\\Users\\lenge\\IdeaProjects\\INFO-H417-group4\\imdb\\movie_link.csv" ,
                "C:\\Users\\lenge\\IdeaProjects\\INFO-H417-group4\\imdb\\name.csv" ,
                "C:\\Users\\lenge\\IdeaProjects\\INFO-H417-group4\\imdb\\person_info.csv" ,
                "C:\\Users\\lenge\\IdeaProjects\\INFO-H417-group4\\imdb\\role_type.csv" ,
                "C:\\Users\\lenge\\IdeaProjects\\INFO-H417-group4\\imdb\\schematext.sql" ,
                "C:\\Users\\lenge\\IdeaProjects\\INFO-H417-group4\\imdb\\title.csv"
        })
        public String fileName;

        @Param({"2" ,
                "4" ,
                "8" ,
                "16" ,
                "32" ,
                "64" ,
                "128" ,
                "256" ,
                "512" ,
                "1024" ,
                "2048" ,
                "4096" ,
                "8192" ,
                "16384" ,
                "32768" ,
                "65536"
        })
        public int bufferSize;

    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    @Warmup(iterations = 3)
    @Measurement(iterations = 20)
    @Timeout(time = 600)
    public void singleCharacterReader(Blackhole bh, NoSizeParameters parameters)
    {


        File fp = new File(parameters.fileName);
        CharacterReader creader = new CharacterReader(fp);
        long sum = 0;
        String lineRead;
        while(! creader.eos_reached())
        {
            lineRead = creader.readln();
            sum += lineRead.length();
        }
        bh.consume(sum);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    @Warmup(iterations = 3)
    @Measurement(iterations = 20)
    @Timeout(time = 600)
    public void bufferedReader(Blackhole bh, WithSizeParameters parameters)
    {


        File fp = new File(parameters.fileName);
        BufferReader breader = new BufferReader(fp, parameters.bufferSize);
        long sum = 0;
        String lineRead;
        while(! breader.eos_reached())
        {
            lineRead = breader.readln();
            sum += lineRead.length();
        }
        bh.consume(sum);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    @Warmup(iterations = 3)
    @Measurement(iterations = 20)
    @Timeout(time = 600)
    public void lineReader(Blackhole bh, NoSizeParameters parameters)
    {


        File fp = new File(parameters.fileName);
        LineReader lreader = new LineReader(fp);
        long sum = 0;
        String lineRead;
        while(! lreader.eos_reached())
        {
            lineRead = lreader.readln();
            sum += lineRead.length();
        }
        bh.consume(sum);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    @Warmup(iterations = 3)
    @Measurement(iterations = 20)
    @Timeout(time = 600)
    public void mappedReader(Blackhole bh, WithSizeParameters parameters)
    {


        File fp = new File(parameters.fileName);
        MappedReader mreader = new MappedReader(fp, parameters.bufferSize);
        long sum = 0;
        String lineRead;
        while(! mreader.eos_reached())
        {
            lineRead = mreader.readln();
            sum += lineRead.length();
        }
        bh.consume(sum);
    }


}