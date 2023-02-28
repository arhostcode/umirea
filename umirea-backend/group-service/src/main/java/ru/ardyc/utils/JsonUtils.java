package ru.ardyc.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.ardyc.response.TypedMessageResponse;

import java.lang.reflect.Type;

public class JsonUtils {

    public static <T> T createFromJson(String json, Class<T> type) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, type);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> TypedMessageResponse<T> getResponse(String json, Class<T> t) {
        ObjectMapper mapper = new ObjectMapper();
        JavaType type = mapper.getTypeFactory().constructParametricType(TypedMessageResponse.class, t);
        try {
            return mapper.readValue(json, type);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

}
