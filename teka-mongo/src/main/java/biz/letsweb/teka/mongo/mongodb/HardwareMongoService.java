package biz.letsweb.teka.mongo.mongodb;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface HardwareMongoService extends MongoRepository<Hardware, String> {

	List<Hardware> findByManufacturer(String manufacturer);

	List<Hardware> findByModel(String model);
        
        List<Hardware> findByFirmware(String firmware);
        
        void deleteById(String id);
        
}
