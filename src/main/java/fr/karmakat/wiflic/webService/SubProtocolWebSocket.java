package fr.karmakat.wiflic.webService;

import fr.karmakat.wiflic.wsClient.SpringWebsocketWithoutStompClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@RestController
@RequestMapping("/")
public class SubProtocolWebSocket {

    @Autowired
    private SpringWebsocketWithoutStompClient springWebsocketWithoutStompClient;
    @GetMapping("closeWebSocket")
    public void lancerWebSocketConfig() throws IOException {
        springWebsocketWithoutStompClient.close();
    }

}

// https://stackoverflow.com/questions/51528992/how-to-implement-a-custom-websocket-subprotocol-with-spring
// https://medium.com/swlh/websockets-with-spring-part-2-websocket-with-sockjs-fallback-1903cf8fe480

// https://idodevjobs.wordpress.com/2019/02/07/spring-websocket-client-without-stomp-example/