package fr.karmakat.wiflic;

import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;

public class MyStompSessionHandler extends StompSessionHandlerAdapter {
    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        System.out.println("connected");
        session.subscribe("/api/latest/ws/event/", this);
        session.send("/api/latest/ws/event/", new RegisterAction());
    }
    @Override
    public Type getPayloadType(StompHeaders headers) {
        System.out.println("getPayloadtype");
        return RegisterAction.class;
    }
    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        System.out.println("handleException");
        System.out.println(headers);
    }
    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        System.out.println("handleFrame");
        System.out.println(headers);
    }


}
