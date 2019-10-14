package com.marcelosilva.samples.banking.web.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.ResourceSupport;

import java.math.BigDecimal;

@Getter
@Setter
public class AccountWebModel extends ResourceSupport {
    private BigDecimal balance;
}
