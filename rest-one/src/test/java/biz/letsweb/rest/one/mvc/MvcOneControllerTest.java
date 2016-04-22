package biz.letsweb.rest.one.mvc;

import biz.letsweb.rest.one.mvc.service.api.ServiceMvcOne;
import org.fest.assertions.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 *
 * @author tomasz
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MockServletContext.class) //sets up an empty mvc context
@WebAppConfiguration
public class MvcOneControllerTest {

    @InjectMocks
    private MvcOneController mvcOneController;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("src/main/resources/templates");
        viewResolver.setSuffix(".html");
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(mvcOneController)
                .setViewResolvers(viewResolver)
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
    public void mvcControllerWithMockedContextNeedsManualInjectionOfAutowiredField() throws Exception {
        final ServiceMvcOne serviceMvcOne = Mockito.mock(ServiceMvcOne.class);

        //stubbing of a service void method
        Mockito.doAnswer((Answer<Void>) (InvocationOnMock invocation) -> {
            System.out.println("Hello world");
            return null;
        }).when(serviceMvcOne).doOne();
        
        // manually set service instance field
        ReflectionTestUtils.setField(mvcOneController, "serviceMvcOne", serviceMvcOne);

        final MockHttpServletRequestBuilder get = get("/greeting?name=jurek")
                .accept(MediaType.APPLICATION_JSON);
        final MvcResult mvcResult = this.mockMvc
                .perform(get)
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        final Object name = mvcResult.getModelAndView().getModelMap().get("name");
        Assertions.assertThat(name).isEqualTo("żąć jurek");

    }

}
