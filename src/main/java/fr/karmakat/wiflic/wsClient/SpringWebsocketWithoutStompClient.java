package fr.karmakat.wiflic.wsClient;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.karmakat.wiflic.domain.DeviceEvent;
import fr.karmakat.wiflic.token.TokenNegotiatior;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.apache.juli.logging.Log;
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
import java.util.stream.Stream;

import static java.util.concurrent.Executors.newSingleThreadScheduledExecutor;

@Service
public class SpringWebsocketWithoutStompClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpringWebsocketWithoutStompClient.class);
    private WebSocketSession webSocketSession;
    public void close() throws IOException {
        this.webSocketSession.close(CloseStatus.NORMAL.withReason("Client asked to close the connection"));
    }
    @PreDestroy
    public void destroy() throws IOException {
        if (this.webSocketSession.isOpen()) {
            this.webSocketSession.close(CloseStatus.NORMAL.withReason("Spring application is shuting down"));
        }
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
            webSocketHttpHeaders.add("Origin", "http://mafreebox.freebox.fr");
            webSocketHttpHeaders.add("Host", "mafreebox.freebox.fr");
            webSocketHttpHeaders.add("Pragma", "no-cache");
            WebSocketClient webSocketClient = new StandardWebSocketClient();
            this.webSocketSession = webSocketClient.doHandshake(new TextWebSocketHandler() {
                @Override
                public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
                    LOGGER.info("received message - " + message.getPayload());
                    final ObjectMapper mapper = new ObjectMapper();
                    final JsonNode fNode = mapper.readTree(message.getPayload());
                    final JsonParser parser = mapper.treeAsTokens(fNode);
                    DeviceEvent device = mapper.readValue(parser, DeviceEvent.class);
                    if (fNode.has("event")) {
                        if (fNode.get("event").textValue().equals("host_l3addr_reachable")) {
                            LOGGER.info("### Reachable");
                        } else if (fNode.get("event").textValue().equals("host_l3addr_unreachable")) {
                            LOGGER.info("### Unreachable");
                        } else {
                            LOGGER.info("oups");
                            LOGGER.info(fNode.get("event").toString());
                        }
                    } else {
                        LOGGER.info("### Couldn't find event");
                    }
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

// https://www.baeldung.com/executable-jar-with-maven

// https://www.baeldung.com/spring-boot-shutdown