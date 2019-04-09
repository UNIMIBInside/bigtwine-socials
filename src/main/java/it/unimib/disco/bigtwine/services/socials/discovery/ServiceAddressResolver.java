package it.unimib.disco.bigtwine.services.socials.discovery;

public interface ServiceAddressResolver {
    String resolve(String serviceId, boolean secure);
    default String resolve(String serviceId) {
        return resolve(serviceId, false);
    }
}
