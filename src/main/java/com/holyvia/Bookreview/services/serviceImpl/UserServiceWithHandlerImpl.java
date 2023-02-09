package com.holyvia.Bookreview.services.serviceImpl;

import com.holyvia.Bookreview.dtos.SignupRequestDto;
import com.holyvia.Bookreview.dtos.SignupResponseDto;
import com.holyvia.Bookreview.entities.Token;
import com.holyvia.Bookreview.exceptions.InvalidTokenException;
import com.holyvia.Bookreview.repositories.TokenRepository;
import com.holyvia.Bookreview.services.RegistrationHandler;
import com.holyvia.Bookreview.services.UserServiceWithHandler;
import com.holyvia.Bookreview.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static com.holyvia.Bookreview.enums.TokenStatus.EXPIRED;

@Service
@RequiredArgsConstructor
public class UserServiceWithHandlerImpl  implements UserServiceWithHandler {
    private final List<RegistrationHandler> registrationHandlers;
    private final TokenRepository tokenRepository;

    @Override
    public ApiResponse<SignupResponseDto> signupUser(SignupRequestDto requestDto) throws IOException {
        RegistrationHandler registrationHandler = registrationHandlers.stream()
                .filter(handler -> handler.canHandle(requestDto.getRole()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No handler found for role: " + requestDto.getRole()));

       return registrationHandler.handleRegistration(requestDto);
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
