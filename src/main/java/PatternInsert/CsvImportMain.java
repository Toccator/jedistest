package PatternInsert;

import com.alibaba.fastjson.JSON;
import insertData.utils.RedisPipeline;
import jedis.GetOpt.GetOpt;
import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CsvImportMain {
    public static final  String VAR_STRING = "\\$\\{([a-zA-z0-9]+)}";
    public static final Pattern VAR_PATTERN = Pattern.compile(VAR_STRING);
    public static void main(String[] args)throws IOException{
        GetOpt getOpt = new GetOpt(args,"c:e:h");
        String jsonFile = "/user/etrm/offline_calc/test.csv";
        String csvPathAndName = "/user/etrm/offline_calc/json";
        int option;
        while ((option = getOpt.getNextOption()) != -1) {
            switch (option) {
                case 'd':
                    jsonFile  = getOpt.getOptionArg();
                    break;
                case 'h' :
                    csvPathAndName = getOpt.getOptionArg();
                    break;
            }
        }

// JSON 解析要导入的文件
        CsvImportConfig csvImportConfig = JSON.parseObject(FileUtils.readFileToString(new File(jsonFile)),CsvImportConfig.class);
        csvImportConfig.setKeyVariable(generateVariable(csvImportConfig.getKey()));
        csvImportConfig.setFieldVariables(generateVariable(csvImportConfig.getField()));
        csvImportConfig.setValueVariable(generateVariable(csvImportConfig.getValue()));
// CsvImportOperation

        CsvImportOperation csvImportOperation = CsvImportOperation.OPERATIONS.get(csvImportConfig.getOperation());
        File file1 = new File(csvPathAndName);

        RedisPipeline redisPipeline = new RedisPipeline();

        try(BufferedReader br = new BufferedReader(new FileReader(file1))) {
            String headerLine = br.readLine();
// 定义一个
            Map<String,Integer> headerToIndexMap = processHeaders(headerLine,csvImportConfig);

            String line = null;
            long count = 0;
            while ((line = br.readLine())!= null) {
                try {
                    Csvline csvline = convertLine(line,csvImportConfig,headerToIndexMap);
                    csvImportOperation.importCsvLine(redisPipeline,csvline);
                }catch (Exception e){
                    continue;
                }
                count++;
                if(count == 20000){
                    count = 0;
                    redisPipeline.sync();
                }
                redisPipeline.sync();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }


//将${field}替换成 %s
    private static CsvImportVariable generateVariable(String importFields) {
        if(importFields == null){
            return null;
        }
        Matcher matcher = VAR_PATTERN.matcher(importFields);
        List<String> vars = new ArrayList<>();
        while (matcher.find()){
            vars.add(matcher.group(1));
        }
        return CsvImportVariable.builder()
                .formatString(importFields.replaceAll(VAR_STRING,"%s"))
                .fields(vars).build();
    }




// 填数，将field 填进去
    private static Csvline convertLine(String line, CsvImportConfig csvImportConfig, Map<String, Integer> headerToIndexMap) {
        String[] contents = line.split(csvImportConfig.getDelimiter());
        //创建一个map 进行 将每一行的数据headers[i]
        Map<String,String> variables = new HashMap<>(contents.length);
        for(Map.Entry<String, Integer> entry: headerToIndexMap.entrySet()){
            variables.put(entry.getKey(),contents[entry.getValue()]);
        }
        return Csvline.builder()
                .key(contentedVariable(csvImportConfig.getKeyVariable(),variables))
                .field(contentedVariable(csvImportConfig.getKeyVariable(),variables))
                .value(contentedVariable(csvImportConfig.getKeyVariable(),variables))
                .build();

    }



//
    private static String contentedVariable(CsvImportVariable csvImportVariable , Map<String, String> variables) {
        if(csvImportVariable == null){
            return null;
        }
        return String.format(csvImportVariable.getFormatString(),
                csvImportVariable.getFields().stream().map(variables::get).collect(Collectors.toList()).toArray(new Object[0]));

    }

    private static Map<String,Integer> processHeaders(String headerLine,CsvImportConfig csvImportConfig){
        String[] headers = headerLine.split(csvImportConfig.getDelimiter());
        Map<String,Integer> HeaderToIndexMap = new HashMap<>();
        for(int i = 0; i< headers.length;i++){
            HeaderToIndexMap.putIfAbsent(headers[i],i);
        }
        return HeaderToIndexMap;

    }
}
