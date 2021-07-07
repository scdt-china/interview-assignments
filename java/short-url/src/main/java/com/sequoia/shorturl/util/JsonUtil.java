package com.sequoia.shorturl.util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.type.ArrayType;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

public class JsonUtil {
    public final static ObjectMapper objectMapper = new ObjectMapper();
    public final static Logger logger = LoggerFactory.getLogger(JsonUtil.class);
    public final static TypeFactory typeFactory;
    public static final Gson gson;

    static {
        objectMapper.setLocale(Locale.CHINA);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        objectMapper.setDateFormat(dateFormat);
        objectMapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);// 默认不显示无JsonView注解的字段

        objectMapper.disableDefaultTyping();// 禁用默认类型作为属性

        objectMapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);// 允许使用非双引号属性名
        objectMapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);// 允许单引号包信属性名
        objectMapper.configure(Feature.ALLOW_NUMERIC_LEADING_ZEROS, true);// 允许JSON整数以多个0开始
        objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, false);


        typeFactory = objectMapper.getTypeFactory();
        
        gson = new GsonBuilder().create();
    }

    public static String stringify(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }

    public static <T> T parse(String json, Class<T> clazz) throws JsonParseException, JsonMappingException, IOException {
        return objectMapper.readValue(json, clazz);
    }

    @SuppressWarnings("unchecked")
    public static <T> T parse(String json, JavaType javaType) throws JsonParseException, JsonMappingException,
            IOException {
        return (T) objectMapper.readValue(json, javaType);
    }

    @SuppressWarnings("unchecked")
    public static <T> T parse(String json, TypeReference<?> typeReference) throws JsonParseException,
            JsonMappingException, IOException {
        return (T) objectMapper.readValue(json, typeReference);
    }

    public static Map<String, String> parseToMap(String json) throws JsonProcessingException, IOException {
        JsonNode node = objectMapper.readTree(json);
        Iterator<Entry<String, JsonNode>> fields = node.fields();
        Map<String, String> map = new HashMap<String, String>();
        while (fields.hasNext()) {
            Entry<String, JsonNode> field = fields.next();
            String key = field.getKey();
            String value = field.getValue().asText();
            map.put(key, value);
        }
        return map;
    }

    public static List<String> parseToList(String json) throws JsonProcessingException, IOException {
        List<String> list = new ArrayList<String>();
        JsonNode node = objectMapper.readTree(json);
        Iterator<JsonNode> it = node.iterator();
        while (it.hasNext()) {
            JsonNode jn = it.next();
            list.add(jn.toString());
        }
        return list;
    }

    public static List<JsonNode> parseToJsonNodeList(String json) throws JsonProcessingException, IOException {
        List<JsonNode> list = new ArrayList<JsonNode>();
        JsonNode node = objectMapper.readTree(json);
        Iterator<JsonNode> it = node.iterator();
        while (it.hasNext()) {
            JsonNode jn = it.next();
            list.add(jn);
        }
        return list;
    }

    public static <T> T changeType(Object obj, Class<T> clazz) throws JsonParseException, JsonMappingException,
            IOException {
        return parse(stringify(obj), clazz);
    }

    public static ArrayType constructArrayType(Class<?> elementType) {
        return typeFactory.constructArrayType(elementType);
    }

    public static CollectionType constructCollectionType(
            @SuppressWarnings("rawtypes") Class<? extends Collection> collectionClass, Class<?> elementClass) {
        return typeFactory.constructCollectionType(collectionClass, elementClass);
    }

    public static MapType constructMapType(@SuppressWarnings("rawtypes") Class<? extends Map> mapClass,
            Class<?> keyClass, Class<?> valueClass) {
        return typeFactory.constructMapType(mapClass, keyClass, valueClass);
    }
    
    public static Map<String, Object> parseToMapFormGson(String json){
        return gson.fromJson(json, new TypeToken<Map<String, Object>>(){}.getType());
    }
    
    public static String parseToJsonFromGson(Map<String, Object> map){
        return gson.toJson(map);
    }

}
