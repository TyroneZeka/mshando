package org.mufasadev.mshando.core.user.service;

import lombok.RequiredArgsConstructor;
import org.mufasadev.mshando.core.security.jwt.JwtUtils;
import org.mufasadev.mshando.core.security.service.UserDetailsImpl;
import org.mufasadev.mshando.core.user.models.AppRole;
import org.mufasadev.mshando.core.user.models.Role;
import org.mufasadev.mshando.core.user.models.User;
import org.mufasadev.mshando.core.user.payload.LoginRequest;
import org.mufasadev.mshando.core.user.payload.MessageResponse;
import org.mufasadev.mshando.core.user.payload.SignupRequest;
import org.mufasadev.mshando.core.user.payload.UserResponseInfo;
import org.mufasadev.mshando.core.user.repository.RoleRepository;
import org.mufasadev.mshando.core.user.repository.UserRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
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

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        UserResponseInfo response = new UserResponseInfo(userDetails.getId(), userDetails.getUsername(), roles);
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString()).body(response);
    }

    @Override
    public ResponseEntity<?> registerUser(SignupRequest signupRequest) {
        if(userRepository.existsByUsername(signupRequest.getUsername())) return ResponseEntity.badRequest().body("Error: Username is already in use");
        if(userRepository.existsByEmail(signupRequest.getEmail())) return ResponseEntity.badRequest().body("Error: Email is already in use");

        User user = new User(
                signupRequest.getUsername(),
                signupRequest.getEmail(),
                passwordEncoder.encode(signupRequest.getPassword())
        );
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
                        Role taskerRole = roleRepository.findByName(AppRole.ROLE_TASKER)
                                .orElseThrow(()-> new RuntimeException("Error: Role is not found"));
                        roles.add(taskerRole);
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
        return ResponseEntity.ok(new MessageResponse("User Registered successfully"));
    }

    @Override
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.generateCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(new MessageResponse("Logged out successfully!"));
    }
}