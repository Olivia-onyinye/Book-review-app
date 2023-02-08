package com.holyvia.Bookreview.services.serviceImpl;

import com.holyvia.Bookreview.dtos.SignupRequestDto;
import com.holyvia.Bookreview.dtos.SignupResponseDto;
import com.holyvia.Bookreview.entities.User;
import com.holyvia.Bookreview.enums.Role;
import com.holyvia.Bookreview.exceptions.AlreadyExistsException;
import com.holyvia.Bookreview.repositories.TokenRepository;
import com.holyvia.Bookreview.repositories.UserRepository;
import com.holyvia.Bookreview.services.JavaMailService;
import com.holyvia.Bookreview.services.RegistrationHandler;
import com.holyvia.Bookreview.services.UserTokenService;
import com.holyvia.Bookreview.services.converters.UserConverterService;
import com.holyvia.Bookreview.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@Component
@RequiredArgsConstructor
public class UserRegistrationHandler implements RegistrationHandler {

    private final UserRepository userRepository;
    private final UserTokenService userTokenService;
    private final UserConverterService userConverterService;
    private final JavaMailService javaMailService;
    private final HttpServletRequest request;

    @Override
    public boolean canHandle(Role role) {
        return Role.USER.equals(role);
    }

    @Override
    public ApiResponse<SignupResponseDto> handleRegistration(SignupRequestDto signupRequestDto) throws IOException {
        validateUser(signupRequestDto.getEmail());

        User newUser = userConverterService.fromSignupRequestDto(signupRequestDto, Role.USER);
        userRepository.save(newUser);

        String registrationToken = userTokenService.generateAndSaveToken(newUser);
        sendMail(signupRequestDto.getEmail(), newUser, registrationToken);

        SignupResponseDto signupResponseDto = userConverterService.toSignupResponseDto(newUser);
        return new ApiResponse<>("Registration Successful! Check your mail for activation link", true, signupResponseDto);
    }

    private void sendMail(String email, User user, String registrationToken) throws IOException {
        javaMailService.sendMail(email,
                "Verify your email address",
                "Hello " + user.getFirstName() + " " + user.getLastName() + ", Thank you for your interest in using our Application." +
                        "To complete your registration, we need you to verify your email address \n" + "http://" +
                        request.getServerName() + ":8080" + "/api/v1/verifyRegistration?token=" + registrationToken);
    }


    private void validateUser(String email){
        boolean user = userRepository.existsByEmail(email);
        if (user)
            throw new AlreadyExistsException("This User with email address already exists");
    }
}
