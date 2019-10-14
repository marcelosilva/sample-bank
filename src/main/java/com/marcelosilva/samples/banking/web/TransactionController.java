package com.marcelosilva.samples.banking.web;

import com.marcelosilva.samples.banking.service.TransactionService;
import com.marcelosilva.samples.banking.web.assembler.TransactionAssembler;
import com.marcelosilva.samples.banking.web.model.TransactionWebModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/accounts/{id}/transactions")
public class TransactionController {

    TransactionService transactionService;
    TransactionAssembler transactionAssembler;

    public TransactionController(@Autowired TransactionAssembler transactionAssembler,
                                 @Autowired TransactionService transactionService) {
        this.transactionAssembler = transactionAssembler;
        this.transactionService = transactionService;
    }

    @GetMapping
    public HttpEntity<List<TransactionWebModel>> getTransactions(@PathVariable Long id) {
        return new ResponseEntity<>(this.transactionAssembler.toResources(transactionService.findByAccount(id)),
                HttpStatus.OK);
    }

}