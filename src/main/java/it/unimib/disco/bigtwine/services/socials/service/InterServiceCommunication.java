package it.unimib.disco.bigtwine.services.socials.service;

import it.unimib.disco.bigtwine.services.socials.security.jwt.JWTAuthRestInterceptor;
import it.unimib.disco.bigtwine.services.socials.security.jwt.TokenProvider;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import java.net.URI;

@Service
public class InterServiceCommunication {

    private final RestTemplateBuilder restTemplateBuilder;
    private final TokenProvider tokenProvider;
    private final DiscoveryClient discoveryClient;

    public InterServiceCommunication(RestTemplateBuilder restTemplateBuilder, TokenProvider tokenProvider, DiscoveryClient discoveryClient) {
        this.restTemplateBuilder = restTemplateBuilder;
        this.tokenProvider = tokenProvider;
        this.discoveryClient = discoveryClient;
    }

    public URI resolveServiceUri(String serviceId) {
        ServiceInstance instanceInfo = discoveryClient.getInstances(serviceId).get(0);

        return instanceInfo.getUri();
    }

    public RestOperations getRestOperations(String serviceId) {
        String token = this.tokenProvider.createSystemToken();
        JWTAuthRestInterceptor interceptor = new JWTAuthRestInterceptor(token);
        String baseUrl = this.resolveServiceUri(serviceId).toString();

        return this
            .restTemplateBuilder
            .rootUri(baseUrl)
            .additionalInterceptors(interceptor)
            .build();
    }
}
