package it.unimib.disco.bigtwine.services.socials.service;

import it.unimib.disco.bigtwine.services.socials.discovery.ServiceAddressResolver;
import it.unimib.disco.bigtwine.services.socials.security.jwt.JWTAuthRestInterceptor;
import it.unimib.disco.bigtwine.services.socials.security.jwt.TokenProvider;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

@Service
public class InterServiceCommunication {

    private final RestTemplateBuilder restTemplateBuilder;
    private final TokenProvider tokenProvider;
    private final ServiceAddressResolver serviceAddressResolver;

    public InterServiceCommunication(RestTemplateBuilder restTemplateBuilder, TokenProvider tokenProvider, ServiceAddressResolver serviceAddressResolver) {
        this.restTemplateBuilder = restTemplateBuilder;
        this.tokenProvider = tokenProvider;
        this.serviceAddressResolver = serviceAddressResolver;
    }

    public RestOperations getRestOperations(String serviceId) {
        String token = this.tokenProvider.createSystemToken();
        JWTAuthRestInterceptor interceptor = new JWTAuthRestInterceptor(token);
        String baseUrl = this.serviceAddressResolver.resolve(serviceId, false);

        return this
            .restTemplateBuilder
            .rootUri(baseUrl)
            .additionalInterceptors(interceptor)
            .build();
    }
}
