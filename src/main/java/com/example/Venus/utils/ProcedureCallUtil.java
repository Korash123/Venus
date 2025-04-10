package com.example.Venus.utils;

import com.example.Venus.contants.APP_CONSTANTS;
import com.example.Venus.exception.ProcResponseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author korash waiba
 * @version 1.0
 * @since 2024-06-24, Monday
 **/

@RequiredArgsConstructor
@Component

public class ProcedureCallUtil {
    private final ObjectMapper objectMapper;

    public Map<String,Object> callProc(String procName, NamedParameterJdbcTemplate namedParameterJdbcTemplate, Object... objects){
        StringBuilder queryBuilder = new StringBuilder(APP_CONSTANTS.SELECT_QUERY);
        queryBuilder.append(procName).append(APP_CONSTANTS.START_SMALL_BRACKET);
        for (int i = 0; i < objects.length; i++) {
            if(objects[i] instanceof String) queryBuilder
                    .append(APP_CONSTANTS.SINGLE_QUOTE)
                    .append(objects[i])
                    .append(APP_CONSTANTS.SINGLE_QUOTE);
            else queryBuilder.append(objects[i]);
            if (i < objects.length - 1) queryBuilder.append(APP_CONSTANTS.COMMA);
        }
        queryBuilder.append(APP_CONSTANTS.CLOSE_SMALL_BRACKET);
        return namedParameterJdbcTemplate.queryForMap(queryBuilder.toString(), new MapSqlParameterSource());
    }

//    public List<Map<String,Object>> callProcForList(String procName, NamedParameterJdbcTemplate namedParameterJdbcTemplate, Object... objects){
//        StringBuilder queryBuilder = new StringBuilder(APP_CONSTANTS.SELECT_QUERY);
//        queryBuilder.append(procName).append(APP_CONSTANTS.START_SMALL_BRACKET);
//        for (int i = 0; i < objects.length; i++) {
//            if(objects[i] instanceof String) queryBuilder
//                    .append(APP_CONSTANTS.SINGLE_QUOTE)
//                    .append(objects[i])
//                    .append(APP_CONSTANTS.SINGLE_QUOTE);
//            else queryBuilder.append(objects[i]);
//            if (i < objects.length - 1) queryBuilder.append(APP_CONSTANTS.COMMA);
//        }
//        queryBuilder.append(APP_CONSTANTS.CLOSE_SMALL_BRACKET);
//        return namedParameterJdbcTemplate.queryForList(queryBuilder.toString(), new HashMap<>());
//    }

    public void validateProcResponse(Map<String, Object> procResponse){
        if( !((int)procResponse.get("response_code")==200)) {
            throw new ProcResponseException(String.valueOf(procResponse.get("response_message")));
        }
    }

    public Object transformDtoToJson(Object data) throws JsonProcessingException {
        return objectMapper.writeValueAsString(data);
    }

    public Object transformDbResponseToJson(Object data) throws JsonProcessingException {
        return objectMapper.readValue(data.toString(), Map.class);
    }

    public Map<String, Object> transformDbResponseToMap(Object data) throws JsonProcessingException {
        return objectMapper.readValue(data.toString(), Map.class);
    }

    public List<Object> transformDbResponseToJsonList(Object data) throws JsonProcessingException {
        return objectMapper.readValue(data.toString(), List.class);
    }

}