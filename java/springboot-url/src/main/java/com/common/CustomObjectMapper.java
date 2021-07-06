package com.common;

import java.io.IOException;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

public class CustomObjectMapper extends ObjectMapper {

    public CustomObjectMapper() {
        SimpleModule simpleModule = new SimpleModule("HTML XSS Serializer");
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        simpleModule.addSerializer(new JsonHtmlXssSerializer(String.class));
        this.registerModule(simpleModule);
        this.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        this.setLocale(Locale.CHINA);
        this.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        // 2019-04-19 新增 在反序列化时，如果属性映射失败，或未知的属性，则忽略处理
        this.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        this.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
    }

    class JsonHtmlXssSerializer extends JsonSerializer<String> {
        public JsonHtmlXssSerializer(Class<String> string) {
            super();
        }

        public Class<String> handledType() {
            return String.class;
        }

        public void serialize(String value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
            if (value != null) {
                String encodedValue = StringEscapeUtils.escapeHtml3(value.toString());
                // 双引号不需要转换，防止移动端反转解析json时出现格式问题
                String str = StringUtils.replace(encodedValue, "&quot;", "\"").trim();
                jsonGenerator.writeString(str);
            }
        }

    }
}