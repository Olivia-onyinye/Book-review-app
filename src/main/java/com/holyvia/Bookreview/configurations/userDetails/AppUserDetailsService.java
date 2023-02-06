package com.holyvia.Bookreview.configurations.userDetails;

import com.holyvia.Bookreview.entities.User;
import com.holyvia.Bookreview.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User dbUser = userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("Not Found"));
        return new AppUserDetails(dbUser);
    }
}
