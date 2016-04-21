package biz.lesweb.rest.one.mvc;

import biz.lesweb.rest.one.mvc.service.api.ServiceOne;
import static org.hamcrest.core.StringContains.containsString;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.fest.assertions.Assertions;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.StringUtils;

/**
 *
 * @author Tomasz
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MockServletContext.class) //sets up an empty mvc context
@WebAppConfiguration
public class RestOneControllerTest {

    @InjectMocks
    private RestOneController restOneController;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(restOneController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .build();
    }

    @After
    public void tearDown() {
    }


    @Test
    public void controllerCleanPathShowcase() throws Exception {
        String path = "admin/./error";
        Assertions.assertThat(StringUtils.cleanPath(path)).isEqualTo("admin/error");
    }

    @Test
    public void controllerWithMockedContextNeedsManualInjectionOfAutowiredField() throws Exception {
        final ServiceOne serviceOne = Mockito.mock(ServiceOne.class);
        
        //stubbing
        Mockito.doAnswer((Answer<Void>) (InvocationOnMock invocation) -> {
            System.out.println("Hello world");
            return null;
        }).when(serviceOne).doOne();
        ReflectionTestUtils.setField(restOneController, "serviceOne", serviceOne);
        
        final MockHttpServletRequestBuilder get = get("/")
                .accept(MediaType.APPLICATION_JSON);
        this.mockMvc.perform(get)
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Greetings from Rest 1!")));

    }

}
