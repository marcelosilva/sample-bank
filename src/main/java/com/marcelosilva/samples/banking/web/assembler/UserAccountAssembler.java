package com.marcelosilva.samples.banking.web.assembler;

import com.marcelosilva.samples.banking.model.User;
import com.marcelosilva.samples.banking.web.model.UserWebModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;


@Component
public class UserAccountAssembler implements ResourceAssembler<User, UserWebModel> {

    @Autowired
    private AccountAssembler accountAssembler;

    @Override
    public UserWebModel toResource(User entity) {
        UserWebModel userWebModel = new UserWebModel();
        userWebModel.setEmail(entity.getEmail());

        if (entity.getAccounts() != null) {
            userWebModel.setAccounts(entity.getAccounts()
                    .stream()
                    .map(account -> accountAssembler.toResource(account)).collect(Collectors.toList()));
        }

        return userWebModel;
    }

    public User toEntity(UserWebModel userWebModel) {
        return User.builder().email(userWebModel.getEmail()).password(userWebModel.getPassword()).build();
    }

}
