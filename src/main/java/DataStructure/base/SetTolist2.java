package DataStructure.base;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

public class SetTolist2  {

    public static void main(String[] args) {

        Stude stu=new Stude("1","gao","hz");
        Stude stu2=new Stude("2","aao","hz");
        Stude stu3=new Stude("3","zao","hz");

        Set<Stude> studeset=new HashSet<Stude>();
        studeset.add(stu);
        studeset.add(stu2);
        studeset.add(stu3);
        List<Stude> stuList=new ArrayList<Stude>();
        stuList.addAll(studeset);
        Collections.sort(stuList);
        for (Stude stude : stuList) {
            System.out.println(stude.getName());
        }
    }
@Setter
@Builder
@Getter
    private static class Stude implements Comparable<Object> {
        protected String num;
        protected String name;
        protected String e;

    @Override
//        public int compareTo(Object o) {
//            Stude stu1=(Stude)o;
//            return e.compareTo(stu1.e);
//
//        }
        public int compareTo(Object arg0) {
            Stude stu1=(Stude)arg0;
            return name.compareTo(stu1.getName());//根据name排序
        }
    }
}
