package CSVtoJava;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Assert {

    public static void main(String[] args) {
        ArrayList<String> a = new ArrayList<>();
        a.add("a");
        a.add("b");
        a.add("c");
        ArrayList<String> b = new ArrayList<>();
        b.add("a");
        b.add("D");


        assert Arrays.asList(a).containsAll(b)
                :"C";
//        System.out.println("断言通过!");


//        boolean isSafe = true;
//        assert isSafe : "Not safe at all";
//        System.out.println("断言通过!");
    }
}
