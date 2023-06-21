package ru.lanolin.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import ru.lanolin.domain.SourceTypeEntity;

import java.util.UUID;

public interface SourceTypeEntityRepository extends JpaRepository<SourceTypeEntity, UUID> {

    boolean existsByNameIgnoreCase(@NonNull String name);


}