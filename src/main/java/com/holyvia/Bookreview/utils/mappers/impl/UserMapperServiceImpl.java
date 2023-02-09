package com.holyvia.Bookreview.utils.mappers.impl;

import com.holyvia.Bookreview.dtos.SignupRequestDto;
import com.holyvia.Bookreview.dtos.SignupResponseDto;
import com.holyvia.Bookreview.entities.User;
import com.holyvia.Bookreview.enums.Role;
import com.holyvia.Bookreview.utils.mappers.UserMapperService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserMapperServiceImpl implements UserMapperService {

    private final PasswordEncoder passwordEncoder;

    @Override
    public User fromSignupRequestDtoToUser(SignupRequestDto signupRequestDto, Role role) {
        User user = new User();
        BeanUtils.copyProperties(signupRequestDto, user);
        user.setRole(role);
        user.setVerificationStatus(false);
        user.setPassword(passwordEncoder.encode(signupRequestDto.getPassword()));
        return user;
    }

    @Override
    public SignupResponseDto userToSignupResponseDto(User user) {
        SignupResponseDto signupResponseDto = new SignupResponseDto();
        BeanUtils.copyProperties(user, signupResponseDto);
        return signupResponseDto;
    }
}
