package it.unimib.disco.bigtwine.services.socials.service;

import it.unimib.disco.bigtwine.services.socials.client.AuthServiceClient;
import it.unimib.disco.bigtwine.services.socials.connect.dto.AccountDTO;
import it.unimib.disco.bigtwine.services.socials.connect.mapper.AccountMapper;
import it.unimib.disco.bigtwine.services.socials.domain.Account;
import it.unimib.disco.bigtwine.services.socials.security.AuthoritiesConstants;
import it.unimib.disco.bigtwine.services.socials.security.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {
    private final AuthServiceClient authServiceClient;

    public AccountService(AuthServiceClient authServiceClient) {
        this.authServiceClient = authServiceClient;
    }

    private AccountDTO prepareAccount(Account account) {
        return AccountMapper.INSTANCE.accountToAccountDTO(account)
            .login(SecurityUtils.cleanUsername(account.getLogin()))
            .authorities(AuthoritiesConstants.USER, AuthoritiesConstants.USER_SOCIAL);
    }

    public Optional<Account> registerAccount(Account account) {
        AccountDTO accountInfo = this.prepareAccount(account);
        try {
            Account registeredAccount = authServiceClient.createAccount(accountInfo);
            return Optional.ofNullable(registeredAccount);
        }catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<Account> getAccountById(String id) {
        try {
            Account account = authServiceClient.findAccountById(id);
            return Optional.ofNullable(account);
        }catch (Exception e) {
            return Optional.empty();
        }
    }
}
