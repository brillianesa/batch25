package com.example.batch25.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CustomResponse {
    // GET -> status code, message, data

    public static ResponseEntity<Object> generate(HttpStatus httpStatus, String message, Object data){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("statusCode", httpStatus);
        map.put("message", message);
        map.put("results", data);
        return new ResponseEntity<Object>(map, httpStatus);
    }
    public static ResponseEntity<Object> generate(HttpStatus httpStatus, String message){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("statusCode", httpStatus);
        map.put("message", message);
        return new ResponseEntity<Object>(map, httpStatus);
    }
}
