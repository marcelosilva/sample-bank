package com.marcelosilva.samples.banking.web;

import com.marcelosilva.samples.banking.exception.BadRequestException;
import com.marcelosilva.samples.banking.exception.BusinessException;
import com.marcelosilva.samples.banking.model.User;
import com.marcelosilva.samples.banking.service.UserService;
import com.marcelosilva.samples.banking.web.assembler.UserAccountAssembler;
import com.marcelosilva.samples.banking.web.model.UserWebModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    private UserService userService;
    private UserAccountAssembler userAccountAssembler;

    public UserController(@Autowired UserService userService,
                          @Autowired UserAccountAssembler userAssembler) {
        this.userService = userService;
        this.userAccountAssembler = userAssembler;
    }

    @RequestMapping(method = RequestMethod.POST)
    public HttpEntity<UserWebModel> create(@RequestBody @Valid UserWebModel user) {
        try {
            User createdUser = userService.createWithAccount(userAccountAssembler.toEntity(user));
            return new ResponseEntity<>(userAccountAssembler.toResource(createdUser), HttpStatus.OK);
        } catch (BusinessException e) {
            throw new BadRequestException(e.getMessage());
        }

    }

}