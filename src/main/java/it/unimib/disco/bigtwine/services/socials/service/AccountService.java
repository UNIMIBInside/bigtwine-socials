package it.unimib.disco.bigtwine.services.socials.service;

import it.unimib.disco.bigtwine.services.socials.config.Constants;
import it.unimib.disco.bigtwine.services.socials.connect.dto.AccountDTO;
import it.unimib.disco.bigtwine.services.socials.connect.mapper.AccountMapper;
import it.unimib.disco.bigtwine.services.socials.domain.Account;
import it.unimib.disco.bigtwine.services.socials.security.AuthoritiesConstants;
import it.unimib.disco.bigtwine.services.socials.security.SecurityUtils;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestOperations;

import java.util.Optional;

@Service
public class AccountService {
    private final InterServiceCommunication serviceCommunication;

    public AccountService(InterServiceCommunication serviceCommunication) {
        this.serviceCommunication = serviceCommunication;
    }

    private AccountDTO prepareAccount(Account account) {
        return AccountMapper.INSTANCE.accountToAccountDTO(account)
            .login(SecurityUtils.cleanUsername(account.getLogin()))
            .authorities(AuthoritiesConstants.USER, AuthoritiesConstants.USER_SOCIAL);
    }

    public Optional<Account> registerAccount(Account account) {
        AccountDTO accountInfo = this.prepareAccount(account);
        RestOperations rest = this.serviceCommunication.getRestOperations(Constants.GATEWAY_SERVICE_ID);
        String url = "/api/users";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<AccountDTO> entity = new HttpEntity<>(accountInfo, headers);
        ResponseEntity<Account> response = rest.exchange(url, HttpMethod.POST, entity, Account.class);

        if (response.getStatusCode() != HttpStatus.CREATED || response.getBody() == null) {
            return Optional.empty();
        }

        return Optional.of(response.getBody());
    }

    public Optional<Account> getAccountById(String id) {
        RestOperations rest = this.serviceCommunication.getRestOperations(Constants.GATEWAY_SERVICE_ID);
        String url = "/api/users/id/" + id;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Void> entity = new HttpEntity<>(null, headers);

        try {
            ResponseEntity<Account> response = rest.exchange(url, HttpMethod.GET, entity, Account.class);

            return Optional.ofNullable(response.getBody());
        }catch (RestClientException e) {
            return Optional.empty();
        }
    }
}
