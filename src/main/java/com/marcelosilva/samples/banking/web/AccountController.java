package com.marcelosilva.samples.banking.web;

import com.marcelosilva.samples.banking.exception.BadRequestException;
import com.marcelosilva.samples.banking.exception.BusinessException;
import com.marcelosilva.samples.banking.exception.NoFundsException;
import com.marcelosilva.samples.banking.exception.NotFoundException;
import com.marcelosilva.samples.banking.model.Account;
import com.marcelosilva.samples.banking.service.AccountDataService;
import com.marcelosilva.samples.banking.service.AccountOperationService;
import com.marcelosilva.samples.banking.web.assembler.AccountAssembler;
import com.marcelosilva.samples.banking.web.model.AccountWebModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping(path = "/accounts")
public class AccountController {

    AccountDataService accountDataService;
    AccountOperationService accountOperationService;
    AccountAssembler accountAssembler;

    public AccountController(@Autowired AccountDataService accountDataService,
                             @Autowired AccountOperationService accountOperationService,
                             @Autowired AccountAssembler accountAssembler) {
        this.accountDataService = accountDataService;
        this.accountOperationService = accountOperationService;
        this.accountAssembler = accountAssembler;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public HttpEntity<AccountWebModel> get(@PathVariable Long id) {
        Account account = accountDataService.get(id).orElseThrow(() -> new NotFoundException("Account not found"));
        return new ResponseEntity<>(accountAssembler.toResource(account), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/{id}/withdraw")
    public HttpEntity<AccountWebModel> withdraw(@PathVariable Long id,
                                                @RequestParam BigDecimal amount) {
        try {
            Account account = accountOperationService.withdraw(id, amount);
            return new ResponseEntity<>(accountAssembler.toResource(account), HttpStatus.OK);
        } catch (NoFundsException | BusinessException e) {
            throw new BadRequestException(e.getMessage());
        }

    }

    @RequestMapping(method = RequestMethod.POST, path = "/{id}/deposit")
    public HttpEntity<AccountWebModel> deposit(@PathVariable Long id,
                                               @RequestParam BigDecimal amount) {
        try {
            Account account = accountOperationService.deposit(id, amount);
            return new ResponseEntity<>(accountAssembler.toResource(account), HttpStatus.OK);
        } catch (BusinessException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

}