

package com.ssm.boot.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RequestUtil {
    private static Log logger = LogFactory.getLog(RequestUtil.class);

    public RequestUtil() {
    }

    public static void populate(HttpServletRequest request, Object obj) throws Exception {
        Map map = request.getParameterMap();

        try {
            BeanUtils.populate(obj, map);
        } catch (Exception var4) {
            logger.error("populate request parameters exception,map=" + map, var4);
            throw var4;
        }
    }

    public static Map<String, String> getRequestHeaders(HttpServletRequest request) {
        HashMap<String, String> res = new HashMap();
        Enumeration emu = request.getHeaderNames();

        while(emu.hasMoreElements()) {
            String key = (String)emu.nextElement();
            String value = request.getHeader(key);
            res.put(key, value);
        }

        return res;
    }

    public static String getRequestString(HttpServletRequest request, String name) {
        return getRequestString(request, name, (String)null);
    }

    public static String getRequestString(HttpServletRequest request, String name, String defaultValue) {
        String value = request.getParameter(name);
        if(value == null) {
            value = defaultValue;
        }

        return value;
    }

    public static int getRequestInt(HttpServletRequest request, String name) {
        return getRequestInt(request, name, 0);
    }

    public static int getRequestInt(HttpServletRequest request, String name, int defaultValue) {
        String value = getRequestString(request, name, (String)null);
        if(value == null) {
            return defaultValue;
        } else {
            try {
                return NumberUtils.toInt(value.trim(), defaultValue);
            } catch (Exception var5) {
                return defaultValue;
            }
        }
    }

    public static long getRequestLong(HttpServletRequest request, String name) {
        return getRequestLong(request, name, 0L);
    }

    public static long getRequestLong(HttpServletRequest request, String name, long defaultValue) {
        String value = getRequestString(request, name, "");
        return ParseUtil.paseLong(value, defaultValue);
    }

    public static String getRemoteIpAddress(HttpServletRequest request) {
        String rip = request.getRemoteAddr();
        String xff = request.getHeader("X-Forwarded-For");
        String ip;
        if(xff != null && xff.length() != 0) {
            int px = xff.indexOf(44);
            if(px != -1) {
                ip = xff.substring(0, px);
            } else {
                ip = xff;
            }
        } else {
            ip = rip;
        }

        return ip.trim();
    }

    public static String getIploc(HttpServletRequest request) {
        String DEFAULT_LOCAL = "CN0000";
        String iploc = request.getHeader("cmccip");
        if(iploc == null || iploc.length() == 0 || "unknown".equalsIgnoreCase(iploc)) {
            iploc = "CN0000";
        }

        return iploc;
    }

    public static String getGbgodeFromIploc(HttpServletRequest request) {
        String iploc = getIploc(request);
        if(StringUtils.indexOf(iploc, "CN") != -1) {
            iploc = StringUtils.substring(iploc, 2);
            if(iploc.length() > 6) {
                iploc = StringUtils.substring(iploc, 0, 6);
            }
        }

        return !"000000".equalsIgnoreCase(iploc) && !"0000".equalsIgnoreCase(iploc)?iploc:null;
    }

    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-source-id");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("x-forwarded-for");
        }

        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }

        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }

    public static String getCookieValue(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for(int i = 0; i < cookies.length; ++i) {
                if(cookies[i].getName().equalsIgnoreCase(name)) {
                    String value = cookies[i].getValue();
                    return value;
                }
            }
        }

        return null;
    }
}
