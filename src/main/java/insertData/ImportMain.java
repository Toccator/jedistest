package insertData;

import jedis.GetOpt.GetOpt;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import insertData.AbstarctImportCsvToFeature;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.FileUtils;


public class ImportMain {
    private static final Map<String,AbstarctImportCsvToFeature> FEATURE_IMPORT_MAP = Stream.of(
                    new CsvtoRedis()
    ).collect(Collectors.toMap(AbstarctImportCsvToFeature::getName,Function.identity()));

    private static JsonDefinition readContext(String file) throws IOException {
        JsonDefinition result = JSON.parseObject(FileUtils.readFileToString(new File(file)),JsonDefinition.class);
        return result;
    }


    public static void main(String[] args) throws IOException {
        String path = null;
        String file = null;
        GetOpt getOpt = new GetOpt(args, "d:h");
        int c;
        while ((c = getOpt.getNextOption()) != -1) {
            System.out.println((char)c);
            switch (c) {
                case 'd':
                    path = getOpt.getOptionArg();
                    break;
                case 'h' :
                    file = getOpt.getOptionArg();
                    break;
            }
        }

        JsonDefinition jsonDefinition =readContext(file);
        ImportMain importMain = new ImportMain();

    }
}
