package DataStructure.base;

import java.util.ArrayList;
import java.util.Collections;

public class setTolist {
    public static void main(String[] args){
        ArrayList l = new ArrayList();
        Collections.addAll(l,new String[]{"1","2","3"});
        System.out.println(l.get(0));
    }

}
