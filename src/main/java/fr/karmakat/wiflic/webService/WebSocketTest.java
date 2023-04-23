package fr.karmakat.wiflic.webService;

import fr.karmakat.wiflic.MyStompSessionHandler;
import fr.karmakat.wiflic.token.TokenNegotiatior;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.SubProtocolWebSocketHandler;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/")
public class WebSocketTest {
    @GetMapping("websockettest")
    public void lancerWebSocketConfig() throws URISyntaxException {
        System.out.println("##################################");
        System.out.println("##################################");
        System.out.println("##################################");
        System.out.println("##################################");
        System.out.println("##################################");
        System.out.println("##################################");
        TokenNegotiatior.negotiateSession();
        String session_token = TokenNegotiatior.getSessionToken();
        System.out.println(session_token);


        WebSocketClient client = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        StompSessionHandler sessionHandler = new MyStompSessionHandler();
        WebSocketHttpHeaders webSocketHttpHeaders = new WebSocketHttpHeaders();
        webSocketHttpHeaders.add("X-Fbx-App-Auth", session_token);
        webSocketHttpHeaders.add("Sec-WebSocket-Extensions", "permessage-deflate; client_max_window_bits");
        webSocketHttpHeaders.add("Sec-WebSocket-Version", "13");
        webSocketHttpHeaders.add("Connection", "Upgrade");
        webSocketHttpHeaders.add("Upgrade", "websocket");
        webSocketHttpHeaders.add("Origin", "http://mafreebox.freebox.fr");
        webSocketHttpHeaders.add("Host", "mafreebox.freebox.fr");
        webSocketHttpHeaders.add("Pragma", "no-cache");

        StompHeaders stompHeaders = new StompHeaders();
        System.out.println("is Empty ?" + stompHeaders.isEmpty());

        stompHeaders.add("", """
            {\"action\":\"register\", \"events\":[\"lan_host_l3addr_reachable\"]}""");
        stompClient.start();
//		ListenableFuture<StompSession> future = stompClient.connect("ws://mafreebox.freebox.fr/api/latest/ws/event/", webSocketHttpHeaders, sessionHandler);
        CompletableFuture<StompSession> future = stompClient.connectAsync(new URI("ws://mafreebox.freebox.fr/api/latest/ws/event/"), webSocketHttpHeaders, stompHeaders, sessionHandler);
//		CompletableFuture<StompSession> future = stompClient.connectAsync("ws://mafreebox.freebox.fr/api/latest/ws/event/", webSocketHttpHeaders, sessionHandler, new Object());

        System.out.println("is Empty ?" + stompHeaders.isEmpty());
        System.out.println("isAuto " + stompClient.isAutoStartup());
        System.out.println(future);
        System.out.println("isRunning " + stompClient.isRunning());

        System.out.println(future);
    }
}
