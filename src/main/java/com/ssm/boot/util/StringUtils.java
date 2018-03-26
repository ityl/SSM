package com.ssm.boot.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by vliu on 2017/6/22.
 */
public class StringUtils {

    public static boolean isEmpty(String str) {
        return str == null || "".equals(str) || str.equals("\"\"");
    }


    public static boolean isInteger(String input){
        Matcher mer = Pattern.compile("^[+-]?[0-9]+$").matcher(input);
        return mer.find();
    }
}
