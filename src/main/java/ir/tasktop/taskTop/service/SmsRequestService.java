package ir.tasktop.taskTop.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SmsRequestService {
 protected WebClient webClient;
 private final static String apiKey = "W84DYlMSb1tUVVpqstlzGMXbUpn3PdNcRaxNKMgOTvY0WdDf";
 private final static String baseUrl = "https://api.sms.ir/v1";
 SmsRequestService() {
     this.webClient = WebClient.builder().baseUrl(baseUrl).build();
 }


 public Mono<String> postRequest(String phone , String code) {

     Map<Object , Object> smsBody = new HashMap<>();
     Map<Object , Object> param = new HashMap<>();
     List<Map<Object , Object>> params = new ArrayList<>();
     param.put("name", "Code");
     param.put("Value", code);
     params.add(param);
     smsBody.put("mobile", phone);
     smsBody.put("templateId" , "100000");
     smsBody.put("parameters" , params);
     return  webClient.post()
             .uri(baseUrl + "/send/verify")
             .contentType(MediaType.APPLICATION_JSON)
             .accept(MediaType.APPLICATION_JSON)
             .header("x-api-key" , apiKey)
             .bodyValue(smsBody)
             .retrieve()
             .bodyToMono(String.class);

 }

}
