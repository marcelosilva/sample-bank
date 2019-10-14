package com.marcelosilva.samples.banking.web.model;

import org.springframework.hateoas.ResourceSupport;

import java.util.List;


public class UserWebModel extends ResourceSupport {
    private String email;
    private String password;
    private List<AccountWebModel> accounts;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<AccountWebModel> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<AccountWebModel> accounts) {
        this.accounts = accounts;
    }
}
