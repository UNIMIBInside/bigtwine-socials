package it.unimib.disco.bigtwine.services.socials.domain;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Account {
    private String id;
    private String login;
    private String email;
    private String firstName;
    private String lastName;
    private String imageUrl;
    private Set<String> authorities;
    private String password;
    private boolean activated;

    public Account() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Account id(String id) {
        this.id = id;
        return this;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Account login(String login) {
        this.login = login;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Account email(String email) {
        this.email = email;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Account firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Account lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Account imageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Account password(String password) {
        this.password = password;
        return this;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public Account activated(boolean activated) {
        this.activated = activated;
        return this;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }

    public Account authorities(String... authorities) {
        Set<String> authoritiesSet = new HashSet<>();
        Collections.addAll(authoritiesSet, authorities);
        this.setAuthorities(authoritiesSet);

        return this;
    }

    public UserDetails createUserDetails() {
        return User
            .withUsername(this.getLogin())
            .password("")
            .authorities(AuthorityUtils.createAuthorityList(this.getAuthorities().toArray(new String[0])))
            .build();
    }
}
