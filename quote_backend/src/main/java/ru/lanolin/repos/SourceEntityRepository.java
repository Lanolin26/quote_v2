package ru.lanolin.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.lanolin.domain.SourceEntity;

import java.util.UUID;

public interface SourceEntityRepository extends JpaRepository<SourceEntity, UUID> {

    boolean existsByNameIgnoreCase(String name);

}