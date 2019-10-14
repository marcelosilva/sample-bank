package com.marcelosilva.samples.banking.web.assembler;

import com.marcelosilva.samples.banking.model.Account;
import com.marcelosilva.samples.banking.web.AccountController;
import com.marcelosilva.samples.banking.web.model.AccountWebModel;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class AccountAssembler implements ResourceAssembler<Account, AccountWebModel> {
    @Override
    public AccountWebModel toResource(Account entity) {

        AccountWebModel accountWebModel = new AccountWebModel();
        accountWebModel.setBalance(entity.getBalance());
        accountWebModel.add(linkTo(methodOn(AccountController.class).get(entity.getId())).withSelfRel());

        return accountWebModel;
    }
}
