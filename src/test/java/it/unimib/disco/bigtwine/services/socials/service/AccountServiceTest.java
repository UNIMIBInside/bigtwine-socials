package it.unimib.disco.bigtwine.services.socials.service;

import it.unimib.disco.bigtwine.services.socials.config.Constants;
import it.unimib.disco.bigtwine.services.socials.connect.dto.AccountDTO;
import it.unimib.disco.bigtwine.services.socials.domain.Account;
import it.unimib.disco.bigtwine.services.socials.security.AuthoritiesConstants;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestOperations;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class AccountServiceTest {

    @Mock
    private InterServiceCommunication interServiceCommunication;

    @Mock
    private RestOperations restOperations;

    private AccountService accountService;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);

        when(interServiceCommunication.getRestOperations(anyString()))
            .thenReturn(restOperations);

        this.accountService = new AccountService(this.interServiceCommunication);
    }

    @Test
    public void testRegisterAccount() {
        when(restOperations.exchange(anyString(), any(), any(), (Class<Object>) any()))
            .thenAnswer((a) -> {
                AccountDTO reqAccount = ((HttpEntity<AccountDTO>)a.getArgument(2)).getBody();
                Account account = this.createAccount()
                    .id("1")
                    .activated(true)
                    .login(reqAccount.getLogin())
                    .email(reqAccount.getEmail())
                    .firstName(reqAccount.getFirstName())
                    .lastName(reqAccount.getLastName())
                    .imageUrl(reqAccount.getImageUrl());
                account.setAuthorities(reqAccount.getAuthorities());

                return new ResponseEntity<>(account, HttpStatus.CREATED);
            });

        Account account = this.createAccount()
            .login("User Name")
            .email("email@email.com");
        Optional<Account> registeredAccount = this.accountService.registerAccount(account);

        assertTrue(registeredAccount.isPresent());
        assertEquals("UserName", registeredAccount.get().getLogin());

        verify(interServiceCommunication, times(1)).getRestOperations(Constants.GATEWAY_SERVICE_ID);
    }

    @Test
    public void testGetAccountById() {
        when(restOperations.exchange(anyString(), any(), any(), (Class<Object>) any()))
            .thenAnswer((a) -> {
                Account account = this.createAccount()
                    .id("1")
                    .activated(true)
                    .login("login")
                    .email("user@email.com")
                    .firstName("firstname")
                    .lastName("lastname")
                    .imageUrl("http://fakehost/image.png")
                    .authorities(AuthoritiesConstants.USER);

                return new ResponseEntity<>(account, HttpStatus.OK);
            });

        Optional<Account> account = this.accountService.getAccountById("1");

        assertTrue(account.isPresent());
        assertEquals("login", account.get().getLogin());

        verify(interServiceCommunication, times(1)).getRestOperations(Constants.GATEWAY_SERVICE_ID);
        verify(restOperations, times(1)).exchange(eq("/api/users/id/1"), any(), any(), (Class<Object>) any());
    }

    private Account createAccount() {
        return new Account();
    }
}
