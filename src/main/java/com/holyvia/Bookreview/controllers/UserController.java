package com.holyvia.Bookreview.controllers;

import com.holyvia.Bookreview.dtos.SignupRequestDto;
import com.holyvia.Bookreview.dtos.SignupResponseDto;
import com.holyvia.Bookreview.services.UserService;
import com.holyvia.Bookreview.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping("/user/signup")
    public ResponseEntity<ApiResponse<SignupResponseDto>> signup(@Valid @RequestBody SignupRequestDto requestDto) throws IOException {
        return new ResponseEntity<>(userService.signupUser(requestDto),HttpStatus.CREATED);
    }

    @GetMapping("/verifyRegistration")
    public ResponseEntity<ApiResponse<String>> verifyAccount(@RequestParam("token") String token){
        return new ResponseEntity<>(userService.verifyRegistration(token), HttpStatus.OK);
    }
}
