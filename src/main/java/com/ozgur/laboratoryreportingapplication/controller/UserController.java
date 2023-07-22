package com.ozgur.laboratoryreportingapplication.controller;

import com.ozgur.laboratoryreportingapplication.shared.RegisterRequest;
import com.ozgur.laboratoryreportingapplication.shared.UserResponse;
import com.ozgur.laboratoryreportingapplication.shared.ResponseMessage;
import com.ozgur.laboratoryreportingapplication.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseMessage<UserResponse> save(@Valid @RequestBody RegisterRequest request) {

        ResponseMessage<UserResponse> response = userService.saveUser(request);
        logger.info("Assistant saved with username " + request.getUsername());
        return response;
    }

    @GetMapping("getUsers")
    Page<UserResponse> getUsers(Pageable pageable){
        return userService.getUsers(pageable);
    }

}
