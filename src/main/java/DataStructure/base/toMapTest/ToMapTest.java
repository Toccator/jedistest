package DataStructure.base.toMapTest;

import java.util.*;
import java.util.stream.Collectors;

public class ToMapTest {

    public static void main(String[] args) {

        List<GroupBrandCateBO> list = new ArrayList<>(
                Arrays.asList(
                        new GroupBrandCateBO("v3", "g1", "b1"),
                        new GroupBrandCateBO("v3", "g2", "b1"),
                        new GroupBrandCateBO("v3", "g3", "b3")
                )
        );

        String a = "v3";
        GroupBrandCateBO vo =GroupBrandCateBO.builder().a(a).
                build();

//        Map<String, Object> map = list.stream().collect(Collectors.toMap(GroupBrandCateBO::getVersion, v -> v, (v1, v2) ->  v1));
//


//        Map<String, GroupBrandCateBO> map = list.stream().collect(Collectors.toMap(GroupBrandCateBO::getVersion, v -> v, (v1, v2) ->  v1));
//        System.out.println(map.get(vo.getVersion()).getB());
//
//        Map<Object, Object> map2 = list.stream().collect(Collectors.toMap(item -> item.getVersion(), item -> item.getGroupCode(), (oldVal, currVal) -> oldVal, LinkedHashMap::new));
        Map<String, Object> map2 = list.stream().collect(Collectors.toMap(item -> item.getVersion(), item -> item.getGroupCode(), (oldVal, currVal) -> oldVal, LinkedHashMap::new));

//        System.out.println(map2.getClass());
//        System.out.println(map2.get(vo.getVersion()));
        System.out.println(map2);
//
//        Map<Object, Object> map0 = list.stream().collect(Collectors.toMap(item -> item.getVersion(), item -> item.getGroupCode(), (oldVal, currVal) -> currVal));
//        System.out.println(map0);


    }



}
