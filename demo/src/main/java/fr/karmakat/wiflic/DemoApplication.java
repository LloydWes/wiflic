package fr.karmakat.wiflic;

import com.mongodb.client.MongoClient;
import fr.karmakat.wiflic.model.Result;
import fr.karmakat.wiflic.repository.ResultRepository;
import fr.karmakat.wiflic.token.TokenNegotiatior;
import fr.karmakat.wiflic.model.WifiDevice;
import fr.karmakat.wiflic.repository.WifiDeviceRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.Callable;

//@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	@Autowired
	MongoTemplate mongoTemplate;

	@Autowired
	MongoClient mongoClient;

	@Autowired
	WifiDeviceRepository wifiDeviceRepository;

	@Autowired
	ResultRepository resultRepository;

	private final Logger logger = LoggerFactory.getLogger(DemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}



	@Override
	public void run(String... args) throws Exception {

		TokenNegotiatior.negotiateSession();
		String session_token = TokenNegotiatior.getSessionToken();
		System.out.println(session_token);
		HttpRequest wifiReq = HttpRequest.newBuilder()
				.uri(new URI("http://192.168.1.254/api/v8/wifi/ap/1/stations"))
				.header("X-Fbx-App-Auth", session_token)
				.GET()
				.build();
		HttpResponse<String> wifiResp = HttpClient.newHttpClient().send(wifiReq, HttpResponse.BodyHandlers.ofString());
		JsonNode wifiJson = TokenNegotiatior.mapper().readTree(wifiResp.body());

		MongoCollection<Document> collection = mongoTemplate.getCollection("test");
//		collection.insertOne();
		mongoTemplate.insert(Document.parse(wifiResp.body()), "test");
		JsonNode node = TokenNegotiatior.mapper().readTree(wifiResp.body());
//		String resultNode = node.findValue("result").get(0).toString();
//		collection.insertOne(Document.parse(resultNode));

		System.out.println("fetching in collection");
		collection.find().forEach(System.out::println);
		System.out.println("end");
//		collection.find().forEach( (k) -> {
//			System.out.println(k.getClass());
//			boolean b = ((Document)k).getBoolean("success");
////			System.out.println(( ((Document) k).get("result")).((Document).getLong("rx_bytes")));
//			System.out.println(((Document) k).get("result"));
//		});

//		MongoCursor<Document> it = collection.find().iterator();
//		while(it.hasNext()){
//			Document next = it.next();
//			System.out.println(next.get("result"));
//		}
		Callable<String> f = () -> {
			return APFetcher.stuff();
		};
		System.out.println("fetching and printing");
//		WifiDevice dev = doc
		Document doc = collection.find().first();
		List<WifiDevice> devices = wifiDeviceRepository.findAll();
		List<Result> results = resultRepository.findAll();
		System.out.println(devices.size());
		System.out.println(results.size());
		results.forEach( (k) -> {
			System.out.println("[+] " + k.getClass());
			System.out.println("[+] " + k.getWifiDevices().get(0).getAccess_point());
			System.out.println("[+] " + k.getWifiDevices().get(0).getBssid());
			System.out.println("[+] " + k.getWifiDevices().get(0).getTXBytes());
		});
		System.out.println("done printing");
//		collection.find().forEach(System.out::println);

	}
}

/*
	FLOW
	une classe lance les tâches via ScheduledExecutorService, reçoit des futures et gère les modifications des données et stock en BDD
	elle capture les exceptions Code403Exception et gère la négociation du token de session si besoin
		ajout date de négociation du nouveau token et stockage du token actuel en mémoire

	maj des bss avec updateAPList en BDD avec Date throw code403exception
	maj des id des bss en mémoire

	maj des stations pour chaque bss (id) en BDD avec Date throw code403exception



*/










//	https://stackoverflow.com/questions/29656128/how-can-a-store-raw-json-in-mongo-using-spring-boot
//	https://stackoverflow.com/questions/30350525/spring-mongo-convert-to-document-from-json-string
