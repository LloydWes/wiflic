package fr.karmakat.wiflic.wsClient;

import fr.karmakat.wiflic.token.TokenNegotiatior;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.Executors.newSingleThreadScheduledExecutor;

@Service
public class SpringWebsocketWithoutStompClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpringWebsocketWithoutStompClient.class);
    private WebSocketSession webSocketSession;
    public void close() throws IOException {
        this.webSocketSession.close(CloseStatus.NORMAL.withReason("Client asked to close the connection"));
    }
    @PostConstruct
    public void connect() {
        try {
            TokenNegotiatior.negotiateSession();
            String session_token = TokenNegotiatior.getSessionToken();
            System.out.println(session_token);
            WebSocketHttpHeaders webSocketHttpHeaders = new WebSocketHttpHeaders();
            webSocketHttpHeaders.add("X-Fbx-App-Auth", session_token);
            webSocketHttpHeaders.add("Sec-WebSocket-Extensions", "permessage-deflate; client_max_window_bits");
            webSocketHttpHeaders.add("Sec-WebSocket-Version", "13");
//            webSocketHttpHeaders.add("Connection", "Upgrade");
//            webSocketHttpHeaders.add("Upgrade", "websocket");
            webSocketHttpHeaders.add("Origin", "http://mafreebox.freebox.fr");
            webSocketHttpHeaders.add("Host", "mafreebox.freebox.fr");
            webSocketHttpHeaders.add("Pragma", "no-cache");
            WebSocketClient webSocketClient = new StandardWebSocketClient();
            this.webSocketSession = webSocketClient.doHandshake(new TextWebSocketHandler() {
                @Override
                public void handleTextMessage(WebSocketSession session, TextMessage message) {
                    LOGGER.info("received message - " + message.getPayload());
                }

                @Override
                public void afterConnectionEstablished(WebSocketSession session) {
                    LOGGER.info("established connection - " + session);
                }

                @Override
                public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
                    LOGGER.info("Connection closed with status " + status);
                }
            }, webSocketHttpHeaders, URI.create("ws://mafreebox.freebox.fr/api/latest/ws/event/")).get();
            TextMessage message = new TextMessage("""
            {\"action\":\"register\", \"events\":[\"lan_host_l3addr_reachable\",\"lan_host_l3addr_unreachable\"]}""");
            webSocketSession.sendMessage(message);

//            newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
//                try {
//                    TextMessage message = new TextMessage("Hello !!");
//                    webSocketSession.sendMessage(message);
//                    LOGGER.info("sent message - " + message.getPayload());
//                } catch (Exception e) {
//                    LOGGER.error("Exception while sending a message", e);
//                }
//            }, 1, 10, TimeUnit.SECONDS);
        } catch (Exception e) {
            LOGGER.error("Exception while accessing websockets", e);
        }
    }
}