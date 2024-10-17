package org.mufasadev.mshando.core.user.service;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.mufasadev.mshando.core.security.email.EmailService;
import org.mufasadev.mshando.core.security.email.EmailTemplate;
import org.mufasadev.mshando.core.security.jwt.JwtUtils;
import org.mufasadev.mshando.core.security.service.UserDetailsImpl;
import org.mufasadev.mshando.core.tasker.models.Tasker;
import org.mufasadev.mshando.core.tasker.repository.TaskerRepository;
import org.mufasadev.mshando.core.user.models.AppRole;
import org.mufasadev.mshando.core.user.models.Role;
import org.mufasadev.mshando.core.user.models.Token;
import org.mufasadev.mshando.core.user.models.User;
import org.mufasadev.mshando.core.user.payload.LoginRequest;
import org.mufasadev.mshando.core.user.payload.MessageResponse;
import org.mufasadev.mshando.core.user.payload.SignupRequest;
import org.mufasadev.mshando.core.user.payload.UserResponseInfo;
import org.mufasadev.mshando.core.user.repository.RoleRepository;
import org.mufasadev.mshando.core.user.repository.TokenRepository;
import org.mufasadev.mshando.core.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final RoleRepository roleRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final TaskerRepository taskerRepository;
    private final JwtUtils jwtService;
    @Value("${application.mailing.frontend.activation-url}")
    private String activationUrl;

    @Override
    public ResponseEntity<?> signInUser(LoginRequest loginRequest) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        }
        catch (AuthenticationException e) {
            Map<String, Object> map = new HashMap<>();
            map.put("message", e.getMessage());
            map.put("status", 400);
            return new ResponseEntity<Object>(map, HttpStatus.NOT_FOUND);
        }

        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
        var claims = new HashMap<String, Object>();
        claims.put("fullname", user.getFullname());
        var jwtToken = jwtService.generateToken(claims,user);
        List<String> roles = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        UserResponseInfo response = new UserResponseInfo(user.getId(), user.getUsername(), roles, jwtToken);
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtToken).body(response);
    }

    @Override
    public ResponseEntity<?> registerUser(SignupRequest signupRequest) throws MessagingException {
        if(userRepository.existsByUsername(signupRequest.getUsername())) return ResponseEntity.badRequest().body("Error: Username is already in use");
        if(userRepository.existsByEmail(signupRequest.getEmail())) return ResponseEntity.badRequest().body("Error: Email is already in use");

        var user = User.builder()
                .username(signupRequest.getUsername())
                .email(signupRequest.getEmail())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .firstname(signupRequest.getFirstname())
                .lastname(signupRequest.getLastname())
                .enabled(false)
                .accountLocked(false)
                .build();
        var tasker = Tasker.builder().build();

        Set<String> strRoles = signupRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if(strRoles == null || strRoles.isEmpty()) {
            Role userRole = roleRepository.findByName(AppRole.ROLE_CLIENT)
                    .orElseThrow(()-> new RuntimeException("Error: Role is not found"));
            roles.add(userRole);
        }
        else{
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(AppRole.ROLE_ADMIN)
                                .orElseThrow(()-> new RuntimeException("Error: Role is not found"));
                        roles.add(adminRole);
                        break;

                    case "tasker":
                        tasker.setUser(user);
                        Role taskerRole = roleRepository.findByName(AppRole.ROLE_TASKER)
                                .orElseThrow(()-> new RuntimeException("Error: Role is not found"));
                        roles.add(taskerRole);
                        user.setRoles(roles);
                        break;

                    default:
                        Role clientRole = roleRepository.findByName(AppRole.ROLE_CLIENT)
                                .orElseThrow(()-> new RuntimeException("Error: Role is not found"));
                        roles.add(clientRole);

                }
            });
        }
        user.setRoles(roles);
        userRepository.save(user);
        taskerRepository.save(tasker);
        sendValidationEmail(user);
        return ResponseEntity.ok(new MessageResponse("User Registered successfully"));
    }

    private String generateAndSaveActivationToken(User user) {
        String generatedToken = generateActivationCode(6);
        var token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();
        tokenRepository.save(token);
        return generatedToken;
    }

    private String generateActivationCode(int length) {
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }
        return codeBuilder.toString();
    }

    private void sendValidationEmail(User user) throws MessagingException {
        var newToken = generateAndSaveActivationToken(user);
//        Send Email
        emailService.sendEmail(
                user.getEmail(),
                EmailTemplate.ACTIVATE_ACCOUNT,
                user.getFullName(),
                activationUrl,
                "Mshando Account Activation",
                newToken
        );
    }

    @Override
    public void activateAccount(String token) throws MessagingException {
        Token savedToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Token not found"));
        if(LocalDateTime.now().isAfter(savedToken.getExpiresAt())) {
            sendValidationEmail(savedToken.getUser());
            throw new MessagingException("Token is expired, a new token has been created");
        }
        var user = userRepository.findByEmail(savedToken.getUser().getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setEnabled(true);
        userRepository.save(user);
        savedToken.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(savedToken);
    }

    @Override
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtService.generateCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(new MessageResponse("Logged out successfully!"));
    }
}