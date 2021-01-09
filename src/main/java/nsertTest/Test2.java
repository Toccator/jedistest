package nsertTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test2 {

    public static void main(String[] args) {
        String source = "5:::{field1}:{field2}";
        Test2 test2 = new Test2();
//        test2.getFormat(source);
        System.out.println(test2.getFormat(source));


    }

    public String getFormat(String source){
        String separator = ":";
        String regex = "\\{([^}]*)\\}";
        Pattern pattern = Pattern.compile(regex);
        String target = "%s";
        List<String> sourceList = Arrays.asList(source.split(separator));
        int count = 0;
        for (String tmp : sourceList) {
            Matcher matcher = pattern.matcher(tmp);
            if (matcher.find()) {
                count++;
            }
        }
        List<String> targetList = new ArrayList<>();
        for(int i = 0;i< count;i++){
            targetList.add(target);
        }

        //5:::cif_no:ip
        int count1 = 0;
        StringBuilder sb = new StringBuilder();
        boolean flag = true;
        for (String tmp : sourceList) {
            if (tmp.length() > 0) {
                Matcher matcher = pattern.matcher(tmp);
                if (matcher.find()) {
                    tmp = matcher.group().replaceAll(regex, targetList.get(count1));
                    count1++;
                }
            }
            if (flag) {
                sb.append(tmp);
                flag = false;
            } else {
                sb.append(separator + tmp);
            }
        }
        return sb.toString();
    }
}
