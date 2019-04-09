package it.unimib.disco.bigtwine.services.socials.discovery;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

public class SpringServiceAddressResolver implements ServiceAddressResolver {

    private final DiscoveryClient discoveryClient;

    public SpringServiceAddressResolver(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @Override
    public String resolve(String serviceId, boolean secure) {
        ServiceInstance instanceInfo = discoveryClient.getInstances(serviceId).get(0);
        String scheme = instanceInfo.getScheme();
        String hostname = instanceInfo.getHost();
        int port = instanceInfo.getPort();

        return scheme + "://" + hostname + ":" + port;
    }
}
