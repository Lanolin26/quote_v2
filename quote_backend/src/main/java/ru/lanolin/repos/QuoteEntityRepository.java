package ru.lanolin.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.lanolin.domain.QuoteEntity;

import java.util.UUID;

public interface QuoteEntityRepository extends JpaRepository<QuoteEntity, UUID> {



}