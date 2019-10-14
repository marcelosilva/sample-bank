package com.marcelosilva.samples.banking.web;

import com.marcelosilva.samples.banking.exception.UnauthorizedException;
import com.marcelosilva.samples.banking.model.User;
import com.marcelosilva.samples.banking.service.UserService;
import com.marcelosilva.samples.banking.web.assembler.UserAccountAssembler;
import com.marcelosilva.samples.banking.web.model.UserWebModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(path = "/auth")
public class AuthenticationController {

    private UserService userService;

    private UserAccountAssembler userAssembler;

    public AuthenticationController(@Autowired UserService userService,
                                    @Autowired UserAccountAssembler userAssembler) {
        this.userService = userService;
        this.userAssembler = userAssembler;
    }

    @RequestMapping(method = RequestMethod.POST)
    public HttpEntity<UserWebModel> authenticate(@RequestParam String email,
                                                 @RequestParam String password) {

        Optional<User> user = userService.authenticate(email, password);

        if (!user.isPresent()) {
            throw new UnauthorizedException("Email or password incorrect");
        }

        return new ResponseEntity<>(userAssembler.toResource(user.get()), HttpStatus.OK);
    }

}