package com.holyvia.Bookreview.services;

import com.holyvia.Bookreview.entities.User;

public interface UserTokenService {

    String generateAndSaveToken(User user);
}
