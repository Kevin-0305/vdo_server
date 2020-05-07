package com.daily.english.app.util;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonFilter;
import org.codehaus.jackson.map.ser.impl.SimpleBeanPropertyFilter;
import org.codehaus.jackson.map.ser.impl.SimpleFilterProvider;
import org.codehaus.jackson.type.JavaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * class_name: JsonUtil
 * package: com.daily.english.app.util
 * describe: json转换工具
 * creat_user: liuzd@chinadailyhk.com
 * creat_date: 2019-08-13
 * creat_time: 11:47
 **/
public class JsonUtil {

    private static Logger log = LoggerFactory.getLogger(JsonUtil.class);

    private static ObjectMapper objectMapper = null;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        objectMapper.disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.setFilters(new SimpleFilterProvider().setFailOnUnknownId(false));
    }

    /*
    public static JsonUtil getInstance() {

        if (instance == null) {
            synchronized (JsonUtil.class) {
                if (instance == null) {
                    instance = new JsonUtil();
                }
            }
        }

        return instance;
    }
    */

    public static String stringify(Object object) {

        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return null;
    }

    public static String stringify(Object object, String... properties) {

        try {
            return objectMapper
                    .writer(new SimpleFilterProvider().addFilter(
                            AnnotationUtils.getValue(
                                    AnnotationUtils.findAnnotation(object.getClass(), JsonFilter.class)).toString(),
                            SimpleBeanPropertyFilter.filterOutAllExcept(properties)))
                    .writeValueAsString(object);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return null;
    }

    public static void stringify(OutputStream out, Object object) {

        try {
            objectMapper.writeValue(out, object);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public static void stringify(OutputStream out, Object object, String... properties) {

        try {
            objectMapper
                    .writer(new SimpleFilterProvider().addFilter(
                            AnnotationUtils.getValue(
                                    AnnotationUtils.findAnnotation(object.getClass(), JsonFilter.class)).toString(),
                            SimpleBeanPropertyFilter.filterOutAllExcept(properties)))
                    .writeValue(out, object);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public static <T> T parse(String json, Class<T> clazz) {

        if (json == null || json.length() == 0) {
            return null;
        }

        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return null;
    }

    /**
     * @class_name 生成JSON数据
     * @param 
     * @describe: TODO
     * @creat_user: liuzd@chinadailyhk.com
     * @creat_date: 2019-08-13
     * @creat_time: 11:46
     **/
    public static <T> List<T> parseList(String json, Class<T> clazz) {
        JavaType javaType = getCollectionType(ArrayList.class, clazz);
        List<T> lst = null;
        try {
            lst = (List<T>) objectMapper.readValue(json, javaType);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return lst;
    }


    /**
     * 获取泛型的Collection Type
     *
     * @param collectionClass 泛型的Collection
     * @param elementClasses  元素类
     * @return JavaType Java类型
     */
    public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }
}
