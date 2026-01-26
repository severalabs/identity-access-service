package com.severalabs.ias.security.config;

import com.severalabs.ias.domain.User;
import com.severalabs.ias.exception.UserNotFoundException;
import com.severalabs.ias.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IasUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return new IasUserDetails( userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("user not found")) );
    }
}
