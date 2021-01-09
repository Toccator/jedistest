package PatternInsert;

import insertData.utils.RedisPipeline;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface CsvImportOperation {

    CsvImportOperation SET = new AbstractCsvImportOperation("SET") {
        @Override
        public void importCsvLine(RedisPipeline redisPipeline, Csvline csvline) {
            redisPipeline.sync();

        }
    };

    CsvImportOperation HSET = new AbstractCsvImportOperation("HSET") {
        @Override
        public void importCsvLine(RedisPipeline redisPipeline, Csvline csvline) {

        }
    };

    CsvImportOperation ZADD = new AbstractCsvImportOperation("ZADD") {
        @Override
        public void importCsvLine(RedisPipeline redisPipeline, Csvline csvline) {

        }
    };


    Map<String,CsvImportOperation> OPERATIONS  = Stream.of(SET,HSET,ZADD).collect(Collectors.toMap(CsvImportOperation::getName, Function.identity()));


    void importCsvLine(RedisPipeline redisPipeline, Csvline csvline);

    String getName();

    abstract class AbstractCsvImportOperation implements CsvImportOperation {
        protected final String name;
        public AbstractCsvImportOperation(String name) {
            this.name = name;
        }
        @Override
        public String getName(){
            return name;
        }


    }


}
