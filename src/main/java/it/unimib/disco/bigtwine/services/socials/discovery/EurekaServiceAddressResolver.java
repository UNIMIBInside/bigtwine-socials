package it.unimib.disco.bigtwine.services.socials.discovery;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;

public class EurekaServiceAddressResolver implements ServiceAddressResolver {

    private final EurekaClient eurekaClient;

    public EurekaServiceAddressResolver(EurekaClient eurekaClient) {
        this.eurekaClient = eurekaClient;
    }

    @Override
    public String resolve(String serviceId, boolean secure) {
        Application application = eurekaClient.getApplication(serviceId);
        InstanceInfo instanceInfo = application.getInstances().get(0);
        String scheme = secure ? "https": "http";
        String hostname = instanceInfo.getIPAddr();
        int port = secure ? instanceInfo.getSecurePort() : instanceInfo.getPort();

        return scheme + "://" + hostname + ":" + port;
    }
}
