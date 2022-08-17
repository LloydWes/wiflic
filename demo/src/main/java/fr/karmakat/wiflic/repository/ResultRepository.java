package fr.karmakat.wiflic.repository;

import fr.karmakat.wiflic.model.Result;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ResultRepository extends MongoRepository<Result, String> {
}
