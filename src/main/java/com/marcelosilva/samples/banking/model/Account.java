package com.marcelosilva.samples.banking.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "account")
    private List<Transaction> transactions;

    private BigDecimal balance;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    public boolean hasEnoughFunds(BigDecimal amount) {
        if (balance != null && amount != null && balance.compareTo(amount) >= 0) {
            return true;
        } else {
            return false;
        }
    }


}
