package edu.miu.paymentservice.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestUtils {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static String stringify(Object object) {
        String json = "";
        try {
            json = OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("Error to parse object to json string: ", e);
        }

        return json;
    }
}
