package biz.letsweb.teka.mongo.rest;

import biz.letsweb.teka.mongo.mongodb.HardwareMongoService;
import biz.letsweb.teka.mongo.mongodb.Hardware;
import biz.letsweb.teka.mongo.mongodb.Hardware;
import biz.letsweb.teka.mongo.mongodb.HardwareMongoService;
import biz.letsweb.teka.mongo.rest.controller.HardwareController;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.fest.assertions.Assertions;
import org.hibernate.validator.HibernateValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.any;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 *
 * @author tomasz
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MockServletContext.class)
@WebAppConfiguration
public class HardwareControllerTest {

    @InjectMocks
    private HardwareController hardwareController;

    @Mock
    private HardwareMongoService hardwareMongoService;

    private MockMvc mockMvc;

    private LocalValidatorFactoryBean validatorFactoryBean;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        validatorFactoryBean = new LocalValidatorFactoryBean();
        validatorFactoryBean.setProviderClass(HibernateValidator.class);
        validatorFactoryBean.afterPropertiesSet();
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(hardwareController)
                .setValidator(validatorFactoryBean)
                .build();
    }

    @Test
    public void returnAllAvailableHardware() throws Exception {

        final List<Hardware> hardwares = new ArrayList<Hardware>();
        hardwares.add(Hardware.builder().withId("1q2w3e4").build());

        Mockito.when(hardwareMongoService.findAll()).thenReturn(hardwares);

        ReflectionTestUtils.setField(hardwareController, "hardwareMongoService", hardwareMongoService);

        final MockHttpServletRequestBuilder get = get("/hardware")
                .accept(MediaType.APPLICATION_JSON);

        final MvcResult restResult = this.mockMvc.perform(get)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final String contentAsString = restResult.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        final List<Hardware> readValue = mapper.readValue(contentAsString, List.class);
        Assertions.assertThat(readValue.size()).isEqualTo(1);

    }

    @Test
    public void returnEmptyListwhenDatabaseReturnsEmptyList() throws Exception {

        final List<Hardware> hardwares = new ArrayList<Hardware>();

        Mockito.when(hardwareMongoService.findAll()).thenReturn(hardwares);

        ReflectionTestUtils.setField(hardwareController, "hardwareMongoService", hardwareMongoService);

        final MockHttpServletRequestBuilder get = get("/hardware")
                .accept(MediaType.APPLICATION_JSON);

        final MvcResult restResult = this.mockMvc.perform(get)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final String contentAsString = restResult.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        final List<Hardware> readValue = mapper.readValue(contentAsString, List.class);
        Assertions.assertThat(readValue.size()).isEqualTo(0);

    }

    @Test
    public void validationHaltsAddingHardwareWithEmptyManufacturer() throws Exception {

        Hardware h = Hardware.builder().withFirmware("1.2.3").withModel("Galaxy").build();
        Mockito.when(hardwareMongoService.save(h)).thenReturn(h);

        ReflectionTestUtils.setField(hardwareController, "hardwareMongoService", hardwareMongoService);
        
        URI uri = new URI("/hardware");
        
        final MockHttpServletRequestBuilder post = MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(h))
                .accept(MediaType.APPLICATION_JSON);

        this.mockMvc
                .perform(post)
                .andExpect(status().is4xxClientError())
                .andReturn();

    }
    
    @Test
    public void validationAllowsAddingHardware() throws Exception {

        Hardware h = Hardware.builder().withId("1q2w3e4r").withFirmware("1.2.3").withModel("Galaxy").withManufacturer("Samsung").build();

        Mockito.when(hardwareMongoService.save(any(Hardware.class))).thenReturn(h);

        ReflectionTestUtils.setField(hardwareController, "hardwareMongoService", hardwareMongoService);
        
        URI uri = new URI("/hardware");
        
        final MockHttpServletRequestBuilder post = MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(h))
                .accept(MediaType.APPLICATION_JSON);

        this.mockMvc
                .perform(post)
                .andExpect(status().is2xxSuccessful())
                .andReturn();

    }

    public String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
