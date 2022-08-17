package fr.karmakat.wiflic.task;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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

public class StationFetcherTask implements Runnable{

    @Autowired
    MongoTemplate mongoTemplate;

    private String apUrl = "http://192.168.1.254/api/v8/wifi/ap/";

    private String station = "stations";

    private ObjectMapper mapper = new ObjectMapper();;

    private List<Integer> apIds = new ArrayList<>();

    public StationFetcherTask() {
        apIds.add(0);
        apIds.add(1);
        apIds.add(3);
    }

    @Override
    public void run() {
        System.out.println("Station fetcher entry");
        try {
            TokenNegotiatior.negotiateSession();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        String session_token = TokenNegotiatior.getSessionToken();
        HttpResponse<String> stationsListResponse;
        List<JsonNode> stationsList = new ArrayList<>();
        List<Integer> signalsList = new ArrayList<>();
        try {
             for(Integer currentId : apIds) {
                HttpRequest stationsRequest = null;
                String stationsUrl = this.apUrl + currentId + "/" + this.station;
                 System.out.println(stationsUrl);
                try {
                    stationsRequest = HttpRequest.newBuilder()
                            .uri(new URI(stationsUrl))
                            .header("X-Fbx-App-Auth", session_token)
                            .GET()
                            .build();
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
                stationsListResponse = HttpClient.newHttpClient().send(stationsRequest, HttpResponse.BodyHandlers.ofString());
                JsonNode stationsResultJson;
                try {
                    stationsResultJson = this.mapper.readTree(stationsListResponse.body());
                    if(stationsResultJson.has("result")){
                        JsonNode stationsListJson = stationsResultJson.findValue("result");
                        stationsListJson.forEach(  (station) -> {
                            stationsList.add(station);
                            System.out.println(station.findValue("signal"));
                        });

                    }
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        stationsList.forEach(System.out::println);


    }
}
