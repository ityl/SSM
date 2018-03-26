

package com.ssm.boot.util;

import com.google.common.collect.Maps;
import com.ssm.boot.enums.ErrorCodeEnum;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;

public class ResponseUtil {
    private static final String ENCODING_PREFIX = "encoding";
    public static final String NOCACHE_PREFIX = "no-cache";
    private static final String ENCODING_DEFAULT = "UTF-8";
    private static final boolean NOCACHE_DEFAULT = true;
    private static int Expires = 100;
    private static final int SUCCESS = 1;
    private static final int FAIL = -1;

    public ResponseUtil() {
    }

    public static void renderXml(HttpServletResponse response, String xml, String... headers) {
        render(response, "text/xml", xml, headers);
    }

    public static void renderText(HttpServletResponse response, String text, String... headers) {
        render(response, "text/plain", text, headers);
    }

    public static void renderText(HttpServletResponse response, int status, String text, String... headers) {
        render(response, status, "text/plain", text, headers);
    }

    public static void renderCacheJson(HttpServletResponse response, String string) {
        render(response, "application/json", string, new String[]{"no-cache:false"});
    }

    public static void renderHtml(HttpServletResponse response, String string, String... headers) {
        render(response, "text/html", string, headers);
    }

    public static void renderSuccessHtml(HttpServletResponse response, Object responseData, String tip, String... headers) {
        Map<String, Object> retMap = Maps.newLinkedHashMap();
        retMap.put("success", Integer.valueOf(1));
        retMap.put("tip", tip);
        retMap.put("data", responseData);
        render(response, "text/html", JSonUtil.toJson(retMap), headers);
    }

    public static void renderFailHtml(HttpServletResponse response, String strResponse, String tip, String... headers) {
        Map<String, Object> retMap = Maps.newLinkedHashMap();
        retMap.put("success", Integer.valueOf(-1));
        retMap.put("tip", tip);
        render(response, "text/html", JSonUtil.toJson(retMap), headers);
    }

    public static void renderSuccessJson(HttpServletResponse response, String tip, Object responseData) {
        Map<String, Object> retMap = Maps.newLinkedHashMap();
        retMap.put("status", Integer.valueOf(1));
        if(tip != null) {
            retMap.put("msg", tip);
        }

        if(responseData != null) {
            retMap.put("data", responseData);
        }

        render(response, "application/json", JSonUtil.toJson(retMap), new String[]{"no-cache:false"});
    }

    public static void renderCodeJson(HttpServletResponse response, Object responseData) {
        Map<String, Object> retMap = Maps.newLinkedHashMap();
        retMap.put("code", Integer.valueOf(1000));
        retMap.put("data", responseData);
        render(response, "application/json", JSonUtil.toJson(retMap), new String[]{"no-cache:false"});
    }

    public static void renderSuccessTipJson(HttpServletResponse response, String tip) {
        renderSuccessJson(response, tip, (Object)null);
    }

    public static void renderFailJson(HttpServletResponse response, ErrorCodeEnum errCode) {
        Map<String, Object> retMap = new LinkedHashMap(2);
        retMap.put("status", Integer.valueOf(-1));
        retMap.put("code", Integer.valueOf(errCode.getCode()));
        retMap.put("msg", errCode.getRemark());
        render(response, "application/json", JSonUtil.toJson(retMap), new String[]{"no-cache:false"});
    }

    public static void renderFailJson(HttpServletResponse response, String msg) {
        Map<String, Object> retMap = new LinkedHashMap(2);
        retMap.put("status", Integer.valueOf(-1));
        retMap.put("msg", msg);
        render(response, "application/json", JSonUtil.toJson(retMap), new String[]{"no-cache:false"});
    }

    public static void renderFailPageJson(HttpServletResponse response, String tip, int totalRecord, ArrayList recordList) {
        Map<String, Object> retMap = new LinkedHashMap(2);
        retMap.put("success", Boolean.valueOf(false));
        retMap.put("tip", tip);
        render(response, "application/json", JSonUtil.toJson(retMap), new String[]{"no-cache:false"});
    }

    private static void render(HttpServletResponse response, String contentType, String content, String... headers) {
        render(response, 200, contentType, content, headers);
    }

    public static void render(HttpServletResponse response, int status, String contentType, String content, String... headers) {
        try {
            String encoding = "UTF-8";
            boolean noCache = true;
            String[] var7 = headers;
            int var8 = headers.length;

            for(int var9 = 0; var9 < var8; ++var9) {
                String header = var7[var9];
                String headerName = StringUtils.substringBefore(header, ":");
                String headerValue = StringUtils.substringAfter(header, ":");
                if(StringUtils.equalsIgnoreCase(headerName, "encoding")) {
                    encoding = headerValue;
                } else {
                    if(!StringUtils.equalsIgnoreCase(headerName, "no-cache")) {
                        throw new IllegalArgumentException(headerName + "不是一个合法的header类型");
                    }

                    noCache = Boolean.parseBoolean(headerValue);
                }
            }

            String fullContentType = contentType + ";charset=" + encoding;
            response.setContentType(fullContentType);
            if(noCache) {
                response.setHeader("Pragma", "No-cache");
                response.setHeader("Cache-Control", "no-cache");
                response.setDateHeader("Expires", 0L);
            }

            response.setStatus(status);
            response.getWriter().write(content);
        } catch (IOException var13) {
            var13.printStackTrace();
        }

    }

    public static void renderJsonp(HttpServletRequest request, HttpServletResponse response, String jstr) {
        String callBack = RequestUtil.getRequestString(request, "fcallback", "fcallback");
        renderJson(response, callBack + "(" + jstr + ")", new String[0]);
    }

    public static void renderJson(HttpServletRequest request, HttpServletResponse response, String jsonStr) {
        String callBack = RequestUtil.getRequestString(request, "fcallback", (String)null);
        if(StringUtils.trimToNull(callBack) == null) {
            renderJson(response, jsonStr, new String[0]);
        } else {
            renderJson(response, callBack + "(" + jsonStr + ")", new String[0]);
        }

    }

    public static void renderJson(HttpServletResponse response, String string, String... headers) {
        render(response, "application/json", string, headers);
    }

    public static void renderUeditorJson(HttpServletResponse response, HashMap retMap) {
        render(response, "application/json", JSonUtil.toJson(retMap), new String[]{"no-cache:false"});
    }
}
