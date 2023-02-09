package com.holyvia.Bookreview.services.serviceImpl;

import com.holyvia.Bookreview.configurations.tokens.TokenService;
import com.holyvia.Bookreview.dtos.SignupRequestDto;
import com.holyvia.Bookreview.dtos.SignupResponseDto;
import com.holyvia.Bookreview.entities.Token;
import com.holyvia.Bookreview.entities.User;
import com.holyvia.Bookreview.enums.Role;
import com.holyvia.Bookreview.enums.TokenStatus;
import com.holyvia.Bookreview.exceptions.AlreadyExistsException;
import com.holyvia.Bookreview.repositories.TokenRepository;
import com.holyvia.Bookreview.repositories.UserRepository;
import com.holyvia.Bookreview.services.JavaMailService;
import com.holyvia.Bookreview.services.RegistrationHandler;
import com.holyvia.Bookreview.utils.mappers.UserMapperService;
import com.holyvia.Bookreview.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AdminRegistrationHandler implements RegistrationHandler {

    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final TokenRepository tokenRepository;
    private final UserMapperService userMapperService;
    private final JavaMailService javaMailService;
    private final HttpServletRequest request;


    @Override
    public boolean canHandle(Role role) {

        return Role.ADMIN.equals(role);

    }

    @Override
    public ApiResponse<SignupResponseDto> handleRegistration(SignupRequestDto signupRequestDto) throws IOException {
        validateUser(signupRequestDto.getEmail());
        User adminUser = userMapperService.fromSignupRequestDtoToUser(signupRequestDto, Role.ADMIN);

        userRepository.save(adminUser);
        String registrationToken = generateAndSaveToken(adminUser);

        sendMail(signupRequestDto.getEmail(), adminUser, registrationToken);

        SignupResponseDto signupResponseDto = userMapperService.userToSignupResponseDto(adminUser);
        return new ApiResponse<>("Admin Registration Successful", true, signupResponseDto);
    }

    private void sendMail(String email, User user, String registrationToken) throws IOException {
        javaMailService.sendMail(email,
                "Verify your email address",
                "Hello " + user.getFirstName() + " " + user.getLastName() + ", Thank you for your interest in using our Application." +
                        "To complete your registration, we need you to verify your email address \n" + "http://" +
                        request.getServerName() + ":8080" + "/api/v1/verifyRegistration?token=" + registrationToken);
    }


    private void validateUser(String email) {
        boolean user = userRepository.existsByEmail(email);
        if (user)
            throw new AlreadyExistsException("This User with email address already exists");
    }

    private String generateAndSaveToken(User user) {
        String registrationToken = tokenService.generateVerificationToken(user.getEmail());
        Token token = new Token();
        token.setToken(registrationToken);
        token.setTokenStatus(TokenStatus.ACTIVE);
        token.setUser(user);
        tokenRepository.save(token);
        return registrationToken;
    }
}