package ru.lanolin.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Entity
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "source_entity")
public class SourceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID, generator = "uuid_v7_gen")
    @GenericGenerator(
            name = "uuid_v7_gen",
            type = ru.lanolin.config.UUIDv7Generator.class
    )
    @Column(name = "id", nullable = false, unique = true)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private SourceTypeEntity type;

    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        SourceEntity that = (SourceEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}