package com.holyvia.Bookreview.services.converters;

import com.holyvia.Bookreview.dtos.SignupRequestDto;
import com.holyvia.Bookreview.dtos.SignupResponseDto;
import com.holyvia.Bookreview.entities.User;
import com.holyvia.Bookreview.enums.Role;

public interface UserConverterService {

    User fromSignupRequestDto(SignupRequestDto signupRequestDto, Role role);
    SignupResponseDto toSignupResponseDto(User user);
}
