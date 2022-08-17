package fr.karmakat.wiflic.repository;

import fr.karmakat.wiflic.model.WifiDevice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WifiDeviceRepository extends MongoRepository<WifiDevice, String> {

}
