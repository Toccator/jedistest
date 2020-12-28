package jedis.util;

import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

public class ReadProperties {

    public static String getValue(String key){
        String url = null;
        Properties prop = new Properties();
        try {
            ClassLoader classLoader = ReadProperties.class.getClassLoader();
            InputStream inputStream  =classLoader.getResourceAsStream("test.properties");
            prop.load(inputStream);
            Iterator<String> it = prop.stringPropertyNames().iterator();
            while (it.hasNext()){
                if(it.next().equals(key)){
                    url = prop.getProperty(key);
                }
            }
        }catch (Exception e){

        }
        return url;
    }

}
