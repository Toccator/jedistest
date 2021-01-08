package insertData;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectorsTest {
    public static void main(String[] args) {
        Map<String, Person> map = Stream
                .of(Person.valueOf("小明", '男', 18), Person.valueOf("小丽", '女', 16), Person.valueOf("小王", '男', 19))
                .collect(Collectors.toMap(Person::getName, Function.identity()));
        System.out.println("map:" + map);

        Map<String, Person> map2 = Stream
                .of(Person.valueOf("小明", '男', 18), Person.valueOf("小丽", '女', 16), Person.valueOf("小明", '男', 19))
                .collect(Collectors.toMap(Person::getName, Function.identity(), (x, y) -> y));
        System.out.println("如果有2个key重复了就用新的，‘y’,想用旧的代替就用'x',自己选择，map2:" + map2);

        /*
         * 需求：要求将已有的国家名为key, 人名为value(当然也可以是对象)集合。
         */


        Map<String, Set<String>> map3 = Stream
                .of(Person.valueOf("China", "小明"), Person.valueOf("China", "小丽"), Person.valueOf("us", "Jack"),
                        Person.valueOf("us", "Alice"))
                .collect(Collectors.toMap(Person::getCountry, p -> Collections.singleton(p.getName()), (x, y) -> {
                    Set<String> set = new HashSet<>(x);
                    set.addAll(y);
                    return set;
                }));
        System.out.println("map3:" + map3);

        // 将所有元素检查key不重复且最终包装成一个TreeMap对象
        Map<String, String> map4 = Stream.of(Person.valueOf("China", "小明"), Person.valueOf("Chinsa", "小丽"), Person.valueOf("us", "Jack"),
                Person.valueOf("uss", "Alice"))
                .collect(Collectors.toMap(
                        Person::getCountry,
                        Person::getName, (x, y) ->
                        {
                            throw new IllegalStateException();
                        },TreeMap::new));
        System.out.println(map4);
    }
}

