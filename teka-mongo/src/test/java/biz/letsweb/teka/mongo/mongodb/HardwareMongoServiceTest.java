package biz.letsweb.teka.mongo.mongodb;

import biz.letsweb.teka.mongo.mongodb.HardwareMongoService;
import biz.letsweb.teka.mongo.mongodb.Hardware;
import biz.letsweb.teka.mongo.RestRepositoryMain;
import java.util.List;
import org.fest.assertions.Assertions;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 *
 * @author tomasz
 */
@Ignore(value = "Ignore until connectivity to database is organised.")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RestRepositoryMain.class)
@WebAppConfiguration
public class HardwareMongoServiceTest {

    @Autowired
    private HardwareMongoService hardwareMongoService;


    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of doOperations method, of class MongodbConnector.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testDoOperations() throws Exception {
        this.hardwareMongoService.deleteAll();

        this.hardwareMongoService.save(Hardware.builder().withManufacturer("Samsung").withModel("Galaxy S2").withFirmware("1.2", "1.2.3").build());
        this.hardwareMongoService.save(Hardware.builder().withManufacturer("Starmax").withModel("Galaxy S2").withFirmware("1.3").build());
        final List<Hardware> foundAll = this.hardwareMongoService.findAll();

        Assertions.assertThat(foundAll.size()).isEqualTo(2);

        final List<Hardware> foundSamsungByManufacturer = this.hardwareMongoService.findByManufacturer("Samsung");
        System.out.println(foundSamsungByManufacturer);

        Assertions.assertThat(foundSamsungByManufacturer.get(0).getModel()).isEqualTo("Galaxy S2");
        final List<Hardware> foundByModel = this.hardwareMongoService.findByModel("Galaxy S2");

        Assertions.assertThat(foundByModel.size()).isEqualTo(2);

        final List<Hardware> findByFirmware = hardwareMongoService.findByFirmware("1.2.3");
        Assertions.assertThat(findByFirmware.size()).isEqualTo(1);
    }


    
}
