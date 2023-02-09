package com.holyvia.Bookreview.services;

import com.holyvia.Bookreview.dtos.SignupRequestDto;
import com.holyvia.Bookreview.dtos.SignupResponseDto;
import com.holyvia.Bookreview.enums.Role;
import com.holyvia.Bookreview.utils.ApiResponse;

import java.io.IOException;

public interface RegistrationHandler {

    boolean canHandle(Role role);

    ApiResponse<SignupResponseDto> handleRegistration(SignupRequestDto signupRequestDto) throws IOException;

}
