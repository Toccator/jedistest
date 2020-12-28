package DataStructure.base;

import java.util.ArrayList;
import java.util.List;

public class Solution {

    public static int lengthOflengthSubtring(String s){
        int maxLen = 0;
        StringBuilder sub = new StringBuilder(s.length());
        int fromIndex = 0;

        for (int i = 0; i < s.length(); i ++){
            char ch = s.charAt(i);

            int index = sub.indexOf(ch+"", fromIndex);  // 重复“字符”（字符串）的位置

            if (index != -1) fromIndex = index+1;  // 不断调整起始下标

            sub.append(ch);

            int len = sub.length() - fromIndex;  // 总长度 - 起始下标 = 当前子字符串的长度

            if (maxLen < len) maxLen = len;
        }

        return maxLen;
    }

    public static void main (String[] args) {
        String ss = "qwererw";
        int a = lengthOflengthSubtring(ss);
        System.out.println(a);
    }

}
