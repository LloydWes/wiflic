package fr.karmakat.wiflic;

import com.fasterxml.jackson.databind.JsonNode;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import fr.karmakat.wiflic.model.Result;
import fr.karmakat.wiflic.model.WifiDevice;
import fr.karmakat.wiflic.repository.ResultRepository;
import fr.karmakat.wiflic.repository.WifiDeviceRepository;
import fr.karmakat.wiflic.task.ApIdFetchTask;
import fr.karmakat.wiflic.task.StationFetcherTask;
import fr.karmakat.wiflic.token.TokenNegotiatior;
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

@SpringBootApplication
public class ApListDemo implements CommandLineRunner {

	@Autowired
	MongoTemplate mongoTemplate;

	@Autowired
	MongoClient mongoClient;

	@Autowired
	WifiDeviceRepository wifiDeviceRepository;

	@Autowired
	ResultRepository resultRepository;

	private final Logger logger = LoggerFactory.getLogger(ApListDemo.class);

	public static void main(String[] args) {
		System.out.println("###########################");
		System.out.println("###########################");
		System.out.println("###########################");
		SpringApplication.run(ApListDemo.class, args);
		System.out.println("###########################");
		System.out.println("###########################");
		System.out.println("###########################");
	}



	@Override
	public void run(String... args) throws Exception {
		StationFetcherTask task = new StationFetcherTask();
//		ApIdFetchTask task = new ApIdFetchTask();
		task.run();
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
