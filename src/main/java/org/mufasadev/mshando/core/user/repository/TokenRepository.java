package org.mufasadev.mshando.core.user.repository;

import org.mufasadev.mshando.core.user.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {
    Optional<Token> findByToken(String token);
}