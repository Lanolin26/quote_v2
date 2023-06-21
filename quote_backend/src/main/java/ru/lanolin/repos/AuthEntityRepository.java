package ru.lanolin.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.lanolin.domain.AuthEntity;

import java.util.UUID;

public interface AuthEntityRepository extends JpaRepository<AuthEntity, UUID> {
    boolean existsByEmail(String email);
    boolean existsByLogin(String login);

}