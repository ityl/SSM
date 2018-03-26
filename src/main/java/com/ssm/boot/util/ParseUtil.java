
package com.ssm.boot.util;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

public class ParseUtil {
    public static final Log logger = LogFactory.getLog(ParseUtil.class);

    public ParseUtil() {
    }

    public static int paseInt(String dataStr, int defaultV) {
        int r = defaultV;

        try {
            if(NumberUtils.isDigits(dataStr)) {
                r = Integer.parseInt(dataStr);
            }
        } catch (Exception var4) {
            logger.error("parse int value error,val:" + dataStr, var4);
        }

        return r;
    }

    public static long paseLong(String dataStr, long defaultV) {
        long r = defaultV;

        try {
            if(NumberUtils.isDigits(dataStr)) {
                r = Long.parseLong(dataStr);
            }
        } catch (Exception var6) {
            logger.error("parse long value error,val:" + dataStr, var6);
        }

        return r;
    }

    public static float parseFloat(String dataStr, float defaultV) {
        float r = defaultV;

        try {
            if(NumberUtils.isNumber(dataStr)) {
                r = Float.parseFloat(dataStr);
            }
        } catch (Exception var4) {
            logger.error("parse float value error,val:" + dataStr, var4);
        }

        return r;
    }

    public static int[] parseIntArray(String[] s, int defaultValue) throws Exception {
        if(s == null) {
            return null;
        } else {
            int[] x_ret = new int[s.length];

            for(int i = 0; i < s.length; ++i) {
                x_ret[i] = paseInt(s[i], defaultValue);
            }

            return x_ret;
        }
    }

    public static List<Integer> parseIntList(String s, String split) throws Exception {
        if(s != null && s.length() != 0) {
            if(split == null || split.length() == 0) {
                split = ",";
            }

            String[] arrayStr = s.split(split);
            int[] arrayInt = parseIntArray(arrayStr, 0);
            if(arrayInt == null) {
                return null;
            } else {
                List<Integer> x_ret = new ArrayList();
                int[] var5 = arrayInt;
                int var6 = arrayInt.length;

                for(int var7 = 0; var7 < var6; ++var7) {
                    int i = var5[var7];
                    x_ret.add(Integer.valueOf(i));
                }

                return x_ret;
            }
        } else {
            return null;
        }
    }

    public static void main(String[] args) {
        try {
            System.out.println(9223372036854775807L);
            System.out.println(paseLong("5790299393819152393", 0L));
            System.out.println(9223372036854775807L > paseLong("5790299393819152393", 0L));
            System.out.println(paseLong("5790299393 81915239", 0L));
        } catch (Exception var2) {
            System.err.println(var2.getMessage());
        }

    }
}
