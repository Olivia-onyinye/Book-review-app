package com.holyvia.Bookreview.utils.mappers;

import com.holyvia.Bookreview.dtos.SignupRequestDto;
import com.holyvia.Bookreview.dtos.SignupResponseDto;
import com.holyvia.Bookreview.entities.User;
import com.holyvia.Bookreview.enums.Role;

public interface UserMapperService {

    User fromSignupRequestDtoToUser(SignupRequestDto signupRequestDto, Role role);
    SignupResponseDto userToSignupResponseDto(User user);
}
