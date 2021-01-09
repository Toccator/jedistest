package nsertTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {


    public static void main(String[] args) {
        String separator = ":";
        String regex = "\\{([^}]*)\\}";
        String source = "5:::{field1}:{field2}";
        String target1 = "%s";
        String target2 = "%s";
        List<String> targetList = new ArrayList<>();
        targetList.add(target1);
        targetList.add(target2);
        //5:::cif_no:ip
        Pattern pattern = Pattern.compile(regex);
        List<String> sourceList = Arrays.asList(source.split(separator));
        int count = 0;

        StringBuilder sb = new StringBuilder();
        boolean flag = true;
        for (String tmp : sourceList) {
            if (tmp.length() > 0) {
                Matcher matcher = pattern.matcher(tmp);
                if (matcher.find()) {
//                    System.out.println(matcher.find());
                    tmp = matcher.group().replaceAll(regex, targetList.get(count));
                    count++;
                }
            }
            System.out.println(count);
            if (flag) {
                sb.append(tmp);
                flag = false;
            } else {
                sb.append(separator + tmp);
            }

        }

        System.out.println(sb.toString());
    }
}
