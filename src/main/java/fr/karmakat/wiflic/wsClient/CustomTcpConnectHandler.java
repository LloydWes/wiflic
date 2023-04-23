package fr.karmakat.wiflic.wsClient;

import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.tcp.TcpConnectionHandler;

public interface CustomTcpConnectHandler<P> extends TcpConnectionHandler<P> {
    String getSessionId();

    String getConnectHeaders();
}
