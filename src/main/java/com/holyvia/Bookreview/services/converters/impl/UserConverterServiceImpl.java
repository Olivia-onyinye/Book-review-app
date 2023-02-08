package com.holyvia.Bookreview.services.converters.impl;

import com.holyvia.Bookreview.dtos.SignupRequestDto;
import com.holyvia.Bookreview.dtos.SignupResponseDto;
import com.holyvia.Bookreview.entities.User;
import com.holyvia.Bookreview.enums.Role;
import com.holyvia.Bookreview.services.converters.UserConverterService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserConverterServiceImpl implements UserConverterService {

    private final PasswordEncoder passwordEncoder;

    @Override
    public User fromSignupRequestDto(SignupRequestDto signupRequestDto, Role role) {
        User user = new User();
        BeanUtils.copyProperties(signupRequestDto, user);
        user.setRole(role);
        user.setVerificationStatus(false);
        user.setPassword(passwordEncoder.encode(signupRequestDto.getPassword()));
        return user;
    }

    @Override
    public SignupResponseDto toSignupResponseDto(User user) {
        SignupResponseDto signupResponseDto = new SignupResponseDto();
        BeanUtils.copyProperties(user, signupResponseDto);
        return signupResponseDto;
    }
}
