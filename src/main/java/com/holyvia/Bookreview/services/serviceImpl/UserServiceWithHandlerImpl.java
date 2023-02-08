package com.holyvia.Bookreview.services.serviceImpl;

import com.holyvia.Bookreview.dtos.SignupRequestDto;
import com.holyvia.Bookreview.dtos.SignupResponseDto;
import com.holyvia.Bookreview.services.RegistrationHandler;
import com.holyvia.Bookreview.services.UserServiceWithHandler;
import com.holyvia.Bookreview.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceWithHandlerImpl  implements UserServiceWithHandler {
    private final List<RegistrationHandler> registrationHandlers;

    @Override
    public ApiResponse<SignupResponseDto> signupUser(SignupRequestDto requestDto) throws IOException {
        RegistrationHandler registrationHandler = registrationHandlers.stream()
                .filter(handler -> handler.canHandle(requestDto.getRole()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No handler found for role: " + requestDto.getRole()));


       return registrationHandler.handleRegistration(requestDto);

    }
}
