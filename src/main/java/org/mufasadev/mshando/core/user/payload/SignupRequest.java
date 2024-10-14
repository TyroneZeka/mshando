package org.mufasadev.mshando.core.user.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.Set;

@Data
@Getter
@Setter
@Service
public class SignupRequest {
    @NotBlank
    private String username;
    @NotEmpty(message = "First Name should not be empty")
    @NotBlank(message = "First name should not be blank")
    private String firstname;
    @NotEmpty(message = "Last Name should not be empty")
    @NotBlank(message = "Last name should not be blank")
    private String lastname;
    @NotBlank
    @Size(min = 8)
    private String password;
    private String email;
    @Getter
    @Setter
    private Set<String> role;
}