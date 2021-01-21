package testerConsumer;

import java.io.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class AppConfiguration {
    private Map<String,String> configMap;
    public AppConfiguration(String file) throws FileNotFoundException {
        Properties properties = new Properties();
        try(FileReader fileReader = new FileReader(file)){
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            properties.load(bufferedReader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        configMap= toMap(properties);
    }


    public Map<String,String> getComponentConfigMap(String componentName){

        Map<String,String> componentConfigMap = new HashMap<>();
        for(Map.Entry<String,String> entry:configMap.entrySet()){
            if(entry.getKey().startsWith(componentName)){
                componentConfigMap.put(entry.getKey(),entry.getValue());
                }
            }
        return componentConfigMap;
    }

    public String get(String key){
        return configMap.get(key);
    }

    public String set(String key,String value){
        return configMap.put(key,value);
    }

    private Map<String, String> toMap(Properties properties) {
        Map<String,String> result = new HashMap<>();
        Enumeration<?> propertyNames = properties.propertyNames();
        while(propertyNames.hasMoreElements()){
            String name = (String)propertyNames.nextElement();
            String value = properties.getProperty(name);
            result.put(name,value);
        }
        return result;
    }
}
