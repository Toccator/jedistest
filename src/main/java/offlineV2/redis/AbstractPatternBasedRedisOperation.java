package offlineV2.redis;

import PatternInsert.CsvImportVariable;
import com.mysql.cj.result.Row;
import offlineV2.util.JedisClusterPipeline;
import org.apache.kafka.common.protocol.types.Field;
import patternClear.PatternBasedRedisClearApp;
import sun.tools.java.Imports;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractPatternBasedRedisOperation implements IRedisOperation{

    public static final String VAR_STRING = "\\$\\{([a-zA-Z0-9])}";

    public static final Pattern VAR_PATTERN = Pattern.compile(VAR_STRING);

    protected ImportVariable generateVariable(String importFields){
        if(importFields == null){
            return null;
        }
        Matcher matcher = VAR_PATTERN.matcher(importFields);


        List<String> vars = new ArrayList<>();

        while (matcher.find()){
           vars.add(matcher.group(1));
        }

        return ImportVariable.builder().formatString(importFields.replaceAll(VAR_STRING,"%s")).fields(vars).build();
    }

    protected String convertedVariables(ImportVariable importVariable, Row varables){
        if(importVariable == null){
            return null;
        }
        return null;

//        Object[] objects = importVariable.getFields().stream().map(varables::getNull).toArray(Object[]::new);

    }
}
