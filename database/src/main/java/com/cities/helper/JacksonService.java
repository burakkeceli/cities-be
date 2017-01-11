package com.cities.helper;

import com.cities.exception.JsonException;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JacksonService {

    public String toJson(Object object) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new JsonException(e);
        }
    }

    public <T> T fromJson(String json, Class<T> cls) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, cls);
        } catch (IOException e) {
            throw new JsonException(e);
        }
    }

    public <T> T fromJson(String json, TypeReference<T> type) {
        if (json == null) {
            return null;
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, type);
        } catch (IOException e) {
            throw new JsonException(e);
        }
    }

    public JsonNode toJsonNode(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readTree(json);
        } catch (IOException e) {
            throw new JsonException(e);
        }
    }
}
