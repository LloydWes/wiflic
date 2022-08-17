package fr.karmakat.wiflic;

import fr.karmakat.wiflic.token.TokenNegotiatior;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class DeviceFetcher {

    private JsonNode wifiJson;

    public void fetchDevices(String session_token, List<Integer> APList) throws URISyntaxException, IOException, InterruptedException {
        for(int i : APList){
            String request = "http://192.168.1.254/api/v8/wifi/ap/" + i + "/stations";
        HttpRequest wifiReq = HttpRequest.newBuilder()
                .uri(new URI(request))
                .header("X-Fbx-App-Auth", session_token)
                .GET()
                .build();
        HttpResponse<String> wifiResp = HttpClient.newHttpClient().send(wifiReq, HttpResponse.BodyHandlers.ofString());
        JsonNode wifiJson = TokenNegotiatior.mapper().readTree(wifiResp.body());
        }
    }

}


