package com.severalabs.ias.repository;

import com.severalabs.ias.domain.PasswordResetToken;
import com.severalabs.ias.domain.User;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PasswordResetTokenRepository
        extends JpaRepository<PasswordResetToken, UUID> {

    Optional<PasswordResetToken> findByHashedToken (String token);

    void deleteByUser (User user);


}
