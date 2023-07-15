package ru.lanolin.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_entity")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID, generator = "uuid_v7_gen")
    @GenericGenerator(
            name = "uuid_v7_gen",
            type = ru.lanolin.config.UUIDv7Generator.class
    )
    @Column(name = "id", nullable = false)
    private UUID id;

    private String name;

    @OneToOne
    @JoinColumn(name = "auth_id")
    private AuthEntity auth;

    private boolean icon;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserEntity that = (UserEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}