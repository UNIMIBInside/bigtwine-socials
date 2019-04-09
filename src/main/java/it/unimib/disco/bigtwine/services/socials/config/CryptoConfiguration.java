package it.unimib.disco.bigtwine.services.socials.config;

import io.github.jhipster.config.JHipsterConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;

@Configuration
@Profile("!" + JHipsterConstants.SPRING_PROFILE_DEVELOPMENT)
public class CryptoConfiguration {
    @Bean
    public TextEncryptor getTextEncryptor(
        @Value("${application.security.encryptors.secret}") String password,
        @Value("${application.security.encryptors.salt}") String salt) {
        return Encryptors.text(password, salt);
    }
}
