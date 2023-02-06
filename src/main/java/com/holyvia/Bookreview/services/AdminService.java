package com.holyvia.Bookreview.services;

import com.holyvia.Bookreview.dtos.SignupRequestDto;
import com.holyvia.Bookreview.dtos.SignupResponseDto;
import com.holyvia.Bookreview.utils.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface AdminService {
    ApiResponse<SignupResponseDto> signupAdmin (SignupRequestDto adminRequestDto) throws IOException;
    ApiResponse<String> verifyRegistration(String token);
}
