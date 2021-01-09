package nsertTest;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test3 {
    public static void main(String[] args) {
        String source = "5:::{field1}:{field2}";
        Test3 test3 = new Test3();
//        test2.getFormat(source);
        System.out.println(test3.getValue(source));

    }

    private StringBuilder getValue(String source) {
        String separator = ",";
        String regex = "\\{([^}]*)\\}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(source);
        StringBuilder sb = new StringBuilder();
        List<String> targetList = new ArrayList<>();
        Boolean flag = true;
//        int count = 0;
        while (matcher.find()) {
            String i1 = matcher.group().replaceAll("\\}", "");
            String i2 = i1.replaceAll("\\{", "");
//            System.out.println(i2);
            targetList.add(i2);
//            count++;
        }
        for (String tmp : targetList) {
//            System.out.println(tmp);
            if (flag) {
                sb.append(tmp);
                flag = false;
            } else {
                sb.append(separator + tmp);
            }
        }
//        System.out.println(sb);
        return  sb;

    }



}
