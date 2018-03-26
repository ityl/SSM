

package com.ssm.boot.util;

import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonGenerator.Feature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.beans.BeanInfo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.WeakHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JSonUtil {
    private static final Logger logger = LoggerFactory.getLogger(JSonUtil.class);
    private static ThreadLocal<StringBuilder> local = new ThreadLocal();
    private static Map<Class, BeanInfo> beanInfoMap = Collections.synchronizedMap(new WeakHashMap());
    private static final ObjectMapper mapper = new ObjectMapper();

    public JSonUtil() {
    }

    public static final String toJson(Map<String, String> map) {
        try {
            return mapper.writeValueAsString(map);
        } catch (IOException var2) {
            logger.error(var2.getMessage(), var2);
            throw new RuntimeException(var2);
        }
    }

    public static final String toJson(Object ob) {
        try {
            return mapper.writeValueAsString(ob);
        } catch (IOException var2) {
            logger.error(var2.getMessage(), var2);
            throw new RuntimeException(var2);
        }
    }

    public static final String toJsonWithObject(Map<String, Object> map) {
        try {
            return mapper.writeValueAsString(map);
        } catch (IOException var2) {
            logger.error(var2.getMessage(), var2);
            throw new RuntimeException(var2);
        }
    }

    public static final Map<String, Object> toMap(String json) {
        try {
            return (Map)mapper.readValue(json, HashMap.class);
        } catch (IOException var2) {
            logger.error(var2.getMessage(), var2);
            throw new RuntimeException(var2);
        }
    }

    public static final ArrayList<Object> toList(String json) {
        try {
            return (ArrayList)mapper.readValue(json, ArrayList.class);
        } catch (IOException var2) {
            logger.error(var2.getMessage(), var2);
            throw new RuntimeException(var2);
        }
    }

    public static final Object toObject(String json) {
        try {
            return mapper.readValue(json, Object.class);
        } catch (IOException var2) {
            logger.error(var2.getMessage(), var2);
            throw new RuntimeException(var2);
        }
    }

    public static final <T> T toObject(String json, TypeReference<T> type) {
        try {
            return mapper.readValue(json, type);
        } catch (IOException var3) {
            logger.error(var3.getMessage(), var3);
            throw new RuntimeException(var3);
        }
    }

    public static final <T> T convertObject(Object bean, TypeReference<T> type) {
        try {
            return mapper.convertValue(bean, type);
        } catch (Exception var3) {
            logger.error(var3.getMessage(), var3);
            throw new RuntimeException(var3);
        }
    }

    public static final <T> List<T> toObjectList(String json, TypeReference<? extends Collection<T>> type) {
        try {
            return (List)mapper.readValue(json, type);
        } catch (IOException var3) {
            logger.error(var3.getMessage(), var3);
            throw new RuntimeException(var3);
        }
    }

    public static final <K, V> Map<K, V> toObjectMap(String json, TypeReference<? extends Map<K, V>> type) {
        try {
            return (Map)mapper.readValue(json, type);
        } catch (IOException var3) {
            logger.error(var3.getMessage(), var3);
            throw new RuntimeException(var3);
        }
    }

    public static final <T> T toObject(String json, Class<T> tclass) {
        try {
            return mapper.readValue(json, tclass);
        } catch (IOException var3) {
            logger.error(var3.getMessage(), var3);
            throw new RuntimeException(var3);
        }
    }

    public static final String toJson(List<String> list) {
        try {
            return mapper.writeValueAsString(list);
        } catch (IOException var2) {
            logger.error(var2.getMessage(), var2);
            throw new RuntimeException(var2);
        }
    }

    public static void main(String[] args) {
        System.out.println(toMap("{\"北京\":\"奥亚会所体检中心,北京公主坟门诊部,北京慈铭联想桥门诊部\",\"上海\":\"1,2\"}"));
    }

    static {
        mapper.configure(Feature.QUOTE_NON_NUMERIC_NUMBERS, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
    }
}
