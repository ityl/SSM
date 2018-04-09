package com.ssm.boot.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yanglei on 2018/4/3.
 */
public class StringUtil {

    public static void main(String[] args) {
        String abc = "1,2,3,4,5,6";
        String [] a = abc.split(",");
        for (int i = 0; i < a.length; i++) {
            System.out.println(a[i]+isMatch(a[i]));
        }
    }

    public static boolean isMatch(String input){
        Matcher mer = Pattern.compile("^[+-]?[0-9]+$").matcher(input);
        return mer.find();
    }

}
