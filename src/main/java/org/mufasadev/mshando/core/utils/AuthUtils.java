package org.mufasadev.mshando.core.utils;

import lombok.RequiredArgsConstructor;
import org.mufasadev.mshando.core.user.models.User;
import org.mufasadev.mshando.core.user.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthUtils {
    private final UserRepository userRepository;

    public User loggedInUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUsername(authentication.getName()).orElseThrow(()->new UsernameNotFoundException("User not found"));
    }

    public Integer loggedInUserId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = loggedInUser();
        return user.getId();
    }

    public String loggedInUserEmail(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = loggedInUser();
        return user.getEmail();
    }
}