package FunctionInterfaceTest;

import java.util.Arrays;
import java.util.Comparator;

/**
 * zcy
 * 如果一个方法的返回值类型是一个函数式接口，那么就可以直接返回一个Lambda表达式
 */

public class lambda_Comparator {
    //下面给出 lambda 以及实际替代的内部类写法



    //方法的返回值类型是一个函数式接口
    private static Comparator<String> newComparator(){
        return (a,b)->b.length()-a.length();
    }
    private static Comparator<String> newComparator1(){
        return new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                return b.length()-a.length();
            }
        };
    }
    public static void main(String[] args) {
        String[] array={"abc","ab","abcd"};
        System.out.println(Arrays.toString(array));
        Arrays.sort(array, newComparator1()); // 方式一
        Arrays.sort(array,(a,b)->b.length()-a.length());//更简单的方式
        System.out.println(Arrays.toString(array));
    }


}
