package com.marcelosilva.samples.banking.web.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.ResourceSupport;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class TransactionWebModel extends ResourceSupport {

    private String transactionType;
    private BigDecimal value;
    private Date executedAt;

}
