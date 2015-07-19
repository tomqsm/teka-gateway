package biz.lesweb.rest.one.mvc;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.BeforeClass;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 *
 * @author Tomasz
 */
public class RestOneControllerTest {
    
    public RestOneControllerTest() {
    }
    
        private final Logger logger = LoggerFactory.getLogger(getClass());

    @InjectMocks
    RestOneController welcomeRestController;

    MockMvc mockMvc;

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(welcomeRestController).setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void controllerFound() throws Exception {
        final MockHttpServletRequestBuilder get = get("/");
        this.mockMvc.perform(get)
                //                .andDo(print())
                .andExpect(status().isOk());
    }
    
}
