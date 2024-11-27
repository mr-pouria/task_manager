package ir.tasktop.taskTop.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ResponseHandler {


    public ResponseEntity<?> responseBack(Object Data , String msg , String errMsg , HttpStatus code) {
        Map<String, Object> map = new HashMap<>();
        map.put("data", Data);
        map.put("msg", msg);
        map.put("errMsg", errMsg);
        return new ResponseEntity<>(map , code);
    }
}
