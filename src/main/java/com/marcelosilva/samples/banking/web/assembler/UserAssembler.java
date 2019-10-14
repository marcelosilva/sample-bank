package com.marcelosilva.samples.banking.web.assembler;

import com.marcelosilva.samples.banking.model.User;
import com.marcelosilva.samples.banking.web.model.UserWebModel;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;


@Component
public class UserAssembler implements ResourceAssembler<User, UserWebModel> {

    @Override
    public UserWebModel toResource(User entity) {
        UserWebModel userWebModel = new UserWebModel();
        userWebModel.setEmail(entity.getEmail());
        return userWebModel;
    }

    public User toEntity(UserWebModel userWebModel) {

        return User.builder().email(userWebModel.getEmail()).password(userWebModel.getPassword()).build();

    }

}
