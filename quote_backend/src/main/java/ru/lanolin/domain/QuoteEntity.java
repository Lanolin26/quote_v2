package ru.lanolin.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "quote_entity")
public class QuoteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "source_id")
    private SourceEntity source;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private UserEntity author;

    private String text;

}