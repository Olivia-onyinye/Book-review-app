package com.holyvia.Bookreview.services.serviceImpl;

import com.holyvia.Bookreview.dtos.SignupRequestDto;
import com.holyvia.Bookreview.dtos.SignupResponseDto;
import com.holyvia.Bookreview.entities.Token;
import com.holyvia.Bookreview.entities.User;
import com.holyvia.Bookreview.enums.Role;
import com.holyvia.Bookreview.exceptions.AlreadyExistsException;
import com.holyvia.Bookreview.exceptions.InvalidTokenException;
import com.holyvia.Bookreview.repositories.TokenRepository;
import com.holyvia.Bookreview.repositories.UserRepository;
import com.holyvia.Bookreview.services.AdminService;
import com.holyvia.Bookreview.services.JavaMailService;
import com.holyvia.Bookreview.services.UserTokenService;
import com.holyvia.Bookreview.services.converters.UserConverterService;
import com.holyvia.Bookreview.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static com.holyvia.Bookreview.enums.TokenStatus.EXPIRED;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;
    private final UserTokenService userTokenService;

    private final UserConverterService userConverterService;
    private final TokenRepository tokenRepository;
    private final JavaMailService javaMailService;
    private final HttpServletRequest request;

    @Override
    public ApiResponse<SignupResponseDto> signupAdmin(SignupRequestDto adminRequestDto) throws IOException {
        validateUser(adminRequestDto.getEmail());
        User adminUser = userConverterService.fromSignupRequestDto(adminRequestDto, Role.ADMIN);

        userRepository.save(adminUser);
        String registrationToken = userTokenService.generateAndSaveToken(adminUser);

        sendMail(adminRequestDto.getEmail(), adminUser, registrationToken);

        SignupResponseDto signupResponseDto = userConverterService.toSignupResponseDto(adminUser);
        return new ApiResponse<>("Admin Registration Successful", true, signupResponseDto);
    }

    @Override
    public ApiResponse<String> verifyRegistration(String token) {
        Token verifyToken = tokenRepository.findByToken(token).orElseThrow(() -> new InvalidTokenException("Token Not Found"));
        if(verifyToken.getTokenStatus().equals(EXPIRED))
            throw new InvalidTokenException("Token expired or already used");
        verifyToken.getUser().setVerificationStatus(true);
        verifyToken.setTokenStatus(EXPIRED);
        tokenRepository.save(verifyToken);
        return new ApiResponse<String>("Account verification successful", true, null);
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
