package org.inin.mycinema.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class Utility {
    public Map<String, String> jsonStrToMap(String jsonStr) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readValue(jsonStr, Map.class);
    }
}
