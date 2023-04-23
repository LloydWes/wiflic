package fr.karmakat.wiflic.wsClient;

import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.tcp.TcpConnection;

import java.util.concurrent.CompletableFuture;

public class CustomConnectionHandling implements  CustomTcpConnectHandler{

    public CompletableFuture<StompSession> getSession() {
        return null;
    }

    @Override
    public String getSessionId() {
        return null;
    }

    @Override
    public String getConnectHeaders() {
        return """
            {\"action\":\"register\", \"events\":[\"lan_host_l3addr_reachable\"]}""";
    }

    @Override
    public void afterConnected(TcpConnection connection) {

    }

    @Override
    public void afterConnectFailure(Throwable ex) {

    }

    @Override
    public void handleMessage(Message message) {

    }

    @Override
    public void handleFailure(Throwable ex) {

    }

    @Override
    public void afterConnectionClosed() {

    }
}
