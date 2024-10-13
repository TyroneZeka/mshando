package org.mufasadev.mshando.core.user.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.Set;

@Data
@Getter
@Service
public class SignupRequest {
    @NotBlank
    private String username;
    @NotBlank
    @Size(min = 8)
    private String password;
    private String email;
    @Getter
    @Setter
    private Set<String> role;
}