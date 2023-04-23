package fr.karmakat.wiflic.webService;

import fr.karmakat.wiflic.token.TokenNegotiatior;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ExecutorSubscribableChannel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.*;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.messaging.SubProtocolHandler;
import org.springframework.web.socket.messaging.SubProtocolWebSocketHandler;

import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class SubProtocolWebSocket {

    @GetMapping("wssub")
    public void lancerWebSocketConfig() {
        System.out.println("##################################");
        System.out.println("##################################");
        System.out.println("##################################");
        System.out.println("##################################");
        System.out.println("##################################");
        System.out.println("##################################");
        TokenNegotiatior.negotiateSession();
        String session_token = TokenNegotiatior.getSessionToken();
        System.out.println(session_token);


        WebSocketHandler f = new SubProtocolWebSocketHandler();
    }


    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(this.webSocketHandler(), new String[]{"/endpointUrl"});
    }

    @Bean
    public WebSocketHandler webSocketHandler() {
        ExecutorSubscribableChannel clientInboundChannel = new ExecutorSubscribableChannel();
        ExecutorSubscribableChannel clientOutboundChannel = new ExecutorSubscribableChannel();
        SubProtocolWebSocketHandler subProtocolWebSocketHandler = new SubProtocolWebSocketHandler(clientInboundChannel, clientOutboundChannel);
        subProtocolWebSocketHandler.addProtocolHandler(new SubProtocolHandler() {
            public List<String> getSupportedProtocols() {
                return Collections.singletonList("custom-protocol");
            }

            public void handleMessageFromClient(WebSocketSession session, WebSocketMessage<?> message, MessageChannel outputChannel) throws Exception {
                session.sendMessage(new TextMessage("some message"));
            }

            public void handleMessageToClient(WebSocketSession session, Message<?> message) throws Exception {
            }

            public String resolveSessionId(Message<?> message) {
                return UUID.randomUUID().toString();
            }

            public void afterSessionStarted(WebSocketSession session, MessageChannel outputChannel) throws Exception {
                System.out.println("SESSION STARTED");
            }

            public void afterSessionEnded(WebSocketSession session, CloseStatus closeStatus, MessageChannel outputChannel) throws Exception {
                session.close();
                System.out.println("SESSION ENDED");
            }
        });
        return subProtocolWebSocketHandler;
    }
}

// https://stackoverflow.com/questions/51528992/how-to-implement-a-custom-websocket-subprotocol-with-spring