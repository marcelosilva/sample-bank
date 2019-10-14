package com.marcelosilva.samples.banking.web.assembler;

import com.marcelosilva.samples.banking.model.Transaction;
import com.marcelosilva.samples.banking.web.model.TransactionWebModel;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransactionAssembler implements ResourceAssembler<Transaction, TransactionWebModel> {

    @Override
    public TransactionWebModel toResource(Transaction transaction) {

        TransactionWebModel transactionWebModel = new TransactionWebModel();
        transactionWebModel.setExecutedAt(transaction.getExecutedAt());
        transactionWebModel.setValue(transaction.getValue());
        transactionWebModel.setTransactionType(transaction.getTransactionType().name());
        return transactionWebModel;

    }

    public List<TransactionWebModel> toResources(List<Transaction> transactions) {

        return transactions
                .stream()
                .map(transaction -> toResource(transaction))
                .collect(Collectors.toList());

    }
}
