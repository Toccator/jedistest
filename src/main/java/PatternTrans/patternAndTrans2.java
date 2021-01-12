package PatternTrans;


import java.util.Arrays;
import java.util.List;

public class patternAndTrans2 {

    public static void main(String[] args) {
        test1();
//        test2();
    }

    public static void test1() {
        List<String> list = Arrays.asList("10", "20", "30", "40","15");

        int i=0;
        for(String l :list){
            if(i<Integer.valueOf(l)){
                i=Integer.valueOf(l);
            }
        }
        System.out.println(i);



        //调用 stream 里的 min 或 max 函数得到最大值或最小值。
//        System.out.println("\nstream()的方法来取得最大最小值");
////        String coolestString = List.stream()
////                .map(s -> new Object[] {s, Index(s)})
////                .max(Comparator.comparingInt(a -> (int)a[1]))
////                .map(a -> (String)a[0])
////                .orElse(null);
//
//        int max = list.stream().filter(e -> e != null).max(Integer.valueOf(String.valueOf(Comparator.comparingInt()))).orElse(null);
//        System.out.println("max=" + max);
////        int min = list.stream().filter(e -> e != null).min(Comparator.naturalOrder()).orElse(null);
//        System.out.println("min=" + min);
//
//
//        System.out.println("\n使用Collections类的方法来取得最大最小值");
//        //使用Collections
//        System.out.println("max=" + Collections.max(list));
//        System.out.println("min=" + Collections.min(list));

    }
//
//    public static void test2() {
//        List<Long> list = Arrays.asList();
//
//        //什么时候会执行orElse()呢？ 当数组为空的时候，就会执行。
//        System.out.println("\n测试orElse()");
//        long max = list.stream().filter(e -> e != null).max(Comparator.naturalOrder()).orElse(new Date().getTime());
//        System.out.println("max=" + max);
//        long min = list.stream().filter(e -> e != null).min(Comparator.naturalOrder()).orElse((long) 0);
//        System.out.println("min=" + min);
//
//
//    }


}



