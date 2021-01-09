package nsertTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Test4 {

    public static void main(String[] args) {
        /**
         * I:{key1}:{key2}
         * I:%s:%s
         * I:key1:key2
         */

        Map<String, String> map = new HashMap<>();
        map.put("field1", "value1");
        map.put("field2", "value2");
        map.put("field3", "value3");
        map.put("field4", "value4");
        map.put("field5", "value5");
        map.put("result", "I::%s:%s");
        String prefix = "field";

        String result = map.get("result");
        int count = getCount(result, "%s");
        System.out.println(count);
        String[] values = new String[count];
        for (int i = 1; i <= count; i++) {
            values[i-1] = map.get(prefix + i);
        }
        Arrays.asList(values).forEach(System.out::println);
        System.out.println(String.format(result, values));


    }

    public static int getCount(String str, String key) {
        if (str == null || key == null || "".equals(str.trim()) || "".equals(key.trim())) {
            return 0;
        }
        int count = 0;
        int index = 0;
        while ((index = str.indexOf(key, index)) != -1) {
            index = index + key.length();
            count++;
        }
        return count;
    }
}
