package com.holyvia.Bookreview.configurations.userDetails;

import com.holyvia.Bookreview.entities.User;
import com.holyvia.Bookreview.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class PasswordService implements UserDetailsPasswordService {
    private final UserRepository userRepository;

    @Override
    public UserDetails updatePassword(UserDetails userDetails, String password) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User does not exist!"));
        user.setPassword(password);
        return new AppUserDetails(user);
    }
}
