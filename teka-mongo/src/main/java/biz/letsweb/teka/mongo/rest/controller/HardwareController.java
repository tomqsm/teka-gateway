package biz.letsweb.teka.mongo.rest.controller;

import biz.letsweb.teka.mongo.mongodb.Hardware;
import biz.letsweb.teka.mongo.mongodb.HardwareMongoService;
import static biz.letsweb.teka.mongo.rest.controller.HardwareController.PATH;
import biz.letsweb.teka.mongo.rest.error.ContentNotFoundException;
import biz.letsweb.teka.mongo.rest.error.ErrorMessage;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author tomasz
 */
@Api(value = "content-repository", description = "Provides access to the Hardware repository.")
@RestController
@RequestMapping(PATH)
public class HardwareController {

    public static final Logger LOG = LoggerFactory.getLogger(HardwareController.class);
    public static final String PATH = "/hardware";

    @Autowired
    private HardwareMongoService hardwareMongoService;

    @RequestMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    @ApiOperation(
            response = Hardware.class,
            notes = "Returns list of available Hardware.",
            value = "Returns all Hardware from data base.")
    public ResponseEntity<List<Hardware>> getHardware() {
        final List<Hardware> allHardware = hardwareMongoService.findAll();
        final ResponseEntity<List<Hardware>> response = ResponseEntity.ok().body(allHardware);
        return response;
    }

    @RequestMapping(
            value = "manufacturer/{manufacturer}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    @ApiOperation(
            response = Hardware.class,
            notes = "Finds Hardware by manufacturer. A manufacturer may have multiple hardware.",
            value = "Finds Hardware by manufacturer.")
    @ApiResponses({
        @ApiResponse(code = 404, response = ErrorMessage.class, message = "Content not found.")
    })
    public ResponseEntity<List<Hardware>> getHardwareByManufacturer(@PathVariable("manufacturer") String manufacturer) throws ContentNotFoundException {
        final List<Hardware> allHardware = hardwareMongoService.findByManufacturer(manufacturer);
        if (allHardware.isEmpty()) {
            throw new ContentNotFoundException();
        }
        final ResponseEntity<List<Hardware>> response = ResponseEntity.ok().body(allHardware);
        return response;
    }
    
    @RequestMapping(
            value = "{id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    @ApiOperation(
            response = Hardware.class,
            notes = "Finds Hardware by its Id.",
            value = "Finds Hardware by its Id.")
    @ApiResponses({
        @ApiResponse(code = 404, response = ErrorMessage.class, message = "Content not found.")
    })
    public ResponseEntity<Hardware> getHardwareById(@PathVariable("id") String id) throws ContentNotFoundException {
        final Hardware h = hardwareMongoService.findById(id);
        if (h == null) {
            throw new ContentNotFoundException();
        }
        final ResponseEntity<Hardware> response = ResponseEntity.ok().body(h);
        return response;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(
            response = Hardware.class,
            produces = MediaType.APPLICATION_JSON_VALUE,
            notes = "Implementation of this endpoint notes.",
            value = "Saves new Hardware to database.")
    public ResponseEntity<Void> addHardware(@Valid @RequestBody Hardware hardware) throws URISyntaxException {
        final Hardware h = hardwareMongoService.save(hardware);
        URI uri = new URI(String.format("%s/%s", PATH, h.getId()));
        final ResponseEntity<Void> response = ResponseEntity.created(uri).build();
        return response;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @ApiOperation(
            response = Hardware.class,
            produces = MediaType.APPLICATION_JSON_VALUE,
            notes = "Implementation of this endpoint notes.",
            value = "Deletes Hardware of given id from database.")
    public ResponseEntity<?> deleteHardware(@PathVariable("id") String id) {
        hardwareMongoService.deleteById(id);
        final ResponseEntity<?> response = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        return response;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    @ApiOperation(
            response = Hardware.class,
            produces = MediaType.APPLICATION_JSON_VALUE,
            notes = "Implementation of this endpoint notes.",
            value = "Updates Hardware of given id in database.")
    @ApiResponses({
        @ApiResponse(code = 404, response = ErrorMessage.class, message = "Content not found.")
    })
    public ResponseEntity<Void> updateHardware(@PathVariable("id") String id, @Valid @RequestBody Hardware hardware) throws ContentNotFoundException {
        final boolean foundOne = hardwareMongoService.exists(id);
        if (!foundOne) {
            throw new ContentNotFoundException();
        }
        final ResponseEntity<Void> response;
        hardwareMongoService.save(hardware);
        response = ResponseEntity.ok().build();
        return response;
    }
}
