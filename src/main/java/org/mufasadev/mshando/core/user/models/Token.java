package org.mufasadev.mshando.core.user.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.parameters.P;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String token;
    private LocalDateTime createdAt;
    private LocalDateTime validatedAt;
    private LocalDateTime expiresAt;

    @ManyToOne()
    @JoinColumn(name = "user_id",nullable = false)
    private User user;
}