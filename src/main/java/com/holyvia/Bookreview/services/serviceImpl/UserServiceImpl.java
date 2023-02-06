package com.holyvia.Bookreview.services.serviceImpl;

import com.holyvia.Bookreview.configurations.tokens.TokenServiceImpl;
import com.holyvia.Bookreview.configurations.userDetails.AppUserDetailsService;
import com.holyvia.Bookreview.dtos.SignupRequestDto;
import com.holyvia.Bookreview.dtos.SignupResponseDto;
import com.holyvia.Bookreview.entities.Token;
import com.holyvia.Bookreview.entities.User;
import com.holyvia.Bookreview.enums.Gender;
import com.holyvia.Bookreview.enums.Role;
import com.holyvia.Bookreview.enums.TokenStatus;
import com.holyvia.Bookreview.exceptions.AlreadyExistsException;
import com.holyvia.Bookreview.exceptions.InvalidTokenException;
import com.holyvia.Bookreview.repositories.TokenRepository;
import com.holyvia.Bookreview.repositories.UserRepository;
import com.holyvia.Bookreview.services.JavaMailService;
import com.holyvia.Bookreview.services.UserService;
import com.holyvia.Bookreview.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static com.holyvia.Bookreview.enums.TokenStatus.EXPIRED;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final TokenServiceImpl tokenService;
    private final TokenRepository tokenRepository;
    private final JavaMailService javaMailService;
    private final HttpServletRequest request;
    private final PasswordEncoder passwordEncoder;


    @Override
    public ApiResponse<SignupResponseDto> signupUser(SignupRequestDto requestDto) throws IOException {
        boolean user = userRepository.existsByEmail(requestDto.getEmail());
        if (user)
            throw new AlreadyExistsException("This  User with email address already exists");
        User newUser = new User();
        BeanUtils.copyProperties(requestDto, newUser);
        newUser.setRole(Role.USER);
        newUser.setVerificationStatus(false);
        newUser.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        userRepository.save(newUser);

        String registrationToken = tokenService.generateVerificationToken(requestDto.getEmail());
        Token token = new Token();
        token.setToken(registrationToken);
        token.setTokenStatus(TokenStatus.ACTIVE);
        token.setUser(newUser);
        tokenRepository.save(token);

        javaMailService.sendMail(requestDto.getEmail(),
                "Verify your email address",
                "Hello " + newUser.getFirstName() + " " + newUser.getLastName() + ", Thank you for your interest in using our Application." +
                        "To complete your registration, we need you to verify your email address \n" + "http://" +
                        request.getServerName() + ":8080" + "/api/v1/verifyRegistration?token=" + registrationToken);

        SignupResponseDto signupResponseDto = new SignupResponseDto();
        BeanUtils.copyProperties(newUser, signupResponseDto);
        return new ApiResponse<>("Registration Successful! Check your mail for activation link", true, signupResponseDto);
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
}