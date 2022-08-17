package fr.karmakat.wiflic.task;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoCollection;
import fr.karmakat.wiflic.token.TokenNegotiatior;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class ApIdFetchTask implements Runnable{

    @Autowired
    MongoTemplate mongoTemplate;

    private String apListUrl = "http://192.168.1.254/api/v8/wifi/ap/";

    private ObjectMapper mapper = new ObjectMapper();;

    @Override
    public void run() {
        try {
            TokenNegotiatior.negotiateSession();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        String session_token = TokenNegotiatior.getSessionToken();
        HttpRequest apListRequest = null;
        try {
            apListRequest = HttpRequest.newBuilder()
                    .uri(new URI(apListUrl))
                    .header("X-Fbx-App-Auth", session_token)
                    .GET()
                    .build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        HttpResponse<String> apListResponse;
        try {
            apListResponse = HttpClient.newHttpClient().send(apListRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        JsonNode apResultJson;
        try {
            apResultJson = this.mapper.readTree(apListResponse.body());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        JsonNode apList =  apResultJson.findValue("result");
        List<Integer> apIds = new ArrayList<>();
        apList.forEach( (ap) -> apIds.add(Integer.parseInt(ap.findValue("id").toString())));
        apIds.forEach(System.out::println);
    }
}
