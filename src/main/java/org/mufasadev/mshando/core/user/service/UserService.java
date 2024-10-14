package org.mufasadev.mshando.core.user.service;

import jakarta.mail.MessagingException;
import org.mufasadev.mshando.core.user.payload.LoginRequest;
import org.mufasadev.mshando.core.user.payload.SignupRequest;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<?> signInUser(LoginRequest loginRequest);

    ResponseEntity<?> registerUser(SignupRequest signupRequest) throws MessagingException;

    ResponseEntity<?> logoutUser();

    void activateAccount(String token) throws MessagingException;
}