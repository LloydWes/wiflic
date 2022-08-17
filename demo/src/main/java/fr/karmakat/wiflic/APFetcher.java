package fr.karmakat.wiflic;

import fr.karmakat.wiflic.token.TokenNegotiatior;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class APFetcher {
    private List<Integer> APList;

    public void updateAPList(String session_token) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest wifiReq = HttpRequest.newBuilder()
                .uri(new URI("http://192.168.1.254/api/v8/wifi/bss"))
                .header("X-Fbx-App-Auth", session_token)
                .GET()
                .build();
        HttpResponse<String> wifiResp = HttpClient.newHttpClient().send(wifiReq, HttpResponse.BodyHandlers.ofString());
        JsonNode resultNode = TokenNegotiatior.mapper().readTree(wifiResp.body()).findValue("result");
        APList = new ArrayList<>();
        for(JsonNode bssNode : resultNode){
            ((ObjectNode) bssNode).remove("config");
            ((ObjectNode) bssNode).remove("bss_params");
            ((ObjectNode) bssNode).remove("shared_bss_params");
            APList.add(Integer.parseInt(String.valueOf(bssNode.findValue("phy_id"))));
        }

    }
    public static String stuff(){
        return "d";
    }
    public List<Integer> getAPList(){
        return APList;
    }
}


/*
PROCESS
maj des bss toutes les 10sec
requete sur updateAPList qui retourne la liste des BSS moins config, bss_params et shared_bss_params
*/


//STRUCTURE
/*{
    date: "",
    bss:[]
  }
*/

