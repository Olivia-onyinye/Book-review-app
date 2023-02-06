package com.holyvia.Bookreview.services;

import com.holyvia.Bookreview.dtos.LoginDto;
import com.holyvia.Bookreview.dtos.SignupRequestDto;
import com.holyvia.Bookreview.dtos.SignupResponseDto;
import com.holyvia.Bookreview.utils.ApiResponse;

import java.io.IOException;

public interface UserService {
    ApiResponse<SignupResponseDto> signupUser (SignupRequestDto requestDto) throws IOException;
    ApiResponse<String> verifyRegistration(String token);

}
