package fr.karmakat.wiflic.token;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

import static fr.karmakat.wiflic.hmacSHA1.HMAC_SHA1.calculateRFC2104HMAC;

public class TokenNegotiatior {
    private static ObjectMapper mapper;
    private static String challenge;
    private static String app_token;
    private static String session_token;

    static{
        mapper = new ObjectMapper();
        app_token = "Q1cVG9fOHMCrreCVNaO2rOLry76gGT7RjHsB1ZHvib1edGs4gP4YWDoqr1peftlq";
    }

    public static String negotiateSession() {
        try {
            TokenNegotiatior.setChallenge();
            TokenNegotiatior.setSession_token();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (SignatureException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
        return session_token;
    }
    public static void setSession_token() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, IOException, InterruptedException, URISyntaxException {
        String requestBody = """
                        { 
                            "app_id": "fr.karmakat.flic",
                            "password": "%s"
                         }
                        """;
        String formattedRequestBody = String.format(requestBody, computeHmacSHA1(app_token, challenge));
        HttpRequest session_token_request = HttpRequest.newBuilder()
                .header("Content-Type", "application/json")
                .uri(new URI("http://192.168.1.254/api/v8/login/session"))
                .POST(HttpRequest.BodyPublishers.ofString(formattedRequestBody))
                .build();
        HttpResponse<String> sessionResp = HttpClient.newHttpClient().send(session_token_request, HttpResponse.BodyHandlers.ofString());
        JsonNode sessionTree = mapper.readTree(sessionResp.body());
        session_token = sessionTree.findValue("result").findValue("session_token").textValue();
    }

    public static void setChallenge() throws InterruptedException, IOException {
        String host = "http://192.168.1.254/api/v8/login";
        HttpRequest getChallengeReq = HttpRequest.newBuilder()
                .uri(URI.create(host))
                .GET()
                .build();
        HttpResponse<String> client = HttpClient.newHttpClient().send(getChallengeReq, HttpResponse.BodyHandlers.ofString());

        JsonNode tree = mapper.readTree(client.body());
        challenge = tree.findValue("result").findValue("challenge").textValue();
    }

    private static String computeHmacSHA1(String key, String value) throws InvalidKeyException, NoSuchAlgorithmException, SignatureException {
        return calculateRFC2104HMAC(challenge, app_token);
    }

    public static String getSessionToken(){
        return session_token;
    }
    public static ObjectMapper mapper(){
        return mapper;
    }
}
