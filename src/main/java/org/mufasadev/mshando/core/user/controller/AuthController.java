package org.mufasadev.mshando.core.user.controller;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.mufasadev.mshando.core.user.payload.LoginRequest;
import org.mufasadev.mshando.core.user.payload.SignupRequest;
import org.mufasadev.mshando.core.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) throws MessagingException {
        return userService.registerUser(signupRequest);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest){
        return userService.signInUser(loginRequest);
    }

    @GetMapping("/activate-account")
    public void confirm(@RequestParam String token) throws Exception{
        userService.activateAccount(token);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        return userService.logoutUser();
    }


}