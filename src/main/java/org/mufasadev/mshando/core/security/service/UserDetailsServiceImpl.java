package org.mufasadev.mshando.core.security.service;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.mufasadev.mshando.core.user.models.User;
import org.mufasadev.mshando.core.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService{

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not found!"));
//TODO: Catch Exception in Global Handler
    }
}