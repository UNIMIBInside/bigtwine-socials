package it.unimib.disco.bigtwine.services.socials.service;

import it.unimib.disco.bigtwine.services.socials.config.Constants;
import it.unimib.disco.bigtwine.services.socials.security.jwt.JWTAuthRestInterceptor;
import it.unimib.disco.bigtwine.services.socials.security.jwt.TokenProvider;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import org.hamcrest.MatcherAssert;
import org.hamcrest.CoreMatchers;

public class InterServiceCommunicationTest {

    private final static String FAKE_BASE_URI = "http://fakehost:9000";
    private final static String FAKE_SYSTEM_TOKEN = "aabbccdd";

    @Mock
    private TokenProvider tokenProvider;

    private RestTemplateBuilder restTemplateBuilder;

    @Mock
    private DiscoveryClient discoveryClient;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);

        ServiceInstance instance = new DefaultServiceInstance(
            Constants.GATEWAY_SERVICE_ID,
            "fakehost",
            9000,
            false,
            new HashMap<>()
        );
        List<ServiceInstance> instances = new ArrayList<>();
        instances.add(instance);

        when(this.discoveryClient.getInstances(Constants.GATEWAY_SERVICE_ID)).thenReturn(instances);
        when(this.tokenProvider.createSystemToken()).thenReturn(FAKE_SYSTEM_TOKEN);
        this.restTemplateBuilder = new RestTemplateBuilder();
    }

    @Test
    public void testGetRestOperations() {
        InterServiceCommunication interServiceCommunication = new InterServiceCommunication(restTemplateBuilder, tokenProvider, discoveryClient);
        RestOperations restOps = interServiceCommunication.getRestOperations(Constants.GATEWAY_SERVICE_ID);

        assertEquals(RestTemplate.class, restOps.getClass());

        RestTemplate rest = (RestTemplate)restOps;

        URI uri = rest.getUriTemplateHandler().expand("/");

        assertEquals("http", uri.getScheme());
        assertEquals("fakehost", uri.getHost());
        assertEquals(9000, uri.getPort());

        MatcherAssert.assertThat(rest.getInterceptors(), CoreMatchers.hasItem(
            CoreMatchers.isA(JWTAuthRestInterceptor.class)
        ));

        JWTAuthRestInterceptor interceptor = (JWTAuthRestInterceptor)rest
            .getInterceptors()
            .stream()
            .filter((a) -> a instanceof JWTAuthRestInterceptor)
            .findFirst()
            .orElse(null);

        assertNotNull(interceptor);
        assertThat(interceptor.getToken(), CoreMatchers.equalTo(FAKE_SYSTEM_TOKEN));
    }
}
