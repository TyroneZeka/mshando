package org.mufasadev.mshando.core.user.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    @NotBlank(message = "Username should not be empty.")
    @NotEmpty(message = "Username should not be blank.")
    private String username;
    @NotBlank(message = "Password should not be empty.")
    @NotEmpty(message = "Password should not be blank.")
    private String password;
}