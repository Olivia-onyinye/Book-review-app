package com.holyvia.Bookreview.services.serviceImpl;

import com.holyvia.Bookreview.configurations.tokens.TokenService;
import com.holyvia.Bookreview.entities.Token;
import com.holyvia.Bookreview.entities.User;
import com.holyvia.Bookreview.enums.TokenStatus;
import com.holyvia.Bookreview.repositories.TokenRepository;
import com.holyvia.Bookreview.services.UserTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserTokenServiceImpl implements UserTokenService {
    private final TokenService tokenService;
    private final TokenRepository tokenRepository;

    @Override
    public String generateAndSaveToken(User user) {
        String registrationToken = tokenService.generateVerificationToken(user.getEmail());
        Token token = new Token();
        token.setToken(registrationToken);
        token.setTokenStatus(TokenStatus.ACTIVE);
        token.setUser(user);
        tokenRepository.save(token);
        return registrationToken;
    }
}
