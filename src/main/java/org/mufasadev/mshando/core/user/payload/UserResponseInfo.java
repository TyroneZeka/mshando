package org.mufasadev.mshando.core.user.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class UserResponseInfo {
    private Integer id;
    private String username;
    private List<String> roles;
    private String token;
}