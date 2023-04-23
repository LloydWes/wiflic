package fr.karmakat.wiflic;

import fr.karmakat.wiflic.token.TokenNegotiatior;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.MultiValueMap;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.SubProtocolWebSocketHandler;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.WebSocketClientSockJsSession;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;


@SpringBootApplication
@ComponentScan("fr.karmakat.wiflic.webService")
public class WiflicApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(WiflicApplication.class, args);
//		ApplicationContext applicationContext2 = new AnnotationConfigApplicationContext(SpringComponentScanApp.class);
//		System.out.println("####Listing beans");
//		for (String beanName : applicationContext.getBeanDefinitionNames()) {
//			System.out.println(beanName);
//		}
//		System.out.println("#############Done listing beans");
	}
}
